package buri.aoc.y19.d02;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.y19.intcode.Computer;

/**
 * Day 02: 1202 Program Alarm
 * 
 * @author Brian Uri!
 */
public class Day02 extends BasePuzzle {

	/**
	 * Returns the input file as a list of longs
	 */
	public static List<Long> getInput(int fileIndex) {
		List<Long> list = new ArrayList<>();
		for (String input : readFile("2019/02", fileIndex).get(0).split(",")) {
			list.add(Long.valueOf(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * What value is left at position 0 after the program halts?
	 */
	public static long getPart1Result(List<Long> program, boolean isExample) {
		Computer computer = isExample ? new Computer(program) : new Computer(program, 12L, 2L);
		computer.run();
		return (computer.get(0));
	}

	/**
	 * Part 2:
	 * Find the input noun and verb that cause the program to produce the output 19690720. What is 100 * noun + verb?
	 */
	public static long getPart2Result(List<Long> program) {
		for (long noun = 0; noun < 100; noun++) {
			for (long verb = 0; verb < 100; verb++) {
				Computer computer = new Computer(program, noun, verb);
				computer.run();
				if (computer.get(0) == 19690720) {
					return (100 * noun + verb);
				}
			}
		}
		throw new RuntimeException("No inputs resulted in expected value.");
	}
}