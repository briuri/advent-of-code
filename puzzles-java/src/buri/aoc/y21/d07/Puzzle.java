package buri.aoc.y21.d07;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 07: The Treachery of Whales
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(37L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(326132L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(168L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(88612508L, result);
	}

	/**
	 * Part 1:
	 * How much fuel must they spend to align to that position?
	 *
	 * Part 2:
	 * How much fuel must they spend to align to that position?
	 */
	public static long getResult(Part part, List<String> input) {
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