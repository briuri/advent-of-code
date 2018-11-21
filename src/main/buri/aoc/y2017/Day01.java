package buri.aoc.y2017;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The captcha requires you to review a sequence of digits (your puzzle input)
 * and find the sum of all digits identified with a matching strategy. The list
 * is circular, so the digit after the last digit is the first digit in the
 * list.
 * 
 * @author Brian Uri!
 */
public class Day01 {

	private Strategy _strategy;
	private String _input;

	public enum Strategy {
		/* Compare to the next digit in the list. */
		MATCH_NEXT,
		/* Compare to the digit halfway around the circular list. */
		MATCH_HALFWAY
	}

	/**
	 * Constructor, built against a specific input
	 * 
	 * Input is non-null and numeric.
	 * 
	 * @param input
	 *            the string content to run against
	 * @param strategy
	 *            enum value for the matching strategy
	 */
	public Day01(String input, Strategy strategy) throws IOException {
		if (input == null || !input.matches("^[0-9]+$")) {
			throw new IOException("Input must be numeric.");
		}
		if (strategy == Strategy.MATCH_HALFWAY && input.length() % 2 != 0) {
			throw new IOException("Input must have an even length.");
		}
		_input = input;
		_strategy = strategy;
	}

	/**
	 * Calculates the index of the digit based on the matching strategy.
	 * 
	 * @param i
	 *            the current index of the digit being evaluated
	 * @param inputLength
	 *            the length of the input list
	 * @return an index based on the strategy
	 */
	private int getMatchingIndex(int i, int inputLength) {
		// Default to MATCH_NEXT
		int increment = (getStrategy() == Strategy.MATCH_HALFWAY) ? inputLength / 2 : 1;
		return ((i + increment) % inputLength);
	}

	/**
	 * Finds all matching digits, based on matching strategies. The list is
	 * circular, so the digit after the last digit is the first digit in the
	 * list.
	 * 
	 * @return a list of integers
	 */
	public List<Integer> getMatchingDigits() {
		List<Integer> matchingDigits = new ArrayList<>();
		int inputLength = getInput().length();
		for (int i = 0; i < inputLength; i++) {
			int matchingIndex = getMatchingIndex(i, inputLength);
			int digit = Character.getNumericValue(getInput().charAt(i));
			int matchingDigit = Character.getNumericValue(getInput().charAt(matchingIndex));
			if (digit == matchingDigit) {
				matchingDigits.add(digit);
			}
		}
		return (matchingDigits);
	}

	/**
	 * Find the sum of all digits that match the next digit in the list.
	 * 
	 * @return the integer sum
	 */
	public int getSum() {
		int sum = 0;
		for (Integer digit : getMatchingDigits()) {
			sum += digit;
		}
		return (sum);
	}

	/**
	 * Accessor for input
	 */
	public String getInput() {
		return (_input);
	}

	/**
	 * Accessor for the matching strategy
	 */
	private Strategy getStrategy() {
		return (_strategy);
	}
}