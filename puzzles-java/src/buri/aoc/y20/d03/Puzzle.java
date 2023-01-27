package buri.aoc.y20.d03;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.grid.CharGrid;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.List;

/**
 * Day 03: Toboggan Trajectory
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(7L, 1, false);
		assertRun(167L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(336L, 1, false);
		assertRun(736527114L, 0, true);
	}

	private static final char TREE = '#';

	/**
	 * Part 1:
	 * Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you
	 * encounter?
	 *
	 * Part 2:
	 * What do you get if you multiply together the number of trees encountered on each of the listed slopes?
	 */
	protected long runLong(Part part, List<String> input) {
		CharGrid grid = new CharGrid(new Pair<>(input.get(0).length(), input.size()));
		for (int y = 0; y < grid.getHeight(); y++) {
			for (int x = 0; x < grid.getWidth(); x++) {
				grid.set(x, y, input.get(y).charAt(x));
			}
		}
		int count = getTreeCount(grid, 3, 1);
		if (part == Part.TWO) {
			count = count * getTreeCount(grid, 1, 1) * getTreeCount(grid, 5, 1) * getTreeCount(grid, 7, 1)
				* getTreeCount(grid, 1, 2);
		}
		return (count);
	}

	/**
	 * Follows the pattern on the grid and counts all trees found.
	 */
	protected static int getTreeCount(CharGrid grid, int x, int y) {
		Pair<Integer> pos = new Pair<>(0, 0);
		int count = 0;
		while (pos.getY() + y < grid.getHeight()) {
			pos.setX((pos.getX() + x) % grid.getWidth());
			pos.setY(pos.getY() + y);
			if (grid.get(pos) == TREE) {
				count++;
			}
		}
		return (count);
	}
}