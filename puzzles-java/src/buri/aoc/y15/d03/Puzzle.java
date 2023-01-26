package buri.aoc.y15.d03;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.Direction;
import buri.aoc.common.data.grid.IntGrid;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.List;

/**
 * Day 3: Perfectly Spherical Houses in a Vacuum
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(2565L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(2639L, 0, true);
	}

	/**
	 * Part 1:
	 * How many houses receive at least one present?
	 *
	 * Part 2:
	 * This year, how many houses receive at least one present?
	 */
	protected long runLong(Part part, List<String> input) {
		IntGrid grid = new IntGrid(new Pair(500, 500));
		Pair<Integer> santa = grid.getCenterPosition();
		Pair<Integer> robosanta = grid.getCenterPosition();
		grid.set(santa, grid.get(santa) + 1);
		grid.set(robosanta, grid.get(santa) + 1);

		// Process directions.
		for (int i = 0; i < input.get(0).length(); i++) {
			Direction direction = Direction.getDirectionFor(input.get(0).charAt(i));
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