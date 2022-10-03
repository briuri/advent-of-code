package buri.aoc.y21.d08;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 07: Seven Segment Search
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * In the output values, how many times do digits 1, 4, 7, or 8 appear?
	 *
	 * Part 2:
	 * What do you get if you add up all of the output values?
	 */
	public static long getResult(Part part, List<String> displays) {
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
				StringBuffer buffer = new StringBuffer();
				for (String out : output) {
					buffer.append(digit.parse(out));
				}
				sum += Integer.valueOf(buffer.toString());
			}
		}
		return (sum);
	}
}