package buri.aoc.y16.d12;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 12: Leonardo's Monorail
 * 
 * @author Brian Uri!
 */
public class Day12 extends BasePuzzle {

	/**
	 * Returns the input file unmodifed.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * After executing the assembunny code in your puzzle input, what value is left in register a?
	 * 
	 * Part 2:
	 * If you instead initialize register c to be 1, what value is now left in register a?
	 */
	public static long getResult(Part part, List<String> input) {
		Registers registers = new Registers(part, input);
		registers.process();
		if (part == Part.ONE) {
			return (registers.get("a"));
		}

		// Part TWO
		return (registers.get("a"));
	}
}