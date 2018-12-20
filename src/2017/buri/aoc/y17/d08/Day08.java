package buri.aoc.y17.d08;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 8: I Heard You Like Registers
 * 
 * @author Brian Uri!
 */
public class Day08 extends Puzzle {
	
	/**
	 * Returns input file as a list of instructions.
	 */
	public static List<RegisterInstruction> getInput(int fileIndex) {
		List<RegisterInstruction> instructions = new ArrayList<>();
		for (String instruction : readFile("2017/08", fileIndex)) {
			instructions.add(new RegisterInstruction(instruction));
		}
		return (instructions);
	}
	
	/**
	 * Part 1:
	 * What is the largest value in any register after completing the instructions?
	 * 
	 * Part 2:
	 * What is the largest value held in any register during this process?
	 */
	public static Long getResult(Part part, List<RegisterInstruction> instructions) {
		Registers registers = new Registers();
		registers.process(instructions);
		return (registers.getLargestValue(part));
	}
}
