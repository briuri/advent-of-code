package buri.aoc.y2017;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The captcha requires you to review a sequence of digits (your puzzle input) and find the sum of all digits identified
 * with a matching strategy. The list is circular, so the digit after the last digit is the first digit in the list.
 * 
 * @author Brian Uri!
 */
public class Day01 {

	public enum Strategy {
		/* Part 1: Compare to the next digit in the list. */
		MATCH_NEXT,
		/* Part 2: Compare to the digit halfway around the circular list. */
		MATCH_HALFWAY
	}

	/**
	 * Private to prevent instantiation
	 */
	private Day01() {}

	/**
	 * Loads the input file and returns its contents as a string.
	 * 
	 * @param filePath the project-relative path to the file
	 * @return the input string in the file, with the last linebreak trimmed.
	 * @throws IllegalArgumentException on file I/O issues
	 */
	public static String getCaptchasFromFile(String filePath) {
		try {
			byte[] rawOutput = Files.readAllBytes(Paths.get(filePath));
			return (new String(rawOutput, StandardCharsets.UTF_8.name()).trim());
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file", e);
		}
	}

	/**
	 * Find the sum of all digits that satisfy the matching strategy.
	 * 
	 * @param strategy the matching strategy
	 * @param input the input string
	 * @return the integer sum
	 */
	public static int getSum(Strategy strategy, String input) {
		assertValidInput(strategy, input);
		int sum = 0;
		for (Integer digit : getMatchingDigits(strategy, input)) {
			sum += digit;
		}
		return (sum);
	}

	/**
	 * Validates input, which must be non-null and numeric. When the strategy is MATCH_HALFWAY, the input must also have
	 * an even length.
	 * 
	 * @param strategy the matching strategy
	 * @param input the input string
	 */
	private static void assertValidInput(Strategy strategy, String input) {
		if (input == null || !input.matches("^[0-9]+$")) {
			throw new IllegalArgumentException("Input must be numeric.");
		}
		if (strategy == Strategy.MATCH_HALFWAY && input.length() % 2 != 0) {
			throw new IllegalArgumentException("Input must have an even length.");
		}
	}

	/**
	 * Finds all matching digits, based on matching strategies. The list is circular, so the digit after the last digit
	 * is the first digit in the list.
	 * 
	 * Assumes input has already been validated.
	 * 
	 * @param strategy the matching strategy
	 * @param input the input string
	 * @return a list of integers
	 */
	private static List<Integer> getMatchingDigits(Strategy strategy, String input) {
		List<Integer> matchingDigits = new ArrayList<>();
		int inputLength = input.length();
		for (int i = 0; i < inputLength; i++) {
			int matchingIndex = getMatchingIndex(strategy, i, inputLength);
			int digit = Character.getNumericValue(input.charAt(i));
			int matchingDigit = Character.getNumericValue(input.charAt(matchingIndex));
			if (digit == matchingDigit) {
				matchingDigits.add(digit);
			}
		}
		return (matchingDigits);
	}

	/**
	 * Calculates the index of the digit based on the matching strategy.
	 * 
	 * @param strategy the matching strategy
	 * @param i the current index of the digit being evaluated
	 * @param inputLength the length of the input list
	 * @return an index based on the strategy
	 */
	private static int getMatchingIndex(Strategy strategy, int i, int inputLength) {
		// Default to MATCH_NEXT strategy, since there are only 2 right now.
		int increment = (strategy == Strategy.MATCH_HALFWAY) ? inputLength / 2 : 1;
		return ((i + increment) % inputLength);
	}
}