package buri.aoc.y2017;

import java.io.IOException;
import java.util.ArrayList;
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
	
	private Strategy _strategy;
	private List<List<Integer>> _spreadsheet;
	
	public enum Strategy {
		/* Difference of min and max value in row */
		MIN_MAX,
		/* Remainder-free dividend of 2 numbers (assumes only 1) */
		EVEN_DIVISION
	}

	/**
	 * Constructor, built against a specific input spreadsheet. Rows are
	 * sorted into ascending order on load.
	 *  
	 * @param spreadsheet
	 *        the row content to run against
	 * @param strategy enum value for the row calculation strategy
	 */
	public Day02(List<List<Integer>> spreadsheet, Strategy strategy) throws IOException {
		if (spreadsheet == null) {
			spreadsheet = new ArrayList<>();
		}
		for (List<Integer> row : spreadsheet) {
			Collections.sort(row);
		}
		_strategy = strategy;
		_spreadsheet = spreadsheet;
	}

	/**
	 * Locates the min and max value in a row of sorted integers and calculates the difference.
	 * 
	 * @param row the row of integers
	 */
	public static int getRowDifference(List<Integer> row) {
		if (row.isEmpty()) {
			throw new IllegalArgumentException("An empty row cannot have a difference.");
		}
		return (row.get(row.size() - 1) - row.get(0));
	}
	
	/**
	 * Locates the (unique) 2 evenly divisible integers in a row of integers and calculates the dividend.
	 * 
	 * @param row the row of integers
	 */
	public static int getRowQuotient(List<Integer> row) {
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
	
	/**
	 * Calculates a checksum based on the calculated value of each row.
	 */
	public int getChecksum() {
		int checksum = 0;
		for (List<Integer> row : getSpreadsheet()) {
			// Default to MIN_MAX
			checksum += (getStrategy() == Strategy.EVEN_DIVISION) ? getRowQuotient(row) : getRowDifference(row);
		}
		return (checksum);
	}
	
	/**
	 * Acessor for row data
	 */
	public List<List<Integer>> getSpreadsheet() {
		return (_spreadsheet);
	}
	
	/**
	 * Accessor for the calculation strategy
	 */
	private Strategy getStrategy() {
		return (_strategy);
	}
}
