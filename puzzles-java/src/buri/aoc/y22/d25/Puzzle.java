package buri.aoc.y22.d25;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Day 25: Full of Hot Air
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testConversion() {
		assertEquals("1=12=0202-000-=0", Puzzle.toSnafu(19925921840L));
		assertEquals(19925921840L, Puzzle.toLong("1=12=0202-000-=0"));
	}

	@Test
	public void testPart1Examples() {
		assertEquals("2=-1=0", Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("2=001=-2=--0212-22-2", result);
	}

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