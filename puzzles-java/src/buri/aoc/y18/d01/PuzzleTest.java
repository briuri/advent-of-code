package buri.aoc.y18.d01;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import buri.aoc.common.BaseTest;
import buri.aoc.common.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	/**
	 * Boilerplate conversion for example input
	 */
	private static List<String> getInput(String values) {
		List<String> input = new ArrayList<>(Arrays.asList(values.split(" ")));
		return (input);
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3, Puzzle.getResult(Part.ONE, getInput("+1 -2 +3 +1")));
		assertEquals(3, Puzzle.getResult(Part.ONE, getInput("+1 +1 +1")));
		assertEquals(0, Puzzle.getResult(Part.ONE, getInput("+1 +1 -2")));
		assertEquals(-6, Puzzle.getResult(Part.ONE, getInput("-1 -2 -3")));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(442, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Puzzle.getResult(Part.TWO, getInput("+1 -2 +3 +1")));
		assertEquals(0, Puzzle.getResult(Part.TWO, getInput("+1 -1")));
		assertEquals(10, Puzzle.getResult(Part.TWO, getInput("+3 +3 +4 -2 -4")));
		assertEquals(5, Puzzle.getResult(Part.TWO, getInput("-6 +3 +8 +5 -6")));
		assertEquals(14, Puzzle.getResult(Part.TWO, getInput("+7 +7 -2 -7 -4")));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(59908, result);
	}
}