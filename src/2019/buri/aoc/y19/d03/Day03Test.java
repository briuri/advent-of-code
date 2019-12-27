package buri.aoc.y19.d03;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day03Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day03.getInput(0);
		assertEquals(2, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(6, Day03.getResult(Part.ONE, Day03.getInput(1), 20));
		assertEquals(159, Day03.getResult(Part.ONE, Day03.getInput(2), 500));
		assertEquals(135, Day03.getResult(Part.ONE, Day03.getInput(3), 400));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day03.getResult(Part.ONE, Day03.getInput(0), 16000);
		toConsole(result);
		assertEquals(731, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(30, Day03.getResult(Part.TWO, Day03.getInput(1), 20));
		assertEquals(610, Day03.getResult(Part.TWO, Day03.getInput(2), 500));
		assertEquals(410, Day03.getResult(Part.TWO, Day03.getInput(3), 400));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day03.getResult(Part.TWO, Day03.getInput(0), 16000);
		toConsole(result);
		assertEquals(5672, result);
	}
}
