package buri.aoc.y15.d18;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day Day 18: Like a GIF For Your Yard
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(4, Puzzle.getResult(Part.ONE, 4, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, 100, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(821, result);
	}
	@Test
	public void testPart2Examples() {
		assertEquals(17, Puzzle.getResult(Part.TWO, 5, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, 100, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(886, result);
	}

	/**
	 * Part 1:
	 * Given your initial configuration, how many lights are on after 100 steps?
	 *
	 * Part 2:
	 * Given your initial configuration, but with the four corners always in the on state, how many lights are on after
	 * 100 steps?
	 */
	public static int getResult(Part part, int steps, List<String> input) {
		Grid grid = new Grid(input);
		grid.animate(part, steps);
		return (grid.getLit());
	}
}