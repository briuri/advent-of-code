package buri.aoc.y21.d05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 05: Hydrothermal Venture
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(5L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(8622L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(12L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(22037L, result);
	}

	/**
	 * Part 1:
	 * At how many points do at least two lines overlap?
	 *
	 * Part 2:
	 * At how many points do at least two lines overlap? (diagonals)
	 */
	public static long getResult(Part part, List<String> input) {
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