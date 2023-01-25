package buri.aoc.y18.d22;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 22: Mode Maze
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(114, Puzzle.getResult(Part.ONE, 510, new Pair(10, 10)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, 3879, new Pair(8, 713));
		toConsole(result);
		assertEquals(6323, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(45, Puzzle.getResult(Part.TWO, 510, new Pair(10, 10)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, 3879, new Pair(8, 713));
		toConsole(result);
		assertEquals(982, result);
	}

	/**
	 * Part 1:
	 * What is the total risk level for the smallest rectangle that includes 0,0 and the target's coordinates?
	 *
	 * Part 2:
	 * What is the fewest number of minutes you can take to reach the target?
	 */
	public static int getResult(Part part, int depth, Pair<Integer> target) {
		Maze maze = new Maze(depth, target);
		return (part == Part.ONE ? maze.getRiskLevel() : maze.getMinutes());
	}
}