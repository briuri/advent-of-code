package buri.aoc.y17.d08;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 8: I Heard You Like Registers
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(Long.valueOf(1), Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4888L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(Long.valueOf(10), Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(7774L, result);
	}

	/**
	 * Part 1:
	 * What is the largest value in any register after completing the instructions?
	 *
	 * Part 2:
	 * What is the largest value held in any register during this process?
	 */
	public static Long getResult(Part part, List<String> input) {
		List<RegisterInstruction> instructions = new ArrayList<>();
		for (String line : input) {
			instructions.add(new RegisterInstruction(line));
		}

		Registers registers = new Registers();
		registers.process(instructions);
		return (registers.getLargestValue(part));
	}
}