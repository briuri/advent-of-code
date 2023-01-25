package buri.aoc.y19.d02;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.data.intcode.Computer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 02: 1202 Program Alarm
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(3500L, Puzzle.getPart1Result(Puzzle.getInput(1), true));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getPart1Result(Puzzle.getInput(0), false);
		toConsole(result);
		assertEquals(4930687L, result);
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getPart2Result(Puzzle.getInput(0));
		toConsole(result);
		assertEquals(5335, result);
	}

	/**
	 * Part 1:
	 * What value is left at position 0 after the program halts?
	 */
	public static long getPart1Result(List<String> input, boolean isExample) {
		Computer computer = isExample ? new Computer(input) : new Computer(input, 12L, 2L);
		computer.run();
		return (computer.get(0));
	}

	/**
	 * Part 2:
	 * Find the input noun and verb that cause the program to produce the output 19690720. What is 100 * noun + verb?
	 */
	public static long getPart2Result(List<String> input) {
		for (long noun = 0; noun < 100; noun++) {
			for (long verb = 0; verb < 100; verb++) {
				Computer computer = new Computer(input, noun, verb);
				computer.run();
				if (computer.get(0) == 19690720L) {
					return (100 * noun + verb);
				}
			}
		}
		throw new RuntimeException("No inputs resulted in expected value.");
	}
}