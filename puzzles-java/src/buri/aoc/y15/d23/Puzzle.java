package buri.aoc.y15.d23;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 23: Opening the Turing Lock
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(255, result);
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(334, result);
	}

	/**
	 * Part 1:
	 * What is the value in register b when the program in your puzzle input is finished executing?
	 *
	 * Part 2:
	 * What is the value in register b after the program is finished executing if register a starts as 1 instead?
	 */
	public static long getResult(Part part, List<String> input) {
		Registers registers = new Registers(part, input);
		registers.process();
		return (registers.get("b"));
	}
}