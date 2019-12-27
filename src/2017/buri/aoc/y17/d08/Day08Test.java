package buri.aoc.y17.d08;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day08Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<RegisterInstruction> instructions = Day08.getInput(0);
		assertEquals(1000, instructions.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(Long.valueOf(1), Day08.getResult(Part.ONE, Day08.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Day08.getResult(Part.ONE, Day08.getInput(0));
		toConsole(result);
		assertEquals(4888L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(Long.valueOf(10), Day08.getResult(Part.TWO, Day08.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Day08.getResult(Part.TWO, Day08.getInput(0));
		toConsole(result);
		assertEquals(7774L, result);
	}

}
