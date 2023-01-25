package buri.aoc.y16.d08;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

/**
 * Day 8: Two-Factor Authentication
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many pixels should be lit?
	 *
	 * Part 2:
	 * After you swipe your card, what code is the screen trying to display?
	 */
	public static String getResult(Part part, List<String> input) {
		Screen screen = new Screen();
		int lit = screen.run(input);
		if (part == Part.ONE) {
			return (String.valueOf(lit));
		}
		return (screen.toString());
	}
}