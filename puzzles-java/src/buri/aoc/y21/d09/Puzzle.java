package buri.aoc.y21.d09;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 09: Smoke Basin
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(15L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(468L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(1134L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1280496L, result);
	}

	/**
	 * Part 1:
	 * What is the sum of the risk levels of all low points on your heightmap?
	 *
	 * Part 2:
	 * What do you get if you multiply together the sizes of the three largest basins?
	 */
	public static long getResult(Part part, List<String> input) {
		HeightMap grid = new HeightMap(input);
		if (part == Part.ONE) {
			return (grid.getRisk());
		}
		return (grid.getBasinProduct());
	}
}