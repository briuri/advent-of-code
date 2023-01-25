package buri.aoc.y21.d25;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 25: Sea Cucumber
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(58L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(513L, result);
	}

	/**
	 * Part 1:
	 * What is the first step on which no sea cucumbers move?
	 */
	public static long getResult(Part part, List<String> input) {
		Map<Pair<Integer>, Character> grid = new HashMap<>();
		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.get(0).length(); x++) {
				grid.put(new Pair<Integer>(x, y), input.get(y).charAt(x));
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
					Pair<Integer> previous = new Pair<Integer>(x, y);
					Character value = grid.get(previous);
					if (value == '>') {
						int nextX = (x + 1) % (maxX + 1);
						Pair<Integer> next = new Pair<Integer>(nextX, y);
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
					Pair<Integer> previous = new Pair<Integer>(x, y);
					Character value = grid.get(previous);
					if (value == 'v') {
						int nextY = (y + 1) % (maxY + 1);
						Pair<Integer> next = new Pair<Integer>(x, nextY);
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