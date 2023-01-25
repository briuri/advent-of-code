package buri.aoc.y17.d02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 2: Corruption Checksum
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(18, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(47136, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(9, Puzzle.getResult(Part.TWO, Puzzle.getInput(2)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(250, result);
	}

	/**
	 * The spreadsheet consists of rows of apparently-random numbers.
	 *
	 * Part 1:
	 * What is the checksum for the spreadsheet, using the difference between highest and lowest value in each row?
	 *
	 * Part 2:
	 * What is the checksum for the spreadsheet, using the dividend of the only two divisible numbers in each row?
	 */
	public static int getResult(Part part, List<String> input) {
		List<List<Integer>> spreadsheet = new ArrayList<>();
		for (String line : input) {
			List<Integer> row = convertStringsToInts(Arrays.asList(line.split("\t")));
			Collections.sort(row);
			spreadsheet.add(row);
		}

		List<Integer> checksums = new ArrayList<>();
		for (List<Integer> row : spreadsheet) {
			checksums.add((part == Part.ONE) ? getRowDifference(row) : getRowQuotient(row));
		}
		return (getIntSum(checksums));
	}

	/**
	 * Locates the min and max value in a row of sorted integers and calculates the difference.
	 */
	private static int getRowDifference(List<Integer> row) {
		return (row.get(row.size() - 1) - row.get(0));
	}

	/**
	 * Locates the (unique) 2 evenly divisible integers in a row of integers and calculates the dividend.
	 */
	private static int getRowQuotient(List<Integer> row) {
		// Start with highest value and try dividing all smaller numbers.
		for (int i = row.size() - 1; i >= 0; i--) {
			Integer dividend = row.get(i);
			for (int j = i - 1; j >= 0; j--) {
				Integer divisor = row.get(j);
				if (dividend % divisor == 0) {
					return (dividend / divisor);
				}
			}
		}
		throw new RuntimeException("Every row should have 2 evenly divisible integers.");
	}
}