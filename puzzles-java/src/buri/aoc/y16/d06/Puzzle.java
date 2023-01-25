package buri.aoc.y16.d06;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.CharFrequency;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 6: Signals and Noise
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals("easter", Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("gyvwpxaz", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("advent", Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("jucfoary", result);
	}

	/**
	 * Part 1:
	 * What is the error-corrected version of the message being sent using the most frequent letters?
	 *
	 * Part 2:
	 * What is the error-corrected version of the message being sent using the least frequent letters?
	 */
	public static String getResult(Part part, List<String> input) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < input.get(0).length(); i++) {
			buffer.append(getFrequencyChar(part, i, input));
		}
		return (buffer.toString());
	}

	/**
	 * Finds the letter that occurs the most or least at a particular index in a list of strings.
	 */
	public static char getFrequencyChar(Part part, int i, List<String> input) {
		CharFrequency frequency = new CharFrequency();
		for (String line : input) {
			frequency.add(line.charAt(i));
		}
		return (part == Part.ONE ? frequency.getHighestFrequency().getKey() : frequency.getLowestFrequency().getKey());
	}
}