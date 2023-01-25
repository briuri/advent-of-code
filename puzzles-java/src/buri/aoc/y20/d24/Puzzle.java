package buri.aoc.y20.d24;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 24: Lobby Layout
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(10L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(388L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2208L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4002L, result);
	}

	/**
	 * Part 1:
	 * After all of the instructions have been followed, how many tiles are left with the black side up?
	 *
	 * Part 2:
	 * How many tiles will be black after 100 days?
	 */
	public static long getResult(Part part, List<String> input) {
		Floor floor = new Floor(input);
		if (part == Part.TWO) {
			floor.cycleArt();
		}
		return (floor.getBlackCount());
	}
}