package buri.aoc.y16.d05;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.MD5Hash;

/**
 * Day 5: How About a Nice Game of Chess?
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * The eight-character password for the door is generated one character at a time by finding the MD5 hash of some
	 * Door ID and an increasing integer index (starting with 0). A hash indicates the next character in the password if
	 * its hexadecimal representation starts with five zeroes.
	 * 
	 * Part 1:
	 * The 6th character in the hash is the next character in the password. What is the password?
	 * 
	 * Part 2:
	 * The 6th character is the position (0-7) and the 7th character is that character in the password. Use only the
	 * first result for each position and ignore invalid positions. What is the password?
	 */
	public static String getResult(Part part, String input) {
		MD5Hash hasher = new MD5Hash();

		int iteration = 0;
		// Represent password as 8 blanks, completed when all are filled in.
		StringBuffer password = new StringBuffer("        ");
		while (password.toString().indexOf(" ") != -1) {
			String hash = hasher.getHash(input + iteration);
			if (hash.startsWith("00000")) {
				if (part == Part.ONE) {
					// Fill in password in order.
					password.setCharAt(password.indexOf(" "), hash.charAt(5));
				}
				else {
					// Fill in specific characters in the password.
					String possibleIndex = String.valueOf(hash.charAt(5));
					if (possibleIndex.matches("[0-9]")) {
						int index = Integer.valueOf(possibleIndex);
						if (index < password.length() && password.charAt(index) == ' ') {
							password.setCharAt(index, hash.charAt(6));
						}
					}
				}
			}
			iteration++;
		}
		return (password.toString());
	}
}