package buri.aoc.y15.d23;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 23: Opening the Turing Lock
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(255L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(334L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the value in register b when the program in your puzzle input is finished executing?
	 *
	 * Part 2:
	 * What is the value in register b after the program is finished executing if register a starts as 1 instead?
	 */
	protected long runLong(Part part, List<String> input) {
		Registers registers = new Registers(part, input);
		registers.process();
		return (registers.get("b"));
	}
}