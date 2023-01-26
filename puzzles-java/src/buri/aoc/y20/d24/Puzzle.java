package buri.aoc.y20.d24;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 24: Lobby Layout
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(10L, 1, false);
		assertRun(388L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(2208L, 1, false);
		assertRun(4002L, 0, true);
	}

	/**
	 * Part 1:
	 * After all of the instructions have been followed, how many tiles are left with the black side up?
	 *
	 * Part 2:
	 * How many tiles will be black after 100 days?
	 */
	protected long runLong(Part part, List<String> input) {
		Floor floor = new Floor(input);
		if (part == Part.TWO) {
			floor.cycleArt();
		}
		return (floor.getBlackCount());
	}
}