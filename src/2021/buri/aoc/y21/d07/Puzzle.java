package buri.aoc.y21.d07;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 07: The Treachery of Whales
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

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