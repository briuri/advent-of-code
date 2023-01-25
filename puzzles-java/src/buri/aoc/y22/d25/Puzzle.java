package buri.aoc.y22.d25;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

import java.util.List;

/**
 * Day 25: Full of Hot Air
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What SNAFU number do you supply to Bob's console?
	 */
	public static String getResult(Part part, List<String> input) {
		long sum = 0;
		for (String line : input) {
			sum += toLong(line);
		}
		return (toSnafu(sum));
	}

	/**
	 * Converts a numeric value to SNAFU.
	 */
	protected static String toSnafu(long value) {
		long remaining = value;
		StringBuilder builder = new StringBuilder();
		while (remaining != 0) {
			long remainder = remaining % 5;
			if (remainder == 0) {
				builder.append("0");
			}
			else if (remainder == 1) {
				builder.append("1");
				remaining -= 1;
			}
			else if (remainder == 2) {
				builder.append("2");
				remaining -= 2;
			}
			else if (remainder == 3) {
				builder.append("=");
				remaining += 2;
			}
			else if (remainder == 4) {
				builder.append("-");
				remaining += 1;
			}
			remaining /= 5;
		}
		return (builder.reverse().toString());
	}

	/**
	 * Converts a SNAFU value to numeric.
	 */
	protected static long toLong(String snafu) {
		long total = 0;
		for (int i = snafu.length() - 1; i >= 0; i--) {
			long place = (long) Math.pow(5, snafu.length() - 1 - i);
			switch (snafu.charAt(i)) {
				case ('='):
					total -= (2 * place);
					break;
				case ('-'):
					total -= place;
					break;
				case ('1'):
					total += place;
					break;
				case ('2'):
					total += (2 * place);
					break;
			}
		}
		return total;
	}
}