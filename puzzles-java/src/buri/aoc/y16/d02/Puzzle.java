package buri.aoc.y16.d02;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 2: Bathroom Security
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("1985", 1, false);
		assertRun("12578", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("5DB3", 1, false);
		assertRun("516DD", 0, true);
	}

	/**
	 * Part 1:
	 * What is the bathroom code? (3x3 grid)
	 *
	 * Part 2:
	 * What is the bathroom code? (diamond grid)
	 */
	protected String runString(Part part, List<String> input) {
		Keypad keypad = new Keypad(part);
		return (keypad.getButtons(input));
	}
}