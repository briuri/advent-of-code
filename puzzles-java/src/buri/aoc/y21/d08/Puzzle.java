package buri.aoc.y21.d08;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 07: Seven Segment Search
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(26L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(387L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(61229L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(986034L, result);
	}

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