package buri.aoc.y20.d03;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Day 03: Toboggan Trajectory
 *
 * @author Brian Uri!
 */
public class Day03 extends BasePuzzle {

	private static final char TREE = '#';

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you
	 * encounter?
	 *
	 * Part 2:
	 * What do you get if you multiply together the number of trees encountered on each of the listed slopes?
	 */
	public static int getResult(Part part, List<String> input) {
		CharGrid grid = new CharGrid(new Pair(input.get(0).length(), input.size()));
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