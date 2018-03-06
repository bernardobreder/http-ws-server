package httpws.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import httpws.nio.HwBuffer;

public class ByteBufferUtil {

	/**
	 * Realiza a leitura de uma linha. Caso não tenha nenhuma informação para ser lido, será
	 * retornado nulo.
	 *
	 * @param buffer
	 * @return line terminado por '\n'
	 */
	public static String line(HwBuffer buffer) {
		if (!buffer.hasRemaining()) { return null; }
		StringBuilder sb = new StringBuilder(32);
		char c;
		do {
			int i1 = buffer.get() & 0xFF;
			if (i1 <= 0x7F) {
				c = (char) i1;
			} else if ((i1 >> 5) == 0x6) {
				if (!buffer.hasRemaining()) {
					break;
				}
				int i2 = buffer.get() & 0xFF;
				c = (char) (((i1 & 0x1F) << 6) + (i2 & 0x3F));
			} else {
				int i2 = buffer.get() & 0xFF;
				if (!buffer.hasRemaining()) {
					break;
				}
				int i3 = buffer.get() & 0xFF;
				if (!buffer.hasRemaining()) {
					break;
				}
				c = (char) (((i1 & 0xF) << 12) + ((i2 & 0x3F) << 6) + (i3 & 0x3F));
			}
			if (c == '\n') {
				break;
			} else if (c == '\r' && !buffer.hasRemaining()) {
				c = '\r';
				return null;
			}
			if (c != '\r') {
				sb.append(c);
			}
		} while (buffer.hasRemaining());
		return sb.toString();
	}

	/**
	 * Retorna a lista de headers e a primeira linha de um cabeçalho http. Caso o cabeçalho não
	 * esteja completo (terminado com \r\n\r\n), será retornado nulo e a posição da leitura será
	 * restaurada.
	 *
	 * @param buffer
	 * @return lista de headers e a primeira linha ou nulo caso não tenha dado
	 */
	public static String[] lines(HwBuffer buffer) {
		int position = buffer.position();
		String line = line(buffer);
		if (line == null) { return null; }
		List<String> lines = new ArrayList<String>(16);
		lines.add(line);
		line = line(buffer);
		while (line != null) {
			if (line.length() == 0) {
				break;
			}
			lines.add(line);
			line = line(buffer);
		}
		if (line == null || line.length() != 0) {
			buffer.position(position);
			return null;
		}
		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * Retorna o header do protocolo http. Caso os dados não sejam suficiente, será retornado nulo e
	 * o estado do buffer será restaurado. O header resultante sempre terá a chave "method", "path"
	 * e "protocol".
	 *
	 * @param buffer
	 * @return header http
	 */
	public static boolean hasHttp(HwBuffer buffer) {
		if (!buffer.hasRemaining()) { return false; }
		int off = buffer.position();
		return buffer.get(off - 1) == '\n' && buffer.get(off - 3) == '\n';
	}

	/**
	 * Retorna o header do protocolo http. Caso os dados não sejam suficiente, será retornado nulo e
	 * o estado do buffer será restaurado. O header resultante sempre terá a chave "method", "path"
	 * e "protocol".
	 *
	 * @param buffer
	 * @return header http
	 */
	public static Map<String, String> http(HwBuffer buffer) {
		String[] lines = lines(buffer);
		if (lines == null) { return null; }
		Map<String, String> headers = new HashMap<String, String>();
		String first = lines[0];
		int firstIndexOf = first.indexOf(' ');
		int firstLastIndexOf = first.lastIndexOf(' ');
		if (firstIndexOf < 0 || firstLastIndexOf < 0) { return null; }
		String method = first.substring(0, firstIndexOf);
		String path = first.substring(method.length() + 1, firstLastIndexOf);
		String protocol = first.substring(method.length() + 1 + path.length() + 1);
		headers.put("method", method);
		headers.put("path", path);
		headers.put("protocol", protocol);
		for (int n = 1; n < lines.length; n++) {
			String line = lines[n];
			int s = line.indexOf(':');
			String key = line.substring(0, s).toLowerCase();
			String value = line.substring(s + 2);
			headers.put(key, value);
		}
		return headers;
	}

	/**
	 * Realiza uma resposta http baseado num arquivo
	 *
	 * @param buffer
	 * @param key
	 */
	public static void putWsHeader(HwBuffer buffer, String key) {
		buffer.put(Charset.ascii("HTTP/1.1 101 Switching Protocols"));
		putEof(buffer);
		buffer.put(Charset.ascii("Upgrade: Websocket"));
		putEof(buffer);
		buffer.put(Charset.ascii("Connection: Upgrade"));
		putEof(buffer);
		buffer.put(Charset.ascii("Sec-WebSocket-Accept: "));
		key += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		buffer.put(Charset.ascii(Base64.encode(Sha1.encode(key))));
		putEof(buffer);
	}

	public static void putEof(HwBuffer buffer) {
		buffer.put('\r').put('\n');
	}

}
