package buri.aoc.y16.d02;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

/**
 * Day 2: Bathroom Security
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

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