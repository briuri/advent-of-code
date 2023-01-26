package buri.aoc.y21.d07;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Day 07: The Treachery of Whales
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(37L, 1, false);
		assertRun(326132L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(168L, 1, false);
		assertRun(88612508L, 0, true);
	}

	/**
	 * Part 1:
	 * How much fuel must they spend to align to that position?
	 *
	 * Part 2:
	 * How much fuel must they spend to align to that position?
	 */
	protected long runLong(Part part, List<String> input) {
		String[] stringInts = input.get(0).split(",");
		List<Integer> values = convertStringsToInts(Arrays.asList(stringInts));

		int minFuel = Integer.MAX_VALUE;
		for (int i = 0; i <= Collections.max(values); i++) {
			int testFuel = 0;
			for (Integer crab : values) {
				int distance = Math.abs(crab - i);
				int fuelCost = 1;
				for (int j = 0; j < distance; j++) {
					testFuel += fuelCost;
					if (part == Part.TWO) {
						fuelCost++;
					}
				}
			}
			minFuel = Math.min(testFuel, minFuel);
		}
		return (minFuel);
	}
}