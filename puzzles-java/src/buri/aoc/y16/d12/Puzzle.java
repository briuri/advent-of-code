package buri.aoc.y16.d12;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 12: Leonardo's Monorail
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(42L, 1, false);
		assertRun(318007L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(9227661L, 0, true);
	}

	/**
	 * Part 1:
	 * After executing the assembunny code in your puzzle input, what value is left in register a?
	 *
	 * Part 2:
	 * If you instead initialize register c to be 1, what value is now left in register a?
	 */
	protected long runLong(Part part, List<String> input) {
		Registers registers = new Registers(part, input);
		registers.process();
		if (part == Part.ONE) {
			return (registers.get("a"));
		}

		// Part TWO
		return (registers.get("a"));
	}
}