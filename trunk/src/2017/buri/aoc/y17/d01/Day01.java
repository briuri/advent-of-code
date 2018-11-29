package buri.aoc.y17.d01;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * The captcha requires you to review a sequence of digits (your puzzle input) and find the sum of all digits identified
 * by the rules of the specified Part. The list is circular, so the digit after the last digit is the first digit in the
 * list.
 * 
 * @author Brian Uri!
 */
public class Day01 extends Puzzle {
	
	/**
	 * Input: String of digits on one line
	 * Output: 1 String of digits, with last linebreak trimmed.
	 */
	public static String getInput(int fileIndex) {
		return (readFile("2017/01", fileIndex).get(0));
	}	
	
	/**
	 * Find the sum of all digits that satisfy Part-specific rules.
	 */
	public static int getSum(Part part, String input) {
		assertValidInput(part, input);
		int sum = 0;
		for (Integer digit : getMatchingDigits(part, input)) {
			sum += digit;
		}
		return (sum);
	}

	/**
	 * Validates input, which must be non-null and numeric. For Part TWO, the input must also have
	 * an even length.
	 */
	private static void assertValidInput(Part part, String input) {
		if (input == null || !input.matches("^[0-9]+$")) {
			throw new IllegalArgumentException("Input must be numeric.");
		}
		if (part == Part.TWO && input.length() % 2 != 0) {
			throw new IllegalArgumentException("Input must have an even length.");
		}
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