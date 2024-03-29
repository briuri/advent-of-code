package buri.aoc.y19.d04;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.CharFrequency;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Day 04: Secure Container
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertTrue(Puzzle.isValidPassword(Part.ONE, "111111"));
		assertFalse(Puzzle.isValidPassword(Part.ONE, "223450"));
		assertFalse(Puzzle.isValidPassword(Part.ONE, "123789"));
		assertRun(1929L, 0, true);
	}
	@Test
	public void testPart2() {
		assertTrue(Puzzle.isValidPassword(Part.TWO, "112233"));
		assertFalse(Puzzle.isValidPassword(Part.TWO, "123444"));
		assertTrue(Puzzle.isValidPassword(Part.TWO, "111122"));
		assertRun(1306L, 0, true);
	}

	/**
	 * Part 1:
	 * How many different passwords within the range given in your puzzle input meet these criteria?
	 *
	 * Part 2:
	 * How many different passwords within the range given in your puzzle input meet all of the criteria?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Integer> range = new ArrayList<>();
		String[] tokens = input.get(0).split("-");
		range.add(Integer.valueOf(tokens[0]));
		range.add(Integer.valueOf(tokens[1]));

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
				break;
			}
		}

		if (part == Part.TWO && hasTwoAdjacent) {
			CharFrequency frequency = new CharFrequency(password);
			hasTwoAdjacent = frequency.containsFrequency(2);
		}

		return (isSixDigit && neverDecreases && hasTwoAdjacent);
	}

}