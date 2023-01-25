package buri.aoc.y17.d22;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 22: Sporifica Virus
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(41, Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 70));
		assertEquals(5587, Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 10000));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 10000);
		toConsole(result);
		assertEquals(5196, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(26, Puzzle.getResult(Part.TWO, Puzzle.getInput(1), 100));
		assertEquals(2511944, Puzzle.getResult(Part.TWO, Puzzle.getInput(1), 10000000));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), 10000000);
		toConsole(result);
		assertEquals(2511633, result);
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
	public static int getResult(Part part, List<String> input, int bursts) {
		Cluster cluster = new Cluster(input);
		for (int i = 1; i <= bursts; i++) {
			cluster.move(part);
		}
		return (cluster.getInfections());
	}
}