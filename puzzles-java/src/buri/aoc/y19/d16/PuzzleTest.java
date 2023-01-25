package buri.aoc.y19.d16;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.common.BaseTest;
import buri.aoc.common.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPatternGeneration() {
		List<Integer> pattern = Puzzle.getPattern(0, 8);
		assertEquals(1, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(-1, (int) pattern.get(2));
		assertEquals(0, (int) pattern.get(3));
		assertEquals(1, (int) pattern.get(4));
		assertEquals(0, (int) pattern.get(5));
		assertEquals(-1, (int) pattern.get(6));
		assertEquals(0, (int) pattern.get(7));
		pattern = Puzzle.getPattern(1, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(1, (int) pattern.get(1));
		assertEquals(1, (int) pattern.get(2));
		assertEquals(0, (int) pattern.get(3));
		assertEquals(0, (int) pattern.get(4));
		assertEquals(-1, (int) pattern.get(5));
		assertEquals(-1, (int) pattern.get(6));
		assertEquals(0, (int) pattern.get(7));
		pattern = Puzzle.getPattern(2, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(1, (int) pattern.get(2));
		assertEquals(1, (int) pattern.get(3));
		assertEquals(1, (int) pattern.get(4));
		assertEquals(0, (int) pattern.get(5));
		assertEquals(0, (int) pattern.get(6));
		assertEquals(0, (int) pattern.get(7));
		pattern = Puzzle.getPattern(3, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(0, (int) pattern.get(2));
		assertEquals(1, (int) pattern.get(3));
		assertEquals(1, (int) pattern.get(4));
		assertEquals(1, (int) pattern.get(5));
		assertEquals(1, (int) pattern.get(6));
		assertEquals(0, (int) pattern.get(7));
		pattern = Puzzle.getPattern(4, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(0, (int) pattern.get(2));
		assertEquals(0, (int) pattern.get(3));
		assertEquals(1, (int) pattern.get(4));
		assertEquals(1, (int) pattern.get(5));
		assertEquals(1, (int) pattern.get(6));
		assertEquals(1, (int) pattern.get(7));
		pattern = Puzzle.getPattern(5, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(0, (int) pattern.get(2));
		assertEquals(0, (int) pattern.get(3));
		assertEquals(0, (int) pattern.get(4));
		assertEquals(1, (int) pattern.get(5));
		assertEquals(1, (int) pattern.get(6));
		assertEquals(1, (int) pattern.get(7));
		pattern = Puzzle.getPattern(6, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(0, (int) pattern.get(2));
		assertEquals(0, (int) pattern.get(3));
		assertEquals(0, (int) pattern.get(4));
		assertEquals(0, (int) pattern.get(5));
		assertEquals(1, (int) pattern.get(6));
		assertEquals(1, (int) pattern.get(7));
		pattern = Puzzle.getPattern(7, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(0, (int) pattern.get(2));
		assertEquals(0, (int) pattern.get(3));
		assertEquals(0, (int) pattern.get(4));
		assertEquals(0, (int) pattern.get(5));
		assertEquals(0, (int) pattern.get(6));
		assertEquals(1, (int) pattern.get(7));
	}

	@Test
	public void testPart1Examples() {
		assertEquals("01029498", Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 4));
		assertEquals("24176176", Puzzle.getResult(Part.ONE, Puzzle.getInput(2), 100));
		assertEquals("73745418", Puzzle.getResult(Part.ONE, Puzzle.getInput(3), 100));
		assertEquals("52432133", Puzzle.getResult(Part.ONE, Puzzle.getInput(4), 100));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 100);
		toConsole(result);
		assertEquals("82435530", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("84462026", Puzzle.getResult(Part.TWO, Puzzle.getInput(5), 100));
		assertEquals("78725270", Puzzle.getResult(Part.TWO, Puzzle.getInput(6), 100));
		assertEquals("53553731", Puzzle.getResult(Part.TWO, Puzzle.getInput(7), 100));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), 100);
		toConsole(result);
		assertEquals("83036156", result);
	}
}