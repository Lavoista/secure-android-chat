package at.fhooe.kls;

import java.security.NoSuchAlgorithmException;

// TODO: Auto-generated Javadoc
/**
 * The Class Hash.
 */
public class Hash {
	/** The Constant DIGEST_ALGORITHM. */
	public static final String DIGEST_ALGORITHM = "SHA-256";

	/**
	 * Generates a SHA256.
	 *
	 * @param text plaintext
	 * @return the byte[] hashed plaintext
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public static byte[] SHA256(byte[] text) throws NoSuchAlgorithmException {
		
			java.security.MessageDigest h = java.security.MessageDigest.getInstance(DIGEST_ALGORITHM);
			return h.digest(text);
		
	}
}
