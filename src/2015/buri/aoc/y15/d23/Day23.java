package buri.aoc.y15.d23;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;

/**
 * Day 23: Opening the Turing Lock
 * 
 * @author Brian Uri!
 */
public class Day23 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2015/23", fileIndex));
	}

	/**
	 * Part 1:
	 * What is the value in register b when the program in your puzzle input is finished executing?
	 * 
	 * Part 2:
	 * What is the value in register b after the program is finished executing if register a starts as 1 instead?
	 */
	public static long getResult(Part part, List<String> input) {
		Registers registers = new Registers(part, input);
		registers.process();
		return (registers.get("b"));
	}
}