package buri.aoc.y15.d01;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 1: Not Quite Lisp
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(74L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(1795L, 0, true);
	}

	/**
	 * Part 1:
	 * To what floor do the instructions take Santa?
	 *
	 * Part 2:
	 * What is the position of the character that causes Santa to first enter the basement?
	 */
	protected long runLong(Part part, List<String> input) {
		int floor = 0;
		for (int i = 0; i < input.get(0).length(); i++) {
			if (input.get(0).charAt(i) == '(') {
				floor++;
			}
			else if (input.get(0).charAt(i) == ')') {
				floor--;
			}
			if (part == Part.TWO && floor < 0) {
				return (i + 1);
			}
		}
		return (floor);
	}
}