package buri.aoc.y19.d08;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day08Test extends BaseTest {

	@Test
	public void testGetInput() {
		String input = Day08.getInput(0);
		assertEquals(15000, input.length());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("1", Day08.getResult(Part.ONE, Day08.getInput(1), 3, 2));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day08.getResult(Part.ONE, Day08.getInput(0), 25, 6);
		toClipboard(result);
		System.out.println("Day 8 Part 1\n\t" + result);
		assertEquals("2684", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(" #\n# ", Day08.getResult(Part.TWO, Day08.getInput(2), 2, 2));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day08.getResult(Part.TWO, Day08.getInput(0), 25, 6);
		System.out.println("Day 8 Part 2\n\t" + result);
		// YGRYZ
		assertEquals("#   # ##  ###  #   ##### \n#   ##  # #  # #   #   # \n # # #    #  #  # #   #  \n  #  # ## ###    #   #   \n  #  #  # # #    #  #    \n  #   ### #  #   #  #### ", result);
	}
}
