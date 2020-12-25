package buri.aoc.y16.d16;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 16: Dragon Checksum
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the correct checksum?
	 * 
	 * Part 2:
	 * The second disk you have to fill has length 35651584. Again using the initial state in your puzzle input, what is
	 * the correct checksum for this disk?
	 */
	public static String getResult(Part part, String input, int diskLength) {
		String curve = generateCurve(input, diskLength);
		return (getChecksum(curve));
	}

	/**
	 * Generates a dragon curve and returns the first segment (up to diskLength).
	 * 
	 * Start with an appropriate initial state (your puzzle input). Then, so long as you don't have enough data yet to
	 * fill the disk, repeat the following steps:
	 * 
	 * - Call the data you have at this point "a".
	 * - Make a copy of "a"; call this copy "b".
	 * - Reverse the order of the characters in "b".
	 * - In "b", replace all instances of 0 with 1 and all 1s with 0.
	 * - The resulting data is "a", then a single 0, then "b".
	 */
	public static String generateCurve(String input, int diskLength) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(input).append("0");
		for (int i = input.length() - 1; i >= 0; i--) {
			char value = input.charAt(i);
			buffer.append(value == '1' ? '0' : '1');
		}
		if (buffer.length() < diskLength) {
			return (generateCurve(buffer.toString(), diskLength));
		}
		return (buffer.substring(0, diskLength));
	}

	/**
	 * Generates a checksum for a string value.
	 * 
	 * - Consider each non-overlapping pair of characters in the input data.
	 * - If the two characters match (00 or 11), the next checksum character is a 1.
	 * - If the characters do not match (01 or 10), the next checksum character is a 0.
	 * 
	 * This should produce a new string which is exactly half as long as the original. If the length of the checksum is
	 * even, repeat the process until you end up with a checksum with an odd length.
	 */
	public static String getChecksum(String input) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < input.length(); i = i + 2) {
			int char1 = input.charAt(i);
			int char2 = input.charAt(i + 1);
			buffer.append(char1 == char2 ? '1' : '0');
		}
		if (buffer.length() % 2 == 0) {
			return (getChecksum(buffer.toString()));
		}
		return (buffer.toString());
	}
}