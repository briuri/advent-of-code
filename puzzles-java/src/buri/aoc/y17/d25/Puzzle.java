package buri.aoc.y17.d25;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 25: The Halting Problem
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(3L, 1, false);
		assertRun(3578L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the diagnostic checksum it produces once it's working again?
	 */
	protected long runLong(Part part, List<String> input) {
		Machine machine = new Machine(input);
		return (machine.run());
	}
}