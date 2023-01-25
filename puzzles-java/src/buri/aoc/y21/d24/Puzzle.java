package buri.aoc.y21.d24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 24: Arithmetic Logic Unit
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(98998519596997L, result);
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(31521119151421L, result);
	}

	// Unique parts of each digit processing algorithm, extracted from the puzzle input.
	private final static int[] STEP_5_AMOUNTS = new int[] { 1, 1, 1, 1, 26, 26, 1, 26, 1, 26, 1, 26, 26, 26 };
	private final static int[] STEP_6_AMOUNTS = new int[] { 13, 11, 11, 10, -14, -4, 11, -3, 12, -12, 13, -12, -15, -12 };
	private final static int[] STEP_16_AMOUNTS = new int[] { 10, 16, 0, 13, 7, 11, 11, 10, 16, 8, 15, 2, 5, 10 };

	// Work backwards from 14th digit to create an upper bound for Z -- increase each time we have a 26 in STEP_5_AMOUNTS
	// private final static int[] MAX_EXPONENT = new int[] { 7, 7, 7, 7, 7, 6, 5, 5, 4, 4, 3, 3, 2, 1};
	// Store the math for the upper Z bound so we don't have to do exponential math during the validation.
	private final static long[] MAX_Z = new long[] { 8031810176L, 8031810176L, 8031810176L, 8031810176L, 8031810176L, 308915776L, 11881376L,
		11881376L, 456976L, 456976L, 17576L, 17576L, 676L, 26L };

	/**
	 * Part 1:
	 * What is the largest model number accepted by MONAD?
	 *
	 * Part 2:
	 * What is the smallest model number accepted by MONAD?
	 */
	public static long getResult(Part part, List<String> input) {
		List<Long> numbers = new ArrayList<>();
		for (String number : getValidNumbers(1, 0)) {
			numbers.add(Long.valueOf(number));
		}
		return (part == Part.ONE ? Collections.max(numbers) : Collections.min(numbers));
	}

	/**
	 * Condensed version of ALU program.
	 */
	private static Long getZForDigit(int w, int digitIndexOneBased, Long zPrevious) {
		int x = (int) (zPrevious % 26) + STEP_6_AMOUNTS[digitIndexOneBased - 1];
		Long z = zPrevious / STEP_5_AMOUNTS[digitIndexOneBased - 1];
		if (w != x) {
			z = z * 26 + (w + STEP_16_AMOUNTS[digitIndexOneBased - 1]);
		}
		return (z);
	}

	/**
	 * Recursively seeks the valid model numbers.
	 */
	private static List<String> getValidNumbers(int digitIndexOneBased, long zPrevious) {
		List<String> numbers = new ArrayList<>();
		// Reached the end
		if (digitIndexOneBased > 14) {
			// Found a number with 14 valid digits. Return empty string so a new number is concatenated.
			if (zPrevious == 0) {
				numbers.add("");
			}
		}
		// Somewhere in the middle. Keep z constrained for smaller search space.
		else if (zPrevious <= MAX_Z[digitIndexOneBased - 1]) {
			for (int w = 1; w <= 9; w++) {
				long z = getZForDigit(w, digitIndexOneBased, zPrevious);
				for (String number : getValidNumbers(digitIndexOneBased + 1, z)) {
					numbers.add(w + number);
				}
			}
		}
		return (numbers);
	}
}