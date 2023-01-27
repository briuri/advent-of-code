package buri.aoc.y17.d11;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Day 11: Hex Ed
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(3L, 1, false);
		assertRun(0L, 2, false);
		assertRun(2L, 3, false);
		assertRun(3L, 4, false);
		assertRun(877L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(1622L, 0, true);
	}

	/**
	 * Using cube coordinates as described at:
	 * https://www.redblobgames.com/grids/hexagons/
	 *
	 * Part 1:
	 * Starting where he started, you need to determine the fewest number of steps required to reach him.
	 *
	 * Part 2:
	 * How many steps away is the furthest he ever got from his starting position?
	 */
	protected long runLong(Part part, List<String> input) {
		List<String> data = new ArrayList<>();
		Collections.addAll(data, input.get(0).split(","));

		int x = 0;
		int y = 0;
		int z = 0;
		int max = 0;
		for (String step : data) {
			switch (step) {
				case "nw":
					x = x - 1;
					y = y + 1;
					break;
				case "se":
					x = x + 1;
					y = y - 1;
					break;
				case "n":
					y = y + 1;
					z = z - 1;
					break;
				case "s":
					y = y - 1;
					z = z + 1;
					break;
				case "ne":
					x = x + 1;
					z = z - 1;
					break;
				case "sw":
					x = x - 1;
					z = z + 1;
					break;
			}
			max = Math.max(getDistance(x, y, z), max);
		}
		if (part == Part.ONE) {
			return (getDistance(x, y, z));
		}
		return (max);
	}

	/**
	 * Calculates the distance from the origin.
	 */
	private static int getDistance(int x, int y, int z) {
		return ((Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2);
	}
}