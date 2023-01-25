package buri.aoc.y19.d20;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 20: Donut Maze
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(23, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(58, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(684, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(26, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
		assertEquals(396, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(7758, result);
	}

	/**
	 * Part 1:
	 * In your maze, how many steps does it take to get from the open tile marked AA to the open tile marked ZZ?
	 *
	 * Part 2:
	 * In your maze, when accounting for recursion, how many steps does it take to get from the open tile marked AA to
	 * the open tile marked ZZ, both at the outermost layer?
	 */
	public static int getResult(Part part, List<String> input) {
		Maze maze = new Maze(input);
		return (maze.getSteps(part));
	}
}