package buri.aoc.y17.d25;

import buri.aoc.Puzzle;

/**
 * Day 25: The Halting Problem
 * 
 * @author Brian Uri!
 */
public class Day25 extends Puzzle {

	/**
	 * Returns the input file as a Machine object.
	 */
	public static Machine getInput(int fileIndex) {
		return (new Machine(readFile("2017/25", fileIndex)));
	}
	
	/**
	 * Part 1:
	 * What is the diagnostic checksum it produces once it's working again?
	 * 
	 * Part 2:
	 * N/A
	 */
	public static int getResult(Machine machine) {
		return (machine.run());
	}
}