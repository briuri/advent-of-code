package buri.aoc.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class to compute an MD5 hash.
 * 
 * @author Brian Uri!
 */
public class MD5Hash {
	private MessageDigest _digest;

	/**
	 * Constructor
	 */
	public MD5Hash() {
		try {
			_digest = MessageDigest.getInstance("MD5");

		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Computes an MD5 hash of a string.
	 */
	public String getHash(String input) {
		StringBuilder builder = new StringBuilder();
		for (byte b : getDigest().digest(input.getBytes())) {
			builder.append(Character.forDigit((b >> 4) & 0xF, 16));
			builder.append(Character.forDigit((b & 0xF), 16));
		}
		return (builder.toString());
	}

	/**
	 * Accessor for the digest
	 */
	public MessageDigest getDigest() {
		return _digest;
	}
}
