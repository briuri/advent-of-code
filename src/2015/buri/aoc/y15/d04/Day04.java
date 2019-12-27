package buri.aoc.y15.d04;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.MD5Hash;

/**
 * Day 4: The Ideal Stocking Stuffer
 * 
 * @author Brian Uri!
 */
public class Day04 extends BasePuzzle {

	/**
	 * Part 1:
	 * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least five zeroes.
	 * 
	 * Part 2:
	 * Now find one that starts with six zeroes.
	 */
	public static int getResult(Part part, String input) {
		MD5Hash hasher = new MD5Hash();
		int number = 0;
		String prefix = (part == Part.ONE ? "00000" : "000000");
		while (true) {
			if (hasher.getHash(input + number).startsWith(prefix)) {
				return (number);
			}
			number++;
		}
	}
}