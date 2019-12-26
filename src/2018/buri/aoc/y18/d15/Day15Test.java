package buri.aoc.y18.d15;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day15Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<String> input = Day15.getInput(0);
		assertEquals(32, input.size());
	}

	@Test
	public void testPart1Examples() {
		assertEquals(27730, Day15.getResult(Part.ONE, Day15.getInput(1)));
		assertEquals(36334, Day15.getResult(Part.ONE, Day15.getInput(2)));
		assertEquals(39514, Day15.getResult(Part.ONE, Day15.getInput(3)));
		assertEquals(27755, Day15.getResult(Part.ONE, Day15.getInput(4)));
		assertEquals(28944, Day15.getResult(Part.ONE, Day15.getInput(5)));
		assertEquals(18740, Day15.getResult(Part.ONE, Day15.getInput(6)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day15.getResult(Part.ONE, Day15.getInput(0));
		toConsole(result);
		assertEquals(190777, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(4988, Day15.getResult(Part.TWO, Day15.getInput(1)));
		assertEquals(31284, Day15.getResult(Part.TWO, Day15.getInput(3)));
		assertEquals(3478, Day15.getResult(Part.TWO, Day15.getInput(4)));
		assertEquals(6474, Day15.getResult(Part.TWO, Day15.getInput(5)));
		assertEquals(1140, Day15.getResult(Part.TWO, Day15.getInput(6)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day15.getResult(Part.TWO, Day15.getInput(0));
		toConsole(result);
		assertEquals(47388, result);
	}
}
