package buri.aoc.y21.d01;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 01: Sonar Sweeep
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(7L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1292L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(5L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1262L, result);
	}

	/**
	 * Part 1:
	 * How many measurements are larger than the previous measurement?
	 *
	 * Part 2:
	 * Consider sums of a three-measurement sliding window. How many sums are larger than the previous sum?
	 */
	public static long getResult(Part part, List<String> input) {
		List<Integer> values = convertStringsToInts(input);
		long sum = 0;
		int windowSize = (part == Part.ONE ? 1 : 3);
		for (int i = windowSize; i < values.size(); i++) {
			// In part 2, simplify (B + C + D > A + B + C) to (D > A)
			if (values.get(i) > values.get(i - windowSize)) {
				sum++;
			}
		}
		return (sum);
	}
}