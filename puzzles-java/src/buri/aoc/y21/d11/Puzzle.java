package buri.aoc.y21.d11;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 11: Dumbo Octopus
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(1656L, 1, false);
		assertRun(1705L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(195L, 1, false);
		assertRun(265L, 0, true);
	}

	/**
	 * Part 1:
	 * How many total flashes are there after 100 steps?
	 *
	 * Part 2:
	 * What is the first step during which all octopuses flash?
	 */
	protected long runLong(Part part, List<String> input) {
		Grid grid = new Grid(input);
		if (part == Part.ONE) {
			for (int i = 0; i < 100; i++) {
				grid.nextTurn();
			}
			return (grid.getFlashCount());
		}

		int round = 0;
		while (grid.getSum() != 0) {
			grid.nextTurn();
			round++;
		}
		return (round);
	}
}