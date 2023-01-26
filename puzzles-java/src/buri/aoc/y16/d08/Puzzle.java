package buri.aoc.y16.d08;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 8: Two-Factor Authentication
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("116", 0, true);
	}
	@Test
	public void testPart2() {
		// UPOJFLBCEZ
		assertRun("■  ■ ■■■   ■■    ■■ ■■■■ ■    ■■■   ■■  ■■■■ ■■■■", 0, true);
	}

	/**
	 * Part 1:
	 * How many pixels should be lit?
	 *
	 * Part 2:
	 * After you swipe your card, what code is the screen trying to display?
	 */
	protected String runString(Part part, List<String> input) {
		Screen screen = new Screen();
		int lit = screen.run(input);
		if (part == Part.ONE) {
			return (String.valueOf(lit));
		}
		return (screen.toString());
	}
}