package httpws.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Codificador de Sha1
 *
 * @author Bernardo Breder
 */
public class Sha1 {
	
	/**
	 * Codifica para Sha1
	 *
	 * @param text
	 * @return bytes
	 */
	public static byte[] encode(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] utf8 = Charset.utf8(text);
			md.update(utf8, 0, utf8.length);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
}
