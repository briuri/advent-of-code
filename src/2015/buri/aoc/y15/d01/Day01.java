package buri.aoc.y15.d01;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 1: Not Quite Lisp
 * 
 * @author Brian Uri!
 */
public class Day01 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static String getInput(int fileIndex) {
		return (readFile("2015/01", fileIndex).get(0));
	}

	/**
	 * Part 1:
	 * To what floor do the instructions take Santa?
	 * 
	 * Part 2:
	 * What is the position of the character that causes Santa to first enter the basement?
	 */
	public static int getResult(Part part, String input) {
		int floor = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '(') {
				floor++;
			}
			else if (input.charAt(i) == ')') {
				floor--;
			}
			if (part == Part.TWO && floor < 0) {
				return (i + 1);
			}
		}
		return (floor);
	}
}