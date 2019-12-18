package buri.aoc.y19.d16;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day16Test extends BaseTest {

	@Test
	public void testGetInput() {
		List<Integer> input = Day16.getInput(0);
		assertEquals(650, input.size());
		assertEquals(Integer.valueOf(5), input.get(0));
	}

	@Test
	public void testPatternGeneration() {
		List<Integer> pattern = Day16.getPattern(0, 8);
		assertEquals(1, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(-1, (int) pattern.get(2));
		assertEquals(0, (int) pattern.get(3));
		assertEquals(1, (int) pattern.get(4));
		assertEquals(0, (int) pattern.get(5));
		assertEquals(-1, (int) pattern.get(6));
		assertEquals(0, (int) pattern.get(7));
		pattern = Day16.getPattern(1, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(1, (int) pattern.get(1));
		assertEquals(1, (int) pattern.get(2));
		assertEquals(0, (int) pattern.get(3));
		assertEquals(0, (int) pattern.get(4));
		assertEquals(-1, (int) pattern.get(5));
		assertEquals(-1, (int) pattern.get(6));
		assertEquals(0, (int) pattern.get(7));
		pattern = Day16.getPattern(2, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(1, (int) pattern.get(2));
		assertEquals(1, (int) pattern.get(3));
		assertEquals(1, (int) pattern.get(4));
		assertEquals(0, (int) pattern.get(5));
		assertEquals(0, (int) pattern.get(6));
		assertEquals(0, (int) pattern.get(7));
		pattern = Day16.getPattern(3, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(0, (int) pattern.get(2));
		assertEquals(1, (int) pattern.get(3));
		assertEquals(1, (int) pattern.get(4));
		assertEquals(1, (int) pattern.get(5));
		assertEquals(1, (int) pattern.get(6));
		assertEquals(0, (int) pattern.get(7));
		pattern = Day16.getPattern(4, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(0, (int) pattern.get(2));
		assertEquals(0, (int) pattern.get(3));
		assertEquals(1, (int) pattern.get(4));
		assertEquals(1, (int) pattern.get(5));
		assertEquals(1, (int) pattern.get(6));
		assertEquals(1, (int) pattern.get(7));
		pattern = Day16.getPattern(5, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(0, (int) pattern.get(2));
		assertEquals(0, (int) pattern.get(3));
		assertEquals(0, (int) pattern.get(4));
		assertEquals(1, (int) pattern.get(5));
		assertEquals(1, (int) pattern.get(6));
		assertEquals(1, (int) pattern.get(7));
		pattern = Day16.getPattern(6, 8);
		assertEquals(0, (int) pattern.get(0));
		assertEquals(0, (int) pattern.get(1));
		assertEquals(0, (int) pattern.get(2));
		assertEquals(0, (int) pattern.get(3));
		assertEquals(0, (int) pattern.get(4));
		assertEquals(0, (int) pattern.get(5));
		assertEquals(1, (int) pattern.get(6));
		assertEquals(1, (int) pattern.get(7));
		pattern = Day16.getPattern(7, 8);
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
		assertEquals("01029498", Day16.getResult(Part.ONE, Day16.getInput(1), 4));
		assertEquals("24176176", Day16.getResult(Part.ONE, Day16.getInput(2), 100));
		assertEquals("73745418", Day16.getResult(Part.ONE, Day16.getInput(3), 100));
		assertEquals("52432133", Day16.getResult(Part.ONE, Day16.getInput(4), 100));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day16.getResult(Part.ONE, Day16.getInput(0), 100);
		toClipboard(result);
		System.out.println("Day 16 Part 1\n\t" + result);
		assertEquals("82435530", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("84462026", Day16.getResult(Part.TWO, Day16.getInput(5), 100));
		assertEquals("78725270", Day16.getResult(Part.TWO, Day16.getInput(6), 100));
		assertEquals("53553731", Day16.getResult(Part.TWO, Day16.getInput(7), 100));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day16.getResult(Part.TWO, Day16.getInput(0), 100);
		toClipboard(result);
		System.out.println("Day 16 Part 2\n\t" + result);
		assertEquals("83036156", result);
	}
}
