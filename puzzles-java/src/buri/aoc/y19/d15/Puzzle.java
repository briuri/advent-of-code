package buri.aoc.y19.d15;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.intcode.Computer;
import org.junit.Test;

import java.util.List;

/**
 * Day 15: Oxygen System
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(280L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(400L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the fewest number of movement commands required to move the repair droid from its starting position to
	 * the location of the oxygen system?
	 *
	 * Part 2:
	 * Use the repair droid to get a complete map of the area. How many minutes will it take to fill with oxygen?
	 */
	protected long runLong(Part part, List<String> input) {
		Maze maze = new Maze();
		Computer droid = new Computer(input);
		maze.explore(droid);
		if (part == Part.ONE) {
			return (maze.getFewestCommands());
		}
		return (maze.getSpreadTime());
	}
}