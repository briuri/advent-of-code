package buri.aoc.y19.d02;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Puzzle;

/**
 * Day 02: 1202 Program Alarm
 * 
 * @author Brian Uri!
 */
public class Day02 extends Puzzle {

	/**
	 * Returns the input file as a list of integers
	 */
	public static List<Integer> getInput(int fileIndex) {
		List<Integer> list = new ArrayList<>();
		for (String input : readFile("2019/02", fileIndex).get(0).split(",")) {
			list.add(Integer.valueOf(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * What value is left at position 0 after the program halts?
	 */
	public static int getPart1Result(List<Integer> input, boolean isExample) {
		Memory memory = isExample ? new Memory(input) : new Memory(input, 12, 2);
		return (memory.run());
	}

	/**
	 * Part 2:
	 * Find the input noun and verb that cause the program to produce the output 19690720. What is 100 * noun + verb?
	 */
	public static int getPart2Result(List<Integer> input) {
		for (int noun = 0; noun < 100; noun++) {
			for (int verb = 0; verb < 100; verb++) {
				Memory memory = new Memory(input, noun, verb);
				if (memory.run() == 19690720) {
					return (100 * noun + verb);
				}
			}
		}
		throw new RuntimeException("No inputs resulted in expected value.");
	}
}