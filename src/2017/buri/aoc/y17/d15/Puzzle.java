package buri.aoc.y17.d15;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 15: Dueling Generators
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * After 40 million pairs, what is the judge's final count?
	 * 
	 * Part 2:
	 * After 5 million pairs, but using this new generator logic, what is the judge's final count?
	 */
	public static int getResult(Part part, long startA, long startB) {
		final int judges = part == Part.ONE ? 40000000 : 5000000;
		Generator a = Generator.getInstance("A", startA);
		Generator b = Generator.getInstance("B", startB);
		int matches = 0;
		for (int i = 0; i < judges; i++) {
			String valueA = a.nextValue(part);
			String valueB = b.nextValue(part);
			if (valueA.equals(valueB)) {
				matches++;
			}
		}
		return (matches);
	}
}