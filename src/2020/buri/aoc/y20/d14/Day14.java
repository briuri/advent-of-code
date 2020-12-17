package buri.aoc.y20.d14;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 14: Docking Data
 *
 * @author Brian Uri!
 */
public class Day14 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * Execute the initialization program. What is the sum of all values left in memory after it completes?
	 *
	 * Part 2:
	 * Execute the initialization program using an emulator for a version 2 decoder chip. What is the sum of all values
	 * left in memory after it completes?
	 */
	public static long getResult(Part part, List<String> input) {
		Memory memory = new Memory();
		for (String line : input) {
			if (line.startsWith("mask")) {
				memory.setMask(line.split(" = ")[1]);
			}
			else {
				Long address = Long.valueOf(line.split("\\[")[1].split("\\]")[0]);
				Long value = Long.valueOf(line.split(" = ")[1]);
				memory.set(part, address, value);
			}
		}
		return (memory.getSum());
	}
}