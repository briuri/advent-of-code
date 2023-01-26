package buri.aoc.y18.d17;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 17: Reservoir Research
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(57L, 1, false);
		assertRun(52800L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(29L, 1, false);
		assertRun(45210L, 0, true);
	}

	/**
	 * Part 1:
	 * How many tiles can the water reach within the range of y values in your scan?
	 *
	 * Part 2:
	 * How many water tiles are left after the water spring stops producing water and all remaining water not at rest
	 * has drained?
	 */
	protected long runLong(Part part, List<String> input) {
		Reservoir reservoir = new Reservoir(input);
		return (reservoir.flow(part));
	}
}