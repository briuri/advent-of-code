package buri.aoc.y15.d03;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;
import buri.aoc.data.Direction;
import buri.aoc.data.Pair;
import buri.aoc.data.grid.IntGrid;

/**
 * Day 3: Perfectly Spherical Houses in a Vacuum
 * 
 * @author Brian Uri!
 */
public class Day03 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static String getInput(int fileIndex) {
		return (readFile("2015/03", fileIndex).get(0));
	}

	/**
	 * Part 1:
	 * How many houses receive at least one present?
	 * 
	 * Part 2:
	 * This year, how many houses receive at least one present?
	 */
	public static int getResult(Part part, String input) {
		IntGrid grid = new IntGrid(new Pair(500, 500));
		Pair santa = new Pair(grid.getWidth() / 2, grid.getHeight() / 2);
		Pair robosanta = new Pair(grid.getWidth() / 2, grid.getHeight() / 2);
		grid.set(santa, grid.get(santa) + 1);
		grid.set(robosanta, grid.get(santa) + 1);

		// Process directions.
		for (int i = 0; i < input.length(); i++) {
			Direction direction = Direction.getDirectionFor(input.charAt(i));
			Pair active;
			if (part == Part.ONE) {
				active = santa;
			}
			else {
				active = (i % 2 == 0) ? santa : robosanta;
			}
			active.move(direction);
			grid.set(active, grid.get(active) + 1);
		}

		// Count houses with at least 1 present.
		int houses = 0;
		for (int y = 0; y < grid.getHeight(); y++) {
			for (int x = 0; x < grid.getWidth(); x++) {
				if (grid.get(x, y) > 0) {
					houses++;
				}
			}
		}
		return (houses);
	}
}