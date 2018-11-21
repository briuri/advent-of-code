package buri.aoc.y2017;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import buri.aoc.util.FileUtil;
import buri.aoc.y2017.Day02.Strategy;

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
	private static List<List<Integer>> getPart1ExampleSpreadsheet() {
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
	private static List<List<Integer>> getPart2ExampleSpreadsheet() {
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

	@Test
	public void testConstructorNullInput() throws IOException {
		Day02 runner = new Day02(null, Strategy.MIN_MAX);
		assertEquals(0, runner.getSpreadsheet().size());
	}

	@Test
	public void testConstructorValidInput() throws IOException {
		Day02 runner = new Day02(getPart1ExampleSpreadsheet(), Strategy.MIN_MAX);
		assertEquals(3, runner.getSpreadsheet().size());
	}

	/**
	 * The first row's largest and smallest values are 9 and 1, and their difference is 8.
	 * The second row's largest and smallest values are 7 and 3, and their difference is 4.
	 * The third row's difference is 6.
	 * 
	 * In this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPart1Example() throws IOException {
		List<List<Integer>> spreadsheet = getPart1ExampleSpreadsheet();
		Day02 runner = new Day02(spreadsheet, Strategy.MIN_MAX);
		assertEquals(8, Day02.getRowDifference(spreadsheet.get(0)));
		assertEquals(4, Day02.getRowDifference(spreadsheet.get(1)));
		assertEquals(6, Day02.getRowDifference(spreadsheet.get(2)));
		assertEquals(18, runner.getChecksum());
	}
	
	/**
	 * Solves the Day 2 Part 1 puzzle against the real input file.
	 */
	@Test
	public void testPart1RealInput() throws IOException {
		List<List<Integer>> rows = FileUtil.getDay2Spreadsheet("data/2017-02.txt");
		Day02 runner = new Day02(rows, Strategy.MIN_MAX);
		System.out.println("Day 2 Part 1 checksum=" + runner.getChecksum());
	}
	
	/**
	 * In the first row, the only two numbers that evenly divide are 8 and 2; the result of this division is 4.
	 * In the second row, the two numbers are 9 and 3; the result is 3.
	 * In the third row, the result is 2.
	 * 
	 * In this example, the sum of the results would be 4 + 3 + 2 = 9.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPart2Example() throws IOException {
		List<List<Integer>> spreadsheet = getPart2ExampleSpreadsheet();
		Day02 runner = new Day02(spreadsheet, Strategy.EVEN_DIVISION);
		assertEquals(4, Day02.getRowQuotient(spreadsheet.get(0)));
		assertEquals(3, Day02.getRowQuotient(spreadsheet.get(1)));
		assertEquals(2, Day02.getRowQuotient(spreadsheet.get(2)));
		assertEquals(9, runner.getChecksum());
	}
	
	/**
	 * Solves the Day 2 Part 2 puzzle against the real input file.
	 */
	@Test
	public void testPart2RealInput() throws IOException {
		List<List<Integer>> rows = FileUtil.getDay2Spreadsheet("data/2017-02.txt");
		Day02 runner = new Day02(rows, Strategy.EVEN_DIVISION);
		System.out.println("Day 2 Part 2 checksum=" + runner.getChecksum());
	}
}
