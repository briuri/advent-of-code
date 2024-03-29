package buri.aoc.y21.d25;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 25: Sea Cucumber
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(58L, 1, false);
		assertRun(513L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the first step on which no sea cucumbers move?
	 */
	protected long runLong(Part part, List<String> input) {
		Map<Pair<Integer>, Character> grid = new HashMap<>();
		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.get(0).length(); x++) {
				grid.put(new Pair<>(x, y), input.get(y).charAt(x));
			}
		}
		int maxX = -1;
		int maxY = -1;
		for (Pair<Integer> point : grid.keySet()) {
			maxX = Math.max(maxX, point.getX());
			maxY = Math.max(maxY, point.getY());
		}

		int step = 0;
		while (true) {
			int changes = 0;
			Map<Pair<Integer>, Character> nextGrid = new HashMap<>();
			for (int y = 0; y <= maxY; y++) {
				for (int x = 0; x <= maxX; x++) {
					Pair<Integer> previous = new Pair<>(x, y);
					Character value = grid.get(previous);
					if (value == '>') {
						int nextX = (x + 1) % (maxX + 1);
						Pair<Integer> next = new Pair<>(nextX, y);
						if (grid.get(next) == '.') {
							changes++;
							nextGrid.put(previous, '.');
							nextGrid.put(next, '>');
						}
					}
				}
			}
			grid.putAll(nextGrid);
			for (int y = 0; y <= maxY; y++) {
				for (int x = 0; x <= maxX; x++) {
					Pair<Integer> previous = new Pair<>(x, y);
					Character value = grid.get(previous);
					if (value == 'v') {
						int nextY = (y + 1) % (maxY + 1);
						Pair<Integer> next = new Pair<>(x, nextY);
						if (grid.get(next) == '.') {
							changes++;
							nextGrid.put(previous, '.');
							nextGrid.put(next, 'v');
						}
					}
				}
			}
			grid.putAll(nextGrid);
			step++;
			if (changes == 0) {
				return (step);
			}
		}
	}
}