package buri.aoc.y19.d07;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day07Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Long> input = Day07.getInput(0);
		assertEquals(531, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(43210L, Day07.getResult(Part.ONE, Day07.getInput(1)));
		assertEquals(54321L, Day07.getResult(Part.ONE, Day07.getInput(2)));
		assertEquals(65210L, Day07.getResult(Part.ONE, Day07.getInput(3)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day07.getResult(Part.ONE, Day07.getInput(0));
		toConsole(result);
		assertEquals(422858L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(139629729L, Day07.getResult(Part.TWO, Day07.getInput(4)));
		assertEquals(18216L, Day07.getResult(Part.TWO, Day07.getInput(5)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day07.getResult(Part.TWO, Day07.getInput(0));
		toConsole(result);
		assertEquals(14897241L, result);
	}
}
