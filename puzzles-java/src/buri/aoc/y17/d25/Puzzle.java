package buri.aoc.y17.d25;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 25: The Halting Problem
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(3, Puzzle.getResult(Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3578, result);
	}

	/**
	 * Part 1:
	 * What is the diagnostic checksum it produces once it's working again?
	 */
	public static int getResult(List<String> input) {
		Machine machine = new Machine(input);
		return (machine.run());
	}
}