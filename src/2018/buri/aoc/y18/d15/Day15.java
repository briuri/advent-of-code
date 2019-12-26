package buri.aoc.y18.d15;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;

/**
 * Day 15: Beverage Bandits
 * 
 * @author Brian Uri!
 */
public class Day15 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2018/15", fileIndex));
	}
	
	/**
	 * Part 1:
	 * What is the outcome of the combat described in your puzzle input?
	 * 
	 * Part 2:
	 * After increasing the Elves' attack power until it is just barely enough for them to win without any Elves dying,
	 * what is the outcome of the combat described in your puzzle input?
	 */
	public static int getResult(Part part, List<String> input) {
		if (part == Part.ONE) {
			Grid grid = new Grid(input, 3);
			return (grid.run());
		}
		
		// Part TWO
		int elfAttackPower = 4;
		while (true) {
			Grid grid = new Grid(input, elfAttackPower);
			int outcome = grid.run();
			if (!grid.getElfDied()) {
				return (outcome);
			}
			elfAttackPower++;
		}
	}
}