package buri.aoc.y17.d15;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * The generators, called generator A and generator B, are trying to agree on a sequence of numbers. However, one of
 * them is malfunctioning, and so the sequences don't always match.
 * 
 * As they do this, a judge waits for each of them to generate its next value, compares the lowest 16 bits of both
 * values, and keeps track of the number of times those parts of the values match.
 * 
 * The generators both work on the same principle. To create its next value, a generator will take the previous value it
 * produced, multiply it by a factor (generator A uses 16807; generator B uses 48271), and then keep the remainder of
 * dividing that resulting product by 2147483647. That final remainder is the value it produces next.
 * 
 * @author Brian Uri!
 */
public class Day15 extends Puzzle {

	/**
	 * Part 1:
	 * After 40 million pairs, what is the judge's final count?
	 * 
	 * Part 2:
	 * After 5 million pairs, but using this new generator logic, what is the judge's final count?
	 */
	public static int getResult(Part part, int judges, long startA, long startB) {
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