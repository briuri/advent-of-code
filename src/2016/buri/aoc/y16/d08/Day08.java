package buri.aoc.y16.d08;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;

/**
 * Day 8: Two-Factor Authentication
 * 
 * @author Brian Uri!
 */
public class Day08 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2016/08", fileIndex));
	}

	/**
	 * Part 1:
	 * How many pixels should be lit?
	 * 
	 * Part 2:
	 * After you swipe your card, what code is the screen trying to display?
	 */
	public static int getResult(Part part, List<String> input) {
		Screen screen = new Screen();
		return (screen.run(input));
	}
}