package buri.aoc.y18.d15;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 15: Beverage Bandits
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(27730L, 1, false);
		assertRun(36334L, 2, false);
		assertRun(39514L, 3, false);
		assertRun(27755L, 4, false);
		assertRun(28944L, 5, false);
		assertRun(18740L, 6, false);
		assertRun(190777L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(4988L, 1, false);
		assertRun(31284L, 3, false);
		assertRun(3478L, 4, false);
		assertRun(6474L, 5, false);
		assertRun(1140L, 6, false);
		assertRun(47388L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the outcome of the combat described in your puzzle input?
	 *
	 * Part 2:
	 * After increasing the Elves' attack power until it is just barely enough for them to win without any Elves dying,
	 * what is the outcome of the combat described in your puzzle input?
	 */
	protected long runLong(Part part, List<String> input) {
		if (part == Part.ONE) {
			Grid grid = new Grid(input, 3);
			return (grid.run());
		}

		// Part TWO
		int elfAttackPower = 4;
		while (true) {
			Grid grid = new Grid(input, elfAttackPower);
			int outcome = grid.run();
			if (!grid.getElfDied()) {
				return (outcome);
			}
			elfAttackPower++;
		}
	}
}