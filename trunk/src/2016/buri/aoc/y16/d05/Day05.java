package buri.aoc.y16.d05;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day05 extends Puzzle {

	/**
	 * Part 1:
	 * The eight-character password for the door is generated one character at a time by finding the MD5 hash of some
	 * Door ID (your puzzle input) and an increasing integer index (starting with 0).
	 * 
	 * A hash indicates the next character in the password if its hexadecimal representation starts with five zeroes. If
	 * it does, the sixth character in the hash is the next character of the password.
	 * 
	 * Part 2:
	 * Instead of simply filling in the password from left to right, the hash now also indicates the position within the
	 * password to fill. You still look for hashes that begin with five zeroes; however, now, the sixth character
	 * represents the position (0-7), and the seventh character is the character to put in that position.
	 */
	public static String getResult(Part part, String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			StringBuffer password = new StringBuffer();
			password.append("        ");
			int i = 0;
			while (password.toString().indexOf(" ") != -1) {
				String iteration = input + i;
				StringBuffer buffer = new StringBuffer();
				for (byte b : md.digest(iteration.getBytes("UTF-8"))) {
					buffer.append(Character.forDigit((b >> 4) & 0xF, 16));
					buffer.append(Character.forDigit((b & 0xF), 16));
				}
				String hash = buffer.toString();
				if (hash.startsWith("00000")) {
					if (part == Part.ONE) {
						password.setCharAt(password.indexOf(" "), hash.charAt(5));
					}
					else {
						String possibleIndex = String.valueOf(hash.charAt(5));
						if (possibleIndex.matches("[0-9]")) {
							int index = Integer.valueOf(possibleIndex);
							if (index < password.length() && password.charAt(index) == ' ') {
								password.setCharAt(index, hash.charAt(6));
							}
						}
					}
				}
				i++;
			}
			return (password.toString());
		}
		catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}

}