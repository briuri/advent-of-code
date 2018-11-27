package buri.aoc.y2017;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import buri.aoc.model.Part;
import buri.aoc.y2017.util.FileUtil;

/**
 * @author Brian Uri!
 */
public class Day02Test {

	/**
	 * Example Data
	 * 
	 * 5 1 9 5
	 * 7 5 3
	 * 2 4 6 8
	 */
	private static List<List<Integer>> getPart1ExampleInput() {
		List<Integer> row0 = new ArrayList<>();
		row0.add(5);
		row0.add(1);
		row0.add(9);
		row0.add(5);
		List<Integer> row1 = new ArrayList<>();
		row1.add(7);
		row1.add(5);
		row1.add(3);
		List<Integer> row2 = new ArrayList<>();
		row2.add(2);
		row2.add(4);
		row2.add(6);
		row2.add(8);
		List<List<Integer>> spreadsheet = new ArrayList<>();
		spreadsheet.add(row0);
		spreadsheet.add(row1);
		spreadsheet.add(row2);
		return (spreadsheet);
	}

	/**
	 * Example Data
	 * 
	 * 5 9 2 8
	 * 9 4 7 3
	 * 3 8 6 5
	 */
	private static List<List<Integer>> getPart2ExampleInput() {
		List<Integer> row0 = new ArrayList<>();
		row0.add(5);
		row0.add(9);
		row0.add(2);
		row0.add(8);
		List<Integer> row1 = new ArrayList<>();
		row1.add(9);
		row1.add(4);
		row1.add(7);
		row1.add(3);
		List<Integer> row2 = new ArrayList<>();
		row2.add(3);
		row2.add(8);
		row2.add(6);
		row2.add(5);
		List<List<Integer>> spreadsheet = new ArrayList<>();
		spreadsheet.add(row0);
		spreadsheet.add(row1);
		spreadsheet.add(row2);
		return (spreadsheet);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetChecksumNullInput() {
		Day02.getChecksum(Part.ONE, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetChecksumEmptyInput() {
		Day02.getChecksum(Part.ONE, new ArrayList<List<Integer>>());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetChecksumEmptyRow() {
		List<List<Integer>> spreadsheet = new ArrayList<>();
		spreadsheet.add(new ArrayList<Integer>());
		Day02.getChecksum(Part.ONE, spreadsheet);
	}

	/**
	 * The first row's largest and smallest values are 9 and 1, and their difference is 8.
	 * The second row's largest and smallest values are 7 and 3, and their difference is 4.
	 * The third row's difference is 6.
	 * 
	 * In this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.
	 */
	@Test
	public void testPart1Example() {
		List<List<Integer>> spreadsheet = getPart1ExampleInput();
		assertEquals(18, Day02.getChecksum(Part.ONE, spreadsheet));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day02.getChecksum(Part.ONE, FileUtil.getDay02());
		System.out.println("Day 2 Part 1\n\t" + result);
		assertEquals(47136, result);
	}

	/**
	 * In the first row, the only two numbers that evenly divide are 8 and 2; the result of this division is 4.
	 * In the second row, the two numbers are 9 and 3; the result is 3.
	 * In the third row, the result is 2.
	 * 
	 * In this example, the sum of the results would be 4 + 3 + 2 = 9.
	 */
	@Test
	public void testPart2Example() {
		List<List<Integer>> spreadsheet = getPart2ExampleInput();
		assertEquals(9, Day02.getChecksum(Part.TWO, spreadsheet));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day02.getChecksum(Part.TWO, FileUtil.getDay02());
		System.out.println("Day 2 Part 2\n\t" + result);
		assertEquals(250, result);
	}
}
