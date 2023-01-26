package buri.aoc.y19.d09;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.intcode.Computer;
import org.junit.Test;

import java.util.List;

/**
 * Day 09: Sensor Boost
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(99L, 1, false);
		assertRun(1219070632396864L, 2, false);
		assertRun(1125899906842624L, 3, false);
		assertRun(2870072642L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(58534L, 0, true);
	}

	/**
	 * Part 1:
	 * What BOOST keycode does it produce?
	 *
	 * Part 2:
	 * What are the coordinates of the distress signal?
	 */
	protected long runLong(Part part, List<String> input) {
		long inputValue = (part == Part.ONE ? 1 : 2);
		Computer computer = new Computer(input);
		computer.getInputs().add(inputValue);
		computer.run();
		return (computer.getLastOutput());
	}
}