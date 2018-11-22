package buri.aoc.y2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The spreadsheet consists of rows of apparently-random numbers. To make sure the recovery process is on the right
 * track, they need you to calculate the spreadsheet's checksum. For each row, calculate a value based on a
 * strategy. The checksum is the sum of all of these calculated values.
 * 
 * @author Brian Uri!
 */
public class Day02 {

	public enum Strategy {
		/* Part 1: Difference of min and max value in row */
		MIN_MAX,
		/* Part 2: Remainder-free dividend of 2 numbers (assumes only 1) */
		EVEN_DIVISION
	}

	/**
	 * Private to prevent instantiation
	 */
	private Day02() {}

	/**
	 * Loads the input file at the provided path and returns its contents as a List of integers.
	 * 
	 * @param filePath the project-relative path to the file
	 * @return the data in the file, ready for processing
	 * @throws IllegalArgumentException on file I/O issues or if the file doesn't contain numbers
	 */
	public static List<List<Integer>> getSpreadsheetFromFile(String filePath) {
		List<List<Integer>> rows = new ArrayList<>();
		try {
			for (String rawRow : Files.readAllLines(Paths.get(filePath))) {
				List<Integer> intTokens = new ArrayList<>();
				for (String token : Arrays.asList(rawRow.split("\t"))) {
					try {
						intTokens.add(Integer.valueOf(token));
					}
					catch (NumberFormatException e) {
						throw new IllegalArgumentException(e.getMessage(), e);
					}
				}
				rows.add(intTokens);
			}
			return (rows);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file", e);
		}
	}

	/**
	 * Calculates a checksum based on the calculated value of each row.
	 * 
	 * @param strategy the calcuation strategy
	 * @param spreadsheet the row content (sorted in ascending order on load)
	 * @return the integer sum
	 */
	public static int getChecksum(Strategy strategy, List<List<Integer>> spreadsheet) {
		assertValidSpreadsheet(strategy, spreadsheet);
		for (List<Integer> row : spreadsheet) {
			Collections.sort(row);
		}

		int checksum = 0;
		for (List<Integer> row : spreadsheet) {
			// Default to MIN_MAX strategy, since there are only 2 right now.
			checksum += (strategy == Strategy.EVEN_DIVISION) ? getRowQuotient(row) : getRowDifference(row);
		}
		return (checksum);
	}

	/**
	 * Validates input spreadsheet, which must be non-null and non-empty.
	 * 
	 * @param strategy the matching strategy
	 * @param input the input string
	 */
	private static void assertValidSpreadsheet(Strategy strategy, List<List<Integer>> spreadsheet) {
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
	 * 
	 * Assumes row has already been validated.
	 * 
	 * @param row the row of integers
	 */
	private static int getRowDifference(List<Integer> row) {
		return (row.get(row.size() - 1) - row.get(0));
	}

	/**
	 * Locates the (unique) 2 evenly divisible integers in a row of integers and calculates the dividend.
	 * 
	 * Assumes row has already been validated.
	 * 
	 * @param row the row of integers
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
