package buri.aoc.y18.d07;

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
		List<String> input = Day07.getInput(0);
		assertEquals(101, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals("CABDFE", Day07.getResult(Part.ONE, Day07.getInput(1), 1, 0));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day07.getResult(Part.ONE, Day07.getInput(0), 1, 0);
		toConsole(result);
		assertEquals("ABGKCMVWYDEHFOPQUILSTNZRJX", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("15", Day07.getResult(Part.TWO, Day07.getInput(1), 2, 0));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day07.getResult(Part.TWO, Day07.getInput(0), 5, 60);
		toConsole(result);
		assertEquals("898", result);
	}
}
