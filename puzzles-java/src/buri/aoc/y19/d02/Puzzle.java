package buri.aoc.y19.d02;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.intcode.Computer;
import org.junit.Test;

import java.util.List;

/**
 * Day 02: 1202 Program Alarm
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(3500L, 1, false);
		assertRun(4930687L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(5335L, 0, true);
	}

	/**
	 * Part 1:
	 * What value is left at position 0 after the program halts?
	 *
	 * Part 2:
	 * Find the input noun and verb that cause the program to produce the output 19690720. What is 100 * noun + verb?
	 */
	public long runLong(Part part, List<String> input) {
		if (part == Part.ONE) {
			boolean isExample = input.get(0).length() < 40;
			Computer computer = isExample ? new Computer(input) : new Computer(input, 12L, 2L);
			computer.run();
			return (computer.get(0));
		}

		// Part Two
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