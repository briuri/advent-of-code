package buri.aoc.y17.d04;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day04Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<List<String>> rows = Day04.getInput(0);
		assertEquals(512, rows.size());
		assertEquals(10, rows.get(0).size());
		assertEquals("pphsv", rows.get(0).get(0));
	}

	@Test
	public void testPart1Examples() {
		assertEquals(2, Day04.getResult(Part.ONE, Day04.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle against the real input.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day04.getResult(Part.ONE, Day04.getInput(0));
		toConsole(result);
		assertEquals(466, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3, Day04.getResult(Part.TWO, Day04.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle against the real input.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day04.getResult(Part.TWO, Day04.getInput(0));
		toConsole(result);
		assertEquals(251, result);
	}
}
