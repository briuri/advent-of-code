package buri.aoc.y17.d06;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day06Test extends BaseTest {
	
	@Test
	public void testGetInput() {
		List<Integer> banks = Day06.getInput(0);
		assertEquals(16, banks.size());
		assertEquals(Integer.valueOf(4), banks.get(0));
	}
	
	@Test
	public void testPart1Examples() {
		assertEquals(5, Day06.getResult(Part.ONE, Day06.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day06.getResult(Part.ONE, Day06.getInput(0));
		toConsole(result);
		assertEquals(12841, result);
	}
	
	@Test
	public void testPart2Examples() {
		assertEquals(4, Day06.getResult(Part.TWO, Day06.getInput(1)));
	}
	
	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day06.getResult(Part.TWO, Day06.getInput(0));
		toConsole(result);
		assertEquals(8038, result);
	}
}
