package buri.aoc.y17.d02;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day02Test {

	@Test
	public void testGetInput() {
		List<List<Integer>> rows = Day02.getInput(0);
		assertEquals(16, rows.size());
		assertEquals(16, rows.get(0).size());
		assertEquals(Integer.valueOf(4347), rows.get(0).get(0));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetResultNullInput() {
		Day02.getResult(Part.ONE, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetResultEmptyInput() {
		Day02.getResult(Part.ONE, new ArrayList<List<Integer>>());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetResultEmptyRow() {
		List<List<Integer>> spreadsheet = new ArrayList<>();
		spreadsheet.add(new ArrayList<Integer>());
		Day02.getResult(Part.ONE, spreadsheet);
	}

	@Test
	public void testPart1Example() {
		assertEquals(18, Day02.getResult(Part.ONE, Day02.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day02.getResult(Part.ONE, Day02.getInput(0));
		System.out.println("Day 2 Part 1\n\t" + result);
		assertEquals(47136, result);
	}

	@Test
	public void testPart2Example() {
		assertEquals(9, Day02.getResult(Part.TWO, Day02.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day02.getResult(Part.TWO, Day02.getInput(0));
		System.out.println("Day 2 Part 2\n\t" + result);
		assertEquals(250, result);
	}
}
