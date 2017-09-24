package httpws.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Codificador de Md5
 *
 * @author Bernardo Breder
 */
public class Md5 {
	
	/**
	 * Codifica em md5
	 *
	 * @param bytes
	 * @return bytes
	 */
	public static byte[] encode(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes, 0, bytes.length);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
}
