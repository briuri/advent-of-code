package buri.aoc.y16.d02;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 2: Bathroom Security
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals("1985", Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("12578", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("5DB3", Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("516DD", result);
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