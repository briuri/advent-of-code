package buri.aoc.y21.d08;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 07: Seven Segment Search
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(26L, 1, false);
		assertRun(387L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(61229L, 1, false);
		assertRun(986034L, 0, true);
	}

	/**
	 * Part 1:
	 * In the output values, how many times do digits 1, 4, 7, or 8 appear?
	 *
	 * Part 2:
	 * What do you get if you add up all of the output values?
	 */
	protected long runLong(Part part, List<String> displays) {
		long sum = 0;
		for (String line : displays) {
			String[] tokens = line.split(" \\| ");
			String[] input = tokens[0].split(" ");
			String[] output = tokens[1].split(" ");
			if (part == Part.ONE) {
				for (String out : output) {
					if (out.length() == 2 || out.length() == 3 || out.length() == 4 || out.length() == 7) {
						sum++;
					}
				}
			}
			else {
				Digit digit = new Digit(input);
				StringBuilder builder = new StringBuilder();
				for (String out : output) {
					builder.append(digit.parse(out));
				}
				sum += Integer.parseInt(builder.toString());
			}
		}
		return (sum);
	}
}