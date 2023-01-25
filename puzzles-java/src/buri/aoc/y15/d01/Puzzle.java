package buri.aoc.y15.d01;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 1: Not Quite Lisp
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(74, result);
	}
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(1795, result);
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