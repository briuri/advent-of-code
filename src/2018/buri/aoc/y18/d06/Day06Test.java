package buri.aoc.y18.d06;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.data.tuple.Pair;

/**
 * @author Brian Uri!
 */
public class Day06Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Pair> input = Day06.getInput(0);
		assertEquals(50, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(17, Day06.getPart1Result(Day06.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day06.getPart1Result(Day06.getInput(0));
		toConsole(result);
		assertEquals(3251, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(16, Day06.getPart2Result(32, Day06.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day06.getPart2Result(10000, Day06.getInput(0));
		toConsole(result);
		assertEquals(47841, result);
	}
}
