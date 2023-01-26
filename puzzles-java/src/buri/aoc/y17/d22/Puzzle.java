package buri.aoc.y17.d22;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 22: Sporifica Virus
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(5587L, 1, false);
		assertRun(5196L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(2511944L, 1, false);
		assertRun(2511633L, 0, true);
	}

	/**
	 * Part 1:
	 * Given your actual map, after 10000 bursts of activity, how many bursts cause a node to become infected? (Do not
	 * count nodes that begin infected.)
	 *
	 * Part 2:
	 * Given your actual map, after 10000000 bursts of activity, how many bursts cause a node to become infected? (Do
	 * not count nodes that begin infected.)
	 */
	protected long runLong(Part part, List<String> input) {
		final int bursts = (part == Part.ONE ? 10000 : 10000000);
		Cluster cluster = new Cluster(input);
		for (int i = 1; i <= bursts; i++) {
			cluster.move(part);
		}
		return (cluster.getInfections());
	}
}