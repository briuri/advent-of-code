package buri.aoc.y16.d12;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;

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
		return (readFile("2016/12", fileIndex));
	}
	
	/**
	 * Part 1:
	 * After executing the assembunny code in your puzzle input, what value is left in register a?
	 * 
	 * Part 2:
	 * QUESTION
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