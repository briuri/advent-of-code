package buri.aoc.y19.d09;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.intcode.Computer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 09: Sensor Boost
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(99, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(1219070632396864L, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(1125899906842624L, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(2870072642L, result);
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(58534L, result);
	}

	/**
	 * Part 1:
	 * What BOOST keycode does it produce?
	 *
	 * Part 2:
	 * What are the coordinates of the distress signal?
	 */
	public static long getResult(Part part, List<String> input) {
		long inputValue = (part == Part.ONE ? 1 : 2);
		Computer computer = new Computer(input);
		computer.getInputs().add(inputValue);
		computer.run();
		return (computer.getLastOutput());
	}
}