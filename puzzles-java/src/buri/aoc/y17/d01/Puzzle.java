package buri.aoc.y17.d01;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 1: Inverse Captcha
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(3, Puzzle.getResult(Part.ONE, "1122"));
		assertEquals(4, Puzzle.getResult(Part.ONE, "1111"));
		assertEquals(0, Puzzle.getResult(Part.ONE, "1234"));
		assertEquals(9, Puzzle.getResult(Part.ONE, "91212129"));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(1171, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(6, Puzzle.getResult(Part.TWO, "1212"));
		assertEquals(0, Puzzle.getResult(Part.TWO, "1221"));
		assertEquals(4, Puzzle.getResult(Part.TWO, "123425"));
		assertEquals(12, Puzzle.getResult(Part.TWO, "123123"));
		assertEquals(4, Puzzle.getResult(Part.TWO, "12131415"));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(1024, result);
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
		return (getIntSum(getMatchingDigits(part, input)));
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