package buri.aoc.y19.d04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.CharFrequency;

/**
 * Day 04: Secure Container
 * 
 * @author Brian Uri!
 */
public class Day04 extends BasePuzzle {

	/**
	 * Returns the input file as two integers.
	 */
	public static List<Integer> getInput(int fileIndex) {
		String line = readFile(fileIndex).get(0);
		List<Integer> range = new ArrayList<>();
		String[] tokens = line.split("-");
		range.add(Integer.valueOf(tokens[0]));
		range.add(Integer.valueOf(tokens[1]));
		return (range);
	}

	/**
	 * Part 1:
	 * How many different passwords within the range given in your puzzle input meet these criteria?
	 * 
	 * Part 2:
	 * How many different passwords within the range given in your puzzle input meet all of the criteria?
	 */
	public static int getResult(Part part, List<Integer> range) {
		int min = range.get(0);
		int max = range.get(1);
		int count = 0;
		for (int testPassword = min; testPassword < max + 1; testPassword++) {
			if (isValidPassword(part, String.valueOf(testPassword))) {
				count++;
			}
		}
		return (count);
	}

	/**
	 * Validates a password against some or all criteria, depending on the Part.
	 * 
	 * Both Parts:
	 * The value is within the range given in your puzzle input. (tested implicitly in main loop)
	 * It is a six-digit number.
	 * Going from left to right, the digits never decrease; they only ever increase or stay the same.
	 * Two adjacent digits are the same.
	 * 
	 * Part TWO only:
	 * The two adjacent matching digits are not part of a larger group of matching digits.
	 */
	public static boolean isValidPassword(Part part, String password) {
		boolean isSixDigit = (password.length() == 6);

		char[] passwordArray = password.toCharArray();
		Arrays.sort(passwordArray);
		String sorted = new String(passwordArray);
		boolean neverDecreases = (password.equals(sorted));

		boolean hasTwoAdjacent = false;
		for (int i = 1; i < password.length(); i++) {
			if (password.charAt(i) == password.charAt(i - 1)) {
				hasTwoAdjacent = true;
			}
		}

		if (part == Part.TWO && hasTwoAdjacent) {
			CharFrequency frequency = new CharFrequency(password);
			hasTwoAdjacent = frequency.containsFrequency(2);
		}

		return (isSixDigit && neverDecreases && hasTwoAdjacent);
	}

}