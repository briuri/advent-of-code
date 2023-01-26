package buri.aoc.y21.d05;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 05: Hydrothermal Venture
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(5L, 1, false);
		assertRun(8622L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(12L, 1, false);
		assertRun(22037L, 0, true);
	}

	/**
	 * Part 1:
	 * At how many points do at least two lines overlap?
	 *
	 * Part 2:
	 * At how many points do at least two lines overlap? (diagonals)
	 */
	protected long runLong(Part part, List<String> input) {
		List<Line> lines = new ArrayList<>();
		for (String line : input) {
			lines.add(new Line(line));
		}

		Map<Pair<Integer>, Integer> grid = new HashMap<>();
		for (Line line : lines) {
			if (part == Part.ONE && line.isDiagonal()) {
				continue;
			}
			for (Pair<Integer> point : line.getPoints()) {
				grid.put(point, grid.getOrDefault(point, 0) + 1);
			}
		}
		long dangerZones = 0;
		for (Pair<Integer> point : grid.keySet()) {
			if (grid.get(point) >= 2) {
				dangerZones++;
			}
		}
		return (dangerZones);
	}
}