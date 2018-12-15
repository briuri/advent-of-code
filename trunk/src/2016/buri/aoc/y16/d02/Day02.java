package buri.aoc.y16.d02;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day02 extends Puzzle {

	/**
	 * Input: each button's commands on a line
	 * Output: List of lines.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2016/02", fileIndex));
	}
	
	/**
	 * Part 1:
	 * Your puzzle input is the instructions from the document you found at the front desk. What is the bathroom code?
	 */
	public static String getResult(Part part, List<String> input) {
		Keypad keypad = new Keypad(part);
		return (keypad.getButtons(input));
	}
}