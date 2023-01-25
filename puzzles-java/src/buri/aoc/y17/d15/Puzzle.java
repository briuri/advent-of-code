package buri.aoc.y17.d15;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 15: Dueling Generators
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(588, Puzzle.getResult(Part.ONE, 65, 8921));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, 634, 301);
		toConsole(result);
		assertEquals(573, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(309, Puzzle.getResult(Part.TWO, 65, 8921));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, 634, 301);
		toConsole(result);
		assertEquals(294, result);
	}

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