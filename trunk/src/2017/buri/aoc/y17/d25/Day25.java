package buri.aoc.y17.d25;

import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day25 extends Puzzle {

	/**
	 * Input: State machine parameters
	 * Output: the machine
	 */
	public static Machine getInput(int fileIndex) {
		return (new Machine(readFile("2017/25", fileIndex)));
	}
	
	/**
	 * Recreate the Turing machine and save the computer! What is the diagnostic checksum it produces once it's working
	 * again?
	 */
	public static int getResult(Machine machine) {
		return (machine.run());
	}
}