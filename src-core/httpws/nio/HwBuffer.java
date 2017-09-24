package httpws.nio;

import java.io.EOFException;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import httpws.util.Base64;
import httpws.util.Charset;
import httpws.util.Sha1;

public class HwBuffer {

	protected final ByteBuffer buffer;

	public static final DateTimeFormatter HTTP_DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.of(TimeZone.getTimeZone("UTC").getID()));
	
	public static final int UINT_COMPRESS_SIZE = 5;

	/**
	 * Construtor
	 *
	 * @param capacity
	 */
	public HwBuffer(int capacity) {
		buffer = ByteBuffer.allocate(capacity);
	}

	/**
	 * @return capacity
	 * @see java.nio.Buffer#capacity()
	 */
	public int capacity() {
		return buffer.capacity();
	}

	/**
	 * @return position
	 * @see java.nio.Buffer#position()
	 */
	public int position() {
		return buffer.position();
	}

	/**
	 * @param newPosition
	 * @return this
	 * @see java.nio.Buffer#position(int)
	 */
	public HwBuffer position(int newPosition) {
		buffer.position(newPosition);
		return this;
	}

	/**
	 * @return limit
	 * @see java.nio.Buffer#limit()
	 */
	public int limit() {
		return buffer.limit();
	}

	/**
	 * @param newLimit
	 * @return this
	 * @see java.nio.Buffer#limit(int)
	 */
	public HwBuffer limit(int newLimit) {
		buffer.limit(newLimit);
		return this;
	}

	/**
	 * @return this
	 * @see java.nio.Buffer#mark()
	 */
	public HwBuffer mark() {
		buffer.mark();
		return this;
	}

	/**
	 * @return this
	 * @see java.nio.Buffer#reset()
	 */
	public HwBuffer reset() {
		buffer.reset();
		return this;
	}

	/**
	 * @return this
	 * @see java.nio.Buffer#clear()
	 */
	public HwBuffer clear() {
		buffer.clear();
		return this;
	}

	/**
	 * @return this
	 * @see java.nio.Buffer#flip()
	 */
	public HwBuffer flip() {
		buffer.flip();
		return this;
	}

	/**
	 * Realiza um flip com os bytes que ainda falta ler, como já escrito
	 *
	 * @param position
	 * @return this
	 */
	public HwBuffer flip(int position) {
		if (position > 0) {
			System.arraycopy(buffer.array(), buffer.position(), buffer.array(), 0, position);
		}
		buffer.position(position);
		buffer.limit(buffer.capacity());
		return this;
	}

	/**
	 * @return this
	 * @see java.nio.Buffer#rewind()
	 */
	public HwBuffer rewind() {
		buffer.rewind();
		return this;
	}

	/**
	 * @return remaining
	 * @see java.nio.Buffer#remaining()
	 */
	public int remaining() {
		return buffer.remaining();
	}

	/**
	 * @return has remaining
	 * @see java.nio.Buffer#hasRemaining()
	 */
	public boolean hasRemaining() {
		return buffer.hasRemaining();
	}

	/**
	 * @return byte
	 * @see java.nio.ByteBuffer#get()
	 */
	public int get() {
		return buffer.get() & 0xFF;
	}

	/**
	 * Realiza a leitura de um byte. Caso não consiga, envia um erro restaurando o estado de escrita
	 *
	 * @return byte ou erro
	 * @throws EOFException
	 */
	public int getOrFlip() throws EOFException {
		if (!buffer.hasRemaining()) {
			buffer.position(buffer.limit()).limit(buffer.capacity());
			throw new EOFException();
		}
		return get();
	}

	/**
	 * @param b
	 * @return this
	 * @see java.nio.ByteBuffer#put(byte)
	 */
	public HwBuffer put(int b) {
		buffer.put((byte) b);
		return this;
	}

	/**
	 * @param index
	 * @return byte
	 * @see java.nio.ByteBuffer#get(int)
	 */
	public int get(int index) {
		return buffer.get(index);
	}

	/**
	 * @param b
	 * @return this
	 * @see java.nio.ByteBuffer#put(byte)
	 */
	public HwBuffer put(byte[] b) {
		buffer.put(b);
		return this;
	}

	/**
	 * @param dst
	 * @param offset
	 * @param length
	 * @return this
	 * @see java.nio.ByteBuffer#get(byte[], int, int)
	 */
	public HwBuffer get(byte[] dst, int offset, int length) {
		buffer.get(dst, offset, length);
		return this;
	}

	/**
	 * @param dst
	 * @return this
	 * @see java.nio.ByteBuffer#get(byte[])
	 */
	public HwBuffer get(byte[] dst) {
		buffer.get(dst);
		return this;
	}

	/**
	 * @return byte
	 */
	public int getUInt32() {
		int value = (buffer.get() & 0xFF) << 24;
		value += (buffer.get() & 0xFF) << 16;
		value += (buffer.get() & 0xFF) << 8;
		value += buffer.get() & 0xFF;
		return value;
	}

	/**
	 * @return byte
	 */
	public int getUInt8() {
		return buffer.get() & 0xFF;
	}

	/**
	 * @param src
	 * @param offset
	 * @param length
	 * @return this
	 * @see java.nio.ByteBuffer#put(byte[], int, int)
	 */
	public HwBuffer put(byte[] src, int offset, int length) {
		buffer.put(src, offset, length);
		return this;
	}

	/**
	 * Retorna o buffer
	 *
	 * @return buffer
	 */
	public ByteBuffer buffer() {
		return buffer;
	}

	/**
	 * @return bytes
	 * @see java.nio.ByteBuffer#array()
	 */
	public byte[] array() {
		return buffer.array();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		int position = buffer.position();
		StringBuffer sb = new StringBuffer();
		sb.append(getClass().getName());
		sb.append("{pos=");
		sb.append(position);
		sb.append(", lim=");
		sb.append(buffer.limit());
		sb.append(", cap=");
		sb.append(buffer.capacity());
		sb.append(", bytes=[");
		int size = Math.min(16, buffer.remaining());
		for (int n = 0; n < size; n++) {
			int c = buffer.get(position + n) & 0xFF;
			sb.append(c);
			if (c >= 32 && c <= 126) {
				sb.append("(");
				sb.append((char) c);
				sb.append(")");
			}
			if (n != size - 1) {
				sb.append(", ");
			}
		}
		if (buffer.remaining() > 16) {
			sb.append(", ...");
		}
		sb.append("]}");
		return sb.toString();
	}

	/**
	 * Realiza uma resposta http baseado num arquivo
	 *
	 * @param code
	 * @param text
	 * @return this
	 */
	public HwBuffer putHttpHeader(int code, String text) {
		String content = String.format("HTTP/1.1 %d %s\r\n", code, text);
		put(Charset.ascii(content));
		return this;
	}

	/**
	 * Realiza uma resposta http baseado num arquivo
	 *
	 * @param headers
	 * @param lastModified
	 * @return this
	 */
	public HwBuffer putHttpHeader(Map<String, String> headers, long lastModified) {
		String modifiedSince = headers.get("if-modified-since");
		if (modifiedSince != null) {
			Date lastModifiedDate = new Date(lastModified);
			Date modifiedSinceDate = Date.from(Instant.from(HwBuffer.HTTP_DATE_FORMAT.parse(modifiedSince)));
			if (modifiedSinceDate.after(lastModifiedDate)) { return putHttpHeader(304, "Not Modified"); }
		}
		return putHttpHeader(200, "Ok");
	}

	/**
	 * Realiza uma resposta http baseado num arquivo
	 *
	 * @param length
	 * @return this
	 */
	public HwBuffer putHttpContentLength(long length) {
		put(Charset.ascii("Content-Length: "));
		put(Charset.ascii(Long.toString(length)));
		put('\r').put('\n');
		return this;
	}

	/**
	 * Realiza uma resposta http baseado num arquivo
	 *
	 * @param type
	 * @param encode
	 * @return this
	 */
	public HwBuffer putHttpContentType(String type, String encode) {
		put(Charset.ascii("Content-Type: "));
		put(Charset.ascii(type));
		if (encode != null) {
			put(Charset.ascii("; charset=" + encode));
		}
		put('\r').put('\n');
		return this;
	}

	/**
	 * Realiza uma resposta http baseado num arquivo
	 *
	 * @param mime
	 * @param file
	 * @return this
	 */
	public HwBuffer putHttpContentType(Map<String, String> mime, String file) {
		int extensionIndex = file.lastIndexOf('.');
		if (extensionIndex >= 0) {
			String extension = file.substring(extensionIndex + 1);
			putHttpContentType(mime.get(extension), "utf-8");
		}
		return this;
	}

	/**
	 * Realiza uma resposta http baseado num arquivo
	 *
	 * @param date
	 * @param experies
	 * @param lastModified
	 * @return this
	 */
	public HwBuffer putHttpDateExperiesLastModified(Date date, Date experies, Date lastModified) {
		Calendar calendar = Calendar.getInstance();
		DateTimeFormatter dateFormat = HwBuffer.HTTP_DATE_FORMAT;
		if (date != null) {
			calendar.setTime(date);
			put(Charset.ascii("Date: "));
			put(Charset.ascii(dateFormat.format(date.toInstant())));
			put('\r').put('\n');
		}
		if (experies != null) {
			calendar.setTime(experies);
			put(Charset.ascii("Expires: "));
			put(Charset.ascii(dateFormat.format(date.toInstant())));
			put('\r').put('\n');
		}
		if (lastModified != null) {
			calendar.setTime(lastModified);
			put(Charset.ascii("Last-Modified: "));
			put(Charset.ascii(dateFormat.format(date.toInstant())));
			put('\r').put('\n');
		}
		return this;
	}

	/**
	 * Realiza uma resposta http baseado num arquivo
	 *
	 * @param key
	 * @return this
	 */
	public HwBuffer putWsHeader(String key) {
		put(Charset.ascii("HTTP/1.1 101 Switching Protocols\r\n"));
		put(Charset.ascii("Upgrade: Websocket\r\n"));
		put(Charset.ascii("Connection: Upgrade\r\n"));
		put(Charset.ascii("Sec-WebSocket-Accept: "));
		key += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		put(Charset.ascii(Base64.encode(Sha1.encode(key))));
		put('\r').put('\n');
		return this;
	}

	/**
	 * Realiza uma resposta http baseado num arquivo
	 *
	 * @return this
	 */
	public HwBuffer putHttpEof() {
		put('\r').put('\n');
		return this;
	}

	public HwBuffer putWsHeader(Map<String, String> headers) {
		String key = headers.get("sec-websocket-key") + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		buffer.put(Charset.ascii("HTTP/1.1 101 Switching Protocols\r\n"));
		buffer.put(Charset.ascii("Upgrade: Websocket\r\n"));
		buffer.put(Charset.ascii("Connection: Upgrade\r\n"));
		buffer.put(Charset.ascii("Sec-WebSocket-Accept: "));
		buffer.put(Charset.ascii(Base64.encode(Sha1.encode(key))));
		buffer.put(Charset.ascii("\r\n\r\n"));
		return this;
	}

	public HwBuffer putWsString(String text) {
		long length = 0;
		for (int n = 0; n < text.length(); n++) {
			char c = text.charAt(n);
			if (c <= 0x7F) {
				length++;
			} else if (c <= 0x7FF) {
				length += 2;
			} else {
				length += 3;
			}
		}
		buffer.put((byte) (0x80 + 0x1));
		if (length <= 125) {
			buffer.put((byte) length);
		} else if (length <= 0xFFFF) {
			buffer.put((byte) 126);
			buffer.put((byte) ((length >> 8) & 0xFF));
			buffer.put((byte) (length & 0xFF));
		} else {
			buffer.put((byte) ((length >> 56) & 0xFF));
			buffer.put((byte) ((length >> 48) & 0xFF));
			buffer.put((byte) ((length >> 40) & 0xFF));
			buffer.put((byte) ((length >> 32) & 0xFF));
			buffer.put((byte) ((length >> 24) & 0xFF));
			buffer.put((byte) ((length >> 16) & 0xFF));
			buffer.put((byte) ((length >> 8) & 0xFF));
			buffer.put((byte) (length & 0xFF));
		}
		for (int n = 0; n < text.length(); n++) {
			int c = text.charAt(n);
			if (c <= 0x7F) {
				buffer.put((byte) c);
			} else if (c <= 0x7FF) {
				buffer.put((byte) (((c >> 6) & 0x1F) + 0xC0));
				buffer.put((byte) ((c & 0x3F) + 0x80));
			} else {
				buffer.put((byte) (((c >> 12) & 0xF) + 0xE0));
				buffer.put((byte) (((c >> 6) & 0x3F) + 0x80));
				buffer.put((byte) ((c & 0x3F) + 0x80));
			}
		}
		return this;
	}

	public HwBuffer putWsBytes(byte[] bytes) {
		long length = bytes.length;
		buffer.put((byte) (0x80 + 0x2));
		if (length <= 125) {
			buffer.put((byte) length);
		} else if (length <= 0xFFFF) {
			buffer.put((byte) 126);
			buffer.put((byte) ((length >> 8) & 0xFF));
			buffer.put((byte) (length & 0xFF));
		} else {
			buffer.put((byte) ((length >> 56) & 0xFF));
			buffer.put((byte) ((length >> 48) & 0xFF));
			buffer.put((byte) ((length >> 40) & 0xFF));
			buffer.put((byte) ((length >> 32) & 0xFF));
			buffer.put((byte) ((length >> 24) & 0xFF));
			buffer.put((byte) ((length >> 16) & 0xFF));
			buffer.put((byte) ((length >> 8) & 0xFF));
			buffer.put((byte) (length & 0xFF));
		}
		for (int n = 0; n < length; n++) {
			buffer.put(bytes[n]);
		}
		return this;
	}

	/**
	 * Adiciona os bytes no modo WebSocket
	 *
	 * @param buffer
	 * @return this
	 */
	public HwBuffer putWsBytes(HwBuffer buffer) {
		this.buffer.put((byte) (0x80 + 0x2));
		int length = buffer.remaining();
		if (length <= 125) {
			this.buffer.put((byte) length);
		} else if (length <= 65535) {
			this.buffer.put((byte) 126);
			this.buffer.put((byte) (length >> 8));
			this.buffer.put((byte) (length & 0xFF));
		} else {
			long llength = length;
			this.buffer.put((byte) 127);
			this.buffer.put((byte) (llength >> 56));
			this.buffer.put((byte) ((llength >> 48) & 0xFF));
			this.buffer.put((byte) ((llength >> 40) & 0xFF));
			this.buffer.put((byte) ((llength >> 32) & 0xFF));
			this.buffer.put((byte) ((llength >> 24) & 0xFF));
			this.buffer.put((byte) ((llength >> 16) & 0xFF));
			this.buffer.put((byte) ((llength >> 8) & 0xFF));
			this.buffer.put((byte) (llength & 0xFF));
		}
		put(buffer.array(), 0, length);
		return this;
	}

	/**
	 * @param value
	 * @return this
	 */
	public HwBuffer putUInt8(int value) {
		if (value > 0xFF) { throw new IllegalArgumentException(); }
		put((byte) (value & 0xFF));
		return this;
	}

	/**
	 * @param utf8
	 * @return this
	 */
	public HwBuffer putUtf8Bytes(byte[] utf8) {
		int length = utf8.length;
		putUInt32Compressed(length);
		put(utf8, 0, utf8.length);
		return this;
	}

	/**
	 * @param value
	 * @return this
	 */
	public HwBuffer putUInt32Compressed(int value) {
		if (value < 0) { throw new IllegalArgumentException(); }
		if (value < 0x80) {
			put((byte) (value & 0x7F));
		} else if (value < 0x4000) {
			put((byte) ((value & 0x7F) + 0x80));
			put((byte) ((value >> 7) & 0x7F));
		} else if (value < 0x200000) {
			put((byte) ((value & 0x7F) + 0x80));
			put((byte) (((value >> 7) & 0x7F) + 0x80));
			put((byte) ((value >> 14) & 0x7F));
		} else if (value < 0x10000000) {
			put((byte) ((value & 0x7F) + 0x80));
			put((byte) (((value >> 7) & 0x7F) + 0x80));
			put((byte) (((value >> 14) & 0x7F) + 0x80));
			put((byte) ((value >> 21) & 0x7F));
		} else {
			put((byte) ((value & 0x7F) + 0x80));
			put((byte) (((value >> 7) & 0x7F) + 0x80));
			put((byte) (((value >> 14) & 0x7F) + 0x80));
			put((byte) (((value >> 21) & 0x7F) + 0x80));
			put((byte) ((value >> 28) & 0x7F));
		}
		return this;
	}

	/**
	 * Realiza a leitura e um número inteiro positivo. Caso ocorra algum problema na leitura, será
	 * retornado o valor -1 e o estado da leitura será restaurada.
	 *
	 * @return inteiro positivo ou -1 caso haja algum erro.
	 */
	public int getUInt32Compressed() {
		if (!buffer.hasRemaining()) { return -1; }
		int position = buffer.position();
		int c1 = buffer.get() & 0xFF;
		if ((c1 & 0x80) == 0) { return c1; }
		c1 -= 0x80;
		if (!buffer.hasRemaining()) {
			buffer.position(position);
			return -1;
		}
		int c2 = buffer.get() & 0xFF;
		if ((c2 & 0x80) == 0) { return c1 + (c2 << 7); }
		c2 -= 0x80;
		if (!buffer.hasRemaining()) {
			buffer.position(position);
			return -1;
		}
		int c3 = buffer.get() & 0xFF;
		if ((c3 & 0x80) == 0) { return c1 + (c2 << 7) + (c3 << 14); }
		c3 -= 0x80;
		if (!buffer.hasRemaining()) {
			buffer.position(position);
			return -1;
		}
		int c4 = buffer.get() & 0xFF;
		if ((c4 & 0x80) == 0) { return c1 + (c2 << 7) + (c3 << 14) + (c4 << 21); }
		c4 -= 0x80;
		if (!buffer.hasRemaining()) {
			buffer.position(position);
			return -1;
		}
		int c5 = buffer.get() & 0xFF;
		if ((c5 & 0x80) == 0) { return c1 + (c2 << 7) + (c3 << 14) + (c4 << 21) + (c5 << 28); }
		buffer.position(position);
		return -1;
	}

	/**
	 * Realiza a leitura de uma String até o final do buffer
	 *
	 * @return string em utf8
	 */
	public String getString() {
		int length = getUInt32Compressed();
		int position = buffer.position();
		String result = Charset.utf8(buffer.array(), position, length);
		buffer.position(position + length);
		return result;
	}

	/**
	 * @param value
	 * @return this
	 */
	public static int sizeCompressUInt(int value) {
		if (value < 0) { return -1; }
		if (value < 0x80) {
			return 1;
		} else if (value < 0x4000) {
			return 2;
		} else if (value < 0x200000) {
			return 3;
		} else if (value < 0x10000000) {
			return 4;
		} else {
			return 5;
		}
	}

	/**
	 * Restaura o limite
	 *
	 * @return this
	 */
	public HwBuffer restore() {
		buffer.limit(buffer.capacity());
		return this;
	}

	/**
	 * Desloca a posição e o limite para o inicio copiando os dados que ainda faltam ser lidos
	 *
	 * @return this
	 */
	public HwBuffer shift() {
		byte[] array = buffer.array();
		int position = buffer.position();
		int length = buffer.remaining();
		if (length > 0) {
			System.arraycopy(array, position, array, 0, length);
		}
		buffer.position(0);
		buffer.limit(length);
		return this;
	}

}
