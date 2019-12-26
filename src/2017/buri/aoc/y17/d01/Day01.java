package buri.aoc.y17.d01;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;

/**
 * Day 1: Inverse Captcha
 * 
 * @author Brian Uri!
 */
public class Day01 extends BasePuzzle {

	/**
	 * Returns input file unmodified.
	 */
	public static String getInput(int fileIndex) {
		return (readFile("2017/01", fileIndex).get(0));
	}

	/**
	 * The captcha requires you to review a sequence of digits and find the sum of all digits that match another digit
	 * in the list.
	 * 
	 * Part 1:
	 * What is the solution when matching to the next digit in the list?
	 * 
	 * Part 2:
	 * What is the solution when matching to the digit halfway around the circular list?
	 */
	public static int getResult(Part part, String input) {
		return (getSum(getMatchingDigits(part, input)));
	}

	/**
	 * Finds all matching digits, based on Part-specific rules. The list is circular, so the digit after the last digit
	 * is the first digit in the list.
	 */
	private static List<Integer> getMatchingDigits(Part part, String input) {
		List<Integer> matchingDigits = new ArrayList<>();
		int inputLength = input.length();
		for (int i = 0; i < inputLength; i++) {
			int matchingIndex = getMatchingIndex(part, i, inputLength);
			int digit = Character.getNumericValue(input.charAt(i));
			int matchingDigit = Character.getNumericValue(input.charAt(matchingIndex));
			if (digit == matchingDigit) {
				matchingDigits.add(digit);
			}
		}
		return (matchingDigits);
	}

	/**
	 * Calculates the index of the digit based on Part-specific rules.
	 */
	private static int getMatchingIndex(Part part, int i, int inputLength) {
		int increment = (part == Part.ONE) ? 1 : inputLength / 2;
		return ((i + increment) % inputLength);
	}
}