package buri.aoc.y17.d02;

import java.util.Collections;
import java.util.List;

import buri.aoc.y17.Part;

/**
 * The spreadsheet consists of rows of apparently-random numbers. To make sure the recovery process is on the right
 * track, they need you to calculate the spreadsheet's checksum. For each row, calculate a value based on
 * Part rules. The checksum is the sum of all of these calculated values.
 * 
 * @author Brian Uri!
 */
public class Day02 {

	/**
	 * Calculates a checksum based on the calculated value of each row.
	 */
	public static int getChecksum(Part part, List<List<Integer>> spreadsheet) {
		assertValidSpreadsheet(spreadsheet);
		for (List<Integer> row : spreadsheet) {
			Collections.sort(row);
		}

		int checksum = 0;
		for (List<Integer> row : spreadsheet) {
			checksum += (part == Part.ONE) ? getRowDifference(row) : getRowQuotient(row);
		}
		return (checksum);
	}

	/**
	 * Validates input spreadsheet, which must be non-null and non-empty.
	 */
	private static void assertValidSpreadsheet(List<List<Integer>> spreadsheet) {
		if (spreadsheet == null || spreadsheet.isEmpty()) {
			throw new IllegalArgumentException("Spreadsheet must be non-null and non-empty.");
		}
		for (List<Integer> row : spreadsheet) {
			if (row.isEmpty()) {
				throw new IllegalArgumentException("Rows cannot be empty.");
			}
		}
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
