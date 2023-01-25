package buri.aoc.y18.d13;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 13: Mine Cart Madness
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals("7,3", Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("50,54", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("6,4", Puzzle.getResult(Part.TWO, Puzzle.getInput(2)));
	}

	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("50,100", result);
	}

	/**
	 * Part 1:
	 * You'd like to know the location of the first crash.
	 *
	 * Part 2:
	 * What is the location of the last cart at the end of the first tick where it is the only cart left?
	 */
	public static String getResult(Part part, List<String> input) {
		Tracks tracks = new Tracks(input);
		while (true) {
			tracks.move();
			if (part == Part.ONE && tracks.getFirstCollision() != null) {
				return (tracks.getFirstCollision());
			}
			if (part == Part.TWO && tracks.getCarts().size() == 1) {
				return (tracks.getLastCartPosition().toString());
			}
		}
	}
}