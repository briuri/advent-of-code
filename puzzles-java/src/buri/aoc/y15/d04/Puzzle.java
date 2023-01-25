package buri.aoc.y15.d04;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.MD5Hash;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 4: The Ideal Stocking Stuffer
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, "iwrupvqb");
		toConsole(result);
		assertEquals(346386, result);
	}
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, "iwrupvqb");
		toConsole(result);
		assertEquals(9958218, result);
	}

	/**
	 * Part 1:
	 * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least five zeroes.
	 *
	 * Part 2:
	 * Now find one that starts with six zeroes.
	 */
	public static int getResult(Part part, String input) {
		MD5Hash hasher = new MD5Hash();
		int number = 0;
		String prefix = (part == Part.ONE ? "00000" : "000000");
		while (true) {
			if (hasher.getHash(input + number).startsWith(prefix)) {
				return (number);
			}
			number++;
		}
	}
}