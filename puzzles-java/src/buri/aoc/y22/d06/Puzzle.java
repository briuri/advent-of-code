package buri.aoc.y22.d06;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Day 06: Tuning Trouble
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(7L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1093L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(19L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3534L, result);
	}

	/**
	 * Part 1:
	 * How many characters need to be processed before the first start-of-packet marker is detected?
	 *
	 * Part 2:
	 * How many characters need to be processed before the first start-of-message marker is detected?
	 */
	public static long getResult(Part part, List<String> input) {
		String line = input.get(0);
		int size = (part == Part.ONE ? 4 : 14);
		for (int i = size - 1; i < line.length(); i++) {
			Set<Character> recent = new HashSet<>();
			for (int j = 0; j < size; j++) {
				recent.add(line.charAt(i - j));
			}
			if (recent.size() == size) {
				return (i + 1);
			}
		}
		throw new RuntimeException("Never found a unique string.");
	}
}