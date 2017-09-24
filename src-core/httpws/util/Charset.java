package httpws.util;

/**
 * Realiza o tratamento do charset
 *
 * @author Bernardo Breder
 */
public class Charset {
	
	/**
	 * @param bytes
	 * @return bytes
	 */
	public static String ascii(byte[] bytes) {
		return ascii(bytes, 0, bytes.length);
	}
	
	/**
	 * @param bytes
	 * @param off
	 * @param length
	 * @return bytes
	 */
	public static String ascii(byte[] bytes, int off, int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int n = off, m = 0; m < length; n++, m++) {
			sb.append((char) bytes[n]);
		}
		return sb.toString();
	}
	
	/**
	 * @param text
	 * @return bytes
	 */
	public static byte[] ascii(String text) {
		int len = text.length();
		byte[] bytes = new byte[len];
		for (int n = 0, i = 0; n < len; n++, i++) {
			bytes[i] = (byte) text.charAt(n);
		}
		return bytes;
	}
	
	/**
	 * @param text
	 * @return bytes
	 */
	public static byte[] utf8(String text) {
		int len = 0;
		int textlen = text.length();
		for (int n = 0; n < textlen; n++) {
			char c = text.charAt(n);
			if (c <= 0x7F) {
				len++;
			} else if (c <= 0x7FF) {
				len += 2;
			} else {
				len += 3;
			}
		}
		byte[] bytes = new byte[len];
		for (int n = 0, i = 0; n < textlen; n++) {
			int c = text.charAt(n);
			if (c <= 0x7F) {
				bytes[i++] = (byte) c;
			} else if (c <= 0x7FF) {
				bytes[i++] = (byte) (((c >> 6) & 0x1F) + 0xC0);
				bytes[i++] = (byte) ((c & 0x3F) + 0x80);
			} else {
				bytes[i++] = (byte) (((c >> 12) & 0xF) + 0xE0);
				bytes[i++] = (byte) (((c >> 6) & 0x3F) + 0x80);
				bytes[i++] = (byte) ((c & 0x3F) + 0x80);
			}
		}
		return bytes;
	}
	
	/**
	 * @param bytes
	 * @return string utf8
	 */
	public static String utf8(byte[] bytes) {
		return utf8(bytes, 0, bytes.length);
	}
	
	/**
	 * @param bytes
	 * @param off
	 * @param length
	 * @return string utf8
	 */
	public static String utf8(byte[] bytes, int off, int length) {
		StringBuilder sb = new StringBuilder(length);
		int size = off + length;
		for (int n = off; n < size;) {
			int i1 = bytes[n++] & 0xFF;
			if (i1 <= 0x7F) {
				sb.append((char) i1);
			} else if ((i1 >> 5) == 0x6) {
				int i2 = bytes[n++] & 0xFF;
				sb.append((char) (((i1 & 0x1F) << 6) + (i2 & 0x3F)));
			} else {
				int i2 = bytes[n++] & 0xFF;
				int i3 = bytes[n++] & 0xFF;
				sb.append((char) (((i1 & 0xF) << 12) + ((i2 & 0x3F) << 6) + (i3 & 0x3F)));
			}
		}
		return sb.toString();
	}
	
}
