package buri.aoc.y17.d15;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 15: Dueling Generators
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(588L, 1, false);
		assertRun(573L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(309L, 1, false);
		assertRun(294L, 0, true);
	}

	/**
	 * Part 1:
	 * After 40 million pairs, what is the judge's final count?
	 *
	 * Part 2:
	 * After 5 million pairs, but using this new generator logic, what is the judge's final count?
	 */
	protected long runLong(Part part, List<String> input) {
		int startA = Integer.parseInt(input.get(0).split("with ")[1]);
		int startB = Integer.parseInt(input.get(1).split("with ")[1]);
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