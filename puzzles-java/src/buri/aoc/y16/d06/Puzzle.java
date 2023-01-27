package buri.aoc.y16.d06;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.CharFrequency;
import org.junit.Test;

import java.util.List;

/**
 * Day 6: Signals and Noise
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("easter", 1, false);
		assertRun("gyvwpxaz", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("advent", 1, false);
		assertRun("jucfoary", 0, true);
	}

	/**
	 * Part 1:
	 * What is the error-corrected version of the message being sent using the most frequent letters?
	 *
	 * Part 2:
	 * What is the error-corrected version of the message being sent using the least frequent letters?
	 */
	protected String runString(Part part, List<String> input) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < input.get(0).length(); i++) {
			builder.append(getFrequencyChar(part, i, input));
		}
		return (builder.toString());
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