package buri.aoc.y21.d07;

import java.util.Arrays;
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
	 * Returns the input file as a list of integers.
	 */
	public static List<Integer> getInput(int fileIndex) {
		 String[] stringInts = readFile(fileIndex).get(0).split(",");
		 return (convertStringsToInts(Arrays.asList(stringInts)));
	}

	/**
	 * Part 1:
	 * How much fuel must they spend to align to that position?
	 *
	 * Part 2:
	 * How much fuel must they spend to align to that position?
	 */
	public static long getResult(Part part, List<Integer> input) {
		int maxPosition = Integer.MIN_VALUE;
		for (Integer crab : input) {
			maxPosition = Math.max(maxPosition, crab);
		}

		int minFuel = Integer.MAX_VALUE;
		for (int i = 0; i <= maxPosition; i++) {
			int testFuel = 0;
			for (Integer crab : input) {
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