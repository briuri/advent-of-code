package buri.aoc.y17.d25;

import java.util.List;

import buri.aoc.common.BasePuzzle;

/**
 * Day 25: The Halting Problem
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the diagnostic checksum it produces once it's working again?
	 */
	public static int getResult(List<String> input) {
		Machine machine = new Machine(input);
		return (machine.run());
	}
}