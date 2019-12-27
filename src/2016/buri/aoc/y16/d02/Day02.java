package buri.aoc.y16.d02;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 2: Bathroom Security
 * 
 * @author Brian Uri!
 */
public class Day02 extends BasePuzzle {

	/**
	 * Returns input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * What is the bathroom code? (3x3 grid)
	 * 
	 * Part 2:
	 * What is the bathroom code? (diamond grid)
	 */
	public static String getResult(Part part, List<String> input) {
		Keypad keypad = new Keypad(part);
		return (keypad.getButtons(input));
	}
}