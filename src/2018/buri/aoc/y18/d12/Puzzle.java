package buri.aoc.y18.d12;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 12: Subterranean Sustainability
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * After 20 generations, what is the sum of the numbers of all pots which contain a plant?
	 * 
	 * Part 2:
	 * After fifty billion (50000000000) generations, what is the sum of the numbers of all pots which contain a plant?
	 */
	public static long getResult(Part part, List<String> input) {
		Pots pots = new Pots(input);
		final int iterations = (part == Part.ONE ? 20 : 125);
		for (int i = 0; i < iterations; i++) {
			pots.grow();
		}
		if (part == Part.ONE) {
			return (pots.getGrowthSum());
		}

		// Part TWO
		// Visual inspection: After 125 iterations, sums are just 109 greater than the last.
		long sum = pots.getGrowthSum() + (109 * (50000000000L - pots.getGeneration()));
		return (sum);
	}
}