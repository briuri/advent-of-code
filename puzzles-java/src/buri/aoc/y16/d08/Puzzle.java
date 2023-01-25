package buri.aoc.y16.d08;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Day 8: Two-Factor Authentication
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("116", result);
	}

	@Test
	public void testPart2Puzzle() {
		// Visual inspection: UPOJFLBCEZ
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertTrue(result.startsWith("■  ■ ■■■   ■■    ■■ ■■■■ ■    ■■■   ■■  ■■■■ ■■■■"));
	}

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