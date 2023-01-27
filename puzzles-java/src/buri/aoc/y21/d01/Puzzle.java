package buri.aoc.y21.d01;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.List;

/**
 * Day 01: Sonar Sweeep
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(7L, 1, false);
		assertRun(1292L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(5L, 1, false);
		assertRun(1262L, 0, true);
	}

	/**
	 * Part 1:
	 * How many measurements are larger than the previous measurement?
	 *
	 * Part 2:
	 * Consider sums of a three-measurement sliding window. How many sums are larger than the previous sum?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Integer> values = PuzzleMath.toInts(input);
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