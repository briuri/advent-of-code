package buri.aoc.y15.d03;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.Direction;
import buri.aoc.data.grid.IntGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Day 3: Perfectly Spherical Houses in a Vacuum
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static String getInput(int fileIndex) {
		return (readFile(fileIndex).get(0));
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
		Pair<Integer> santa = grid.getCenterPosition();
		Pair<Integer> robosanta = grid.getCenterPosition();
		grid.set(santa, grid.get(santa) + 1);
		grid.set(robosanta, grid.get(santa) + 1);

		// Process directions.
		for (int i = 0; i < input.length(); i++) {
			Direction direction = Direction.getDirectionFor(input.charAt(i));
			Pair<Integer> active;
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