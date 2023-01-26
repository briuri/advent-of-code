package buri.aoc.y19.d20;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 20: Donut Maze
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(23L, 1, false);
		assertRun(58L, 2, false);
		assertRun(684L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(26L, 1, false);
		assertRun(396L, 3, false);
		assertRun(7758L, 0, true);
	}

	/**
	 * Part 1:
	 * In your maze, how many steps does it take to get from the open tile marked AA to the open tile marked ZZ?
	 *
	 * Part 2:
	 * In your maze, when accounting for recursion, how many steps does it take to get from the open tile marked AA to
	 * the open tile marked ZZ, both at the outermost layer?
	 */
	protected long runLong(Part part, List<String> input) {
		Maze maze = new Maze(input);
		return (maze.getSteps(part));
	}
}