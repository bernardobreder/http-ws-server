package httpws.util;

/**
 * Codificador base64
 *
 * @author bbreder
 */
public abstract class Base64 {
	
	/** Mapa de convers�o **/
	private static final char[] map1 = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789+/=").toCharArray();
	
	/** Mapa de retorno */
	private static final byte[] map2 = new byte[128];
	
	/**
	 * Inicializa as constantes
	 */
	static {
		for (int i = 0; i < map2.length; i++) {
			map2[i] = -1;
		}
		for (int i = 0; i < 64; i++) {
			map2[map1[i]] = (byte) i;
		}
	}
	
	/**
	 * Codifica bytes
	 *
	 * @param bytes
	 * @return string
	 */
	public static String encode(byte[] bytes) {
		return new String(encode(bytes, 0, bytes.length));
	}
	
	/**
	 * Encodes a byte array into Base64 format. No blanks or line breaks are inserted in the output.
	 *
	 * @param in
	 *            An array containing the data bytes to be encoded.
	 * @param iOff
	 *            Offset of the first byte in <code>in</code> to be processed.
	 * @param iLen
	 *            Number of bytes to process in <code>in</code>, starting at <code>iOff</code>.
	 * @return A character array containing the Base64 encoded data.
	 */
	public static String encode(byte[] in, int iOff, int iLen) {
		int oDataLen = (iLen * 4 + 2) / 3;
		int oLen = ((iLen + 2) / 3) * 4;
		char[] out = new char[oLen];
		int ip = iOff;
		int iEnd = iOff + iLen;
		int op = 0;
		while (ip < iEnd) {
			int i0 = in[ip++] & 0xff;
			int i1 = ip < iEnd ? in[ip++] & 0xff : 0;
			int i2 = ip < iEnd ? in[ip++] & 0xff : 0;
			int o0 = i0 >>> 2;
			int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
			int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
			int o3 = i2 & 0x3F;
			out[op++] = map1[o0];
			out[op++] = map1[o1];
			out[op] = op < oDataLen ? map1[o2] : '=';
			op++;
			out[op] = op < oDataLen ? map1[o3] : '=';
			op++;
		}
		return new String(out);
	}
	
	/**
	 * Decodifica uma string
	 *
	 * @param encode
	 * @return bytes
	 */
	public static byte[] decode(String encode) {
		char[] chars = encode.toCharArray();
		return decode(chars, 0, chars.length);
	}
	
	/**
	 * Decodes a byte array from Base64 format. No blanks or line breaks are allowed within the
	 * Base64 encoded input data.
	 *
	 * @param in
	 *            A character array containing the Base64 encoded data.
	 * @param iOff
	 *            Offset of the first character in <code>in</code> to be processed.
	 * @param iLen
	 *            Number of characters to process in <code>in</code>, starting at <code>iOff</code>.
	 * @return An array containing the decoded data bytes.
	 * @throws IllegalArgumentException
	 *             If the input is not valid Base64 encoded data.
	 */
	public static byte[] decode(char[] in, int iOff, int iLen) {
		if (iLen % 4 != 0) { throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4."); }
		while (iLen > 0 && in[iOff + iLen - 1] == '=') {
			iLen--;
		}
		int oLen = (iLen * 3) / 4;
		byte[] out = new byte[oLen];
		int ip = iOff;
		int iEnd = iOff + iLen;
		int op = 0;
		while (ip < iEnd) {
			int i0 = in[ip++];
			int i1 = in[ip++];
			int i2 = ip < iEnd ? in[ip++] : 'A';
			int i3 = ip < iEnd ? in[ip++] : 'A';
			if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127) { throw new IllegalArgumentException("Illegal character in Base64 encoded data."); }
			int b0 = map2[i0];
			int b1 = map2[i1];
			int b2 = map2[i2];
			int b3 = map2[i3];
			if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0) { throw new IllegalArgumentException("Illegal character in Base64 encoded data."); }
			int o0 = (b0 << 2) | (b1 >>> 4);
			int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
			int o2 = ((b2 & 3) << 6) | b3;
			out[op++] = (byte) o0;
			if (op < oLen) {
				out[op++] = (byte) o1;
			}
			if (op < oLen) {
				out[op++] = (byte) o2;
			}
		}
		return out;
	}
	
}
