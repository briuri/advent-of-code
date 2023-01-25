package buri.aoc.y21.d18;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 18: Snailfish
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(4140L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4641L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3993L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4624L, result);
	}

	/**
	 * Part 1:
	 * What is the magnitude of the final sum?
	 *
	 * Part 2:
	 * What is the largest magnitude of any sum of two different snailfish numbers from the homework assignment?
	 */
	public static long getResult(Part part, List<String> input) {
		Number finalSum = new Number(input.get(0), null);
		for (int i = 1; i < input.size(); i++) {
			finalSum = new Number(finalSum, new Number(input.get(i), null));
		}
		if (part == Part.ONE) {
			return (finalSum.getMagnitude());
		}

		long maxMagnitude = Long.MIN_VALUE;
		for (int i = 0; i < input.size(); i++) {
			for (int j = 0; j < input.size(); j++) {
				if (i == j) {
					continue;
				}
				Number sum = new Number(new Number(input.get(i), null), new Number(input.get(j), null));
				maxMagnitude = Math.max(maxMagnitude, sum.getMagnitude());
				sum = new Number(new Number(input.get(j), null), new Number(input.get(i), null));
				maxMagnitude = Math.max(maxMagnitude, sum.getMagnitude());
			}
		}
		return (maxMagnitude);
	}
}