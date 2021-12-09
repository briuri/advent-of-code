package buri.aoc.y21.d05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.tuple.Pair;

/**
 * Day 05: Hydrothermal Venture
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file as a list of lines.
	 */
	public static List<Line> getInput(int fileIndex) {
		List<Line> list = new ArrayList<>();
		for (String input : readFile(fileIndex)) {
			list.add(new Line(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * At how many points do at least two lines overlap?
	 *
	 * Part 2:
	 * At how many points do at least two lines overlap? (diagonals)
	 */
	public static long getResult(Part part, List<Line> lines) {
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