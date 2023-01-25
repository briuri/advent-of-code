package buri.aoc.y16.d12;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 12: Leonardo's Monorail
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(42, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(318007, result);
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(9227661, result);
	}

	/**
	 * Part 1:
	 * After executing the assembunny code in your puzzle input, what value is left in register a?
	 *
	 * Part 2:
	 * If you instead initialize register c to be 1, what value is now left in register a?
	 */
	public static long getResult(Part part, List<String> input) {
		Registers registers = new Registers(part, input);
		registers.process();
		if (part == Part.ONE) {
			return (registers.get("a"));
		}

		// Part TWO
		return (registers.get("a"));
	}
}