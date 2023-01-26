package buri.aoc.y15.d04;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.MD5Hash;
import org.junit.Test;

import java.util.List;

/**
 * Day 4: The Ideal Stocking Stuffer
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(346386L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(9958218L, 0, true);
	}

	/**
	 * Part 1:
	 * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least five zeroes.
	 *
	 * Part 2:
	 * Now find one that starts with six zeroes.
	 */
	protected long runLong(Part part, List<String> input) {
		MD5Hash hasher = new MD5Hash();
		int number = 0;
		String prefix = (part == Part.ONE ? "00000" : "000000");
		while (true) {
			if (hasher.getHash(input.get(0) + number).startsWith(prefix)) {
				return (number);
			}
			number++;
		}
	}
}