package buri.aoc.y19.d18;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 18: Many-Worlds Interpretation
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(8L, 1, false);
		assertRun(86L, 2, false);
		assertRun(132L, 3, false);
		assertRun(136L, 4, false);
		assertRun(81L, 5, false);
		assertRun(5392L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(8L, 7, false);
		assertRun(24L, 8, false);
		assertRun(32L, 9, false);
		assertRun(1684L, 6, true);
	}

	/**
	 * Part 1:
	 * How many steps is the shortest path that collects all of the keys?
	 *
	 * Part 2:
	 * After updating your map and using the remote-controlled robots, what is the fewest steps necessary to collect all
	 * of the keys?
	 */
	protected long runLong(Part part, List<String> input) {
		Vault vault = new Vault(input);
		return (vault.getFewestSteps());
	}
}