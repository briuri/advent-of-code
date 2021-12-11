package buri.aoc.y19.d02;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.data.intcode.Computer;

/**
 * Day 02: 1202 Program Alarm
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

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