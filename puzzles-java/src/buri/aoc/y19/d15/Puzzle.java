package buri.aoc.y19.d15;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.intcode.Computer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 15: Oxygen System
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(280, result);
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(400, result);
	}

	/**
	 * Part 1:
	 * What is the fewest number of movement commands required to move the repair droid from its starting position to
	 * the location of the oxygen system?
	 *
	 * Part 2:
	 * Use the repair droid to get a complete map of the area. How many minutes will it take to fill with oxygen?
	 */
	public static int getResult(Part part, List<String> input) {
		Maze maze = new Maze();
		Computer droid = new Computer(input);
		maze.explore(droid);
		if (part == Part.ONE) {
			return (maze.getFewestCommands());
		}
		return (maze.getSpreadTime());
	}
}