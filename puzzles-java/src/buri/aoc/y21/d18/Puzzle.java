package buri.aoc.y21.d18;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 18: Snailfish
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(4140L, 1, false);
		assertRun(4641L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(3993L, 1, false);
		assertRun(4624L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the magnitude of the final sum?
	 *
	 * Part 2:
	 * What is the largest magnitude of any sum of two different snailfish numbers from the homework assignment?
	 */
	protected long runLong(Part part, List<String> input) {
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