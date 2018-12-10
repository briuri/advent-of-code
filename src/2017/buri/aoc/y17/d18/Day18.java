package buri.aoc.y17.d18;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day18 extends Puzzle {

	/**
	 * Input: One command per line
	 * Output: A list of commands
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2017/18", fileIndex));
	}

	/**
	 * Part 1:
	 * What is the value of the recovered frequency (the value of the most recently played sound) the first time a rcv
	 * instruction is executed with a non-zero value?
	 * 
	 * Part 2:
	 */
	public static long getResult(Part part, List<String> input) {
		Registers registers = new Registers();
		registers.process(input);
		if (part == Part.ONE) {
			return (registers.getFrequencyAfterFirstReceive());
		}
		return (0);
	}
}