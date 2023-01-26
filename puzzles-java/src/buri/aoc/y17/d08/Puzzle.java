package buri.aoc.y17.d08;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 8: I Heard You Like Registers
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(1L, 1, false);
		assertRun(4888L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(10L, 1, false);
		assertRun(7774L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the largest value in any register after completing the instructions?
	 *
	 * Part 2:
	 * What is the largest value held in any register during this process?
	 */
	protected long runLong(Part part, List<String> input) {
		List<RegisterInstruction> instructions = new ArrayList<>();
		for (String line : input) {
			instructions.add(new RegisterInstruction(line));
		}

		Registers registers = new Registers();
		registers.process(instructions);
		return (registers.getLargestValue(part));
	}
}