package buri.aoc.y21.d11;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 11: Dumbo Octopus
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many total flashes are there after 100 steps?
	 *
	 * Part 2:
	 * What is the first step during which all octopuses flash?
	 */
	public static long getResult(Part part, List<String> input) {
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