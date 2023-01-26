package buri.aoc.y18.d13;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 13: Mine Cart Madness
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("7,3", 1, false);
		assertRun("50,54", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("6,4", 2, false);
		assertRun("50,100", 0, true);
	}

	/**
	 * Part 1:
	 * You'd like to know the location of the first crash.
	 *
	 * Part 2:
	 * What is the location of the last cart at the end of the first tick where it is the only cart left?
	 */
	protected String runString(Part part, List<String> input) {
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