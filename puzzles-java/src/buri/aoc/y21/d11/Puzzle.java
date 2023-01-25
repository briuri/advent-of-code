package buri.aoc.y21.d11;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 11: Dumbo Octopus
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {


	@Test
	public void testPart1Examples() {
		assertEquals(1656L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1705L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(195L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(265L, result);
	}

	/**
	 * Part 1:
	 * How many total flashes are there after 100 steps?
	 *
	 * Part 2:
	 * What is the first step during which all octopuses flash?
	 */
	public static long getResult(Part part, List<String> input) {
		Grid grid = new Grid(input);
		if (part == Part.ONE) {
			for (int i = 0; i < 100; i++) {
				grid.nextTurn();
			}
			return (grid.getFlashCount());
		}

		int round = 0;
		while (grid.getSum() != 0) {
			grid.nextTurn();
			round++;
		}
		return (round);
	}
}