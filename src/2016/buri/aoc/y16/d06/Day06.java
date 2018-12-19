package buri.aoc.y16.d06;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;
import buri.aoc.data.CharFrequency;

/**
 * Day 6: Signals and Noise
 * 
 * @author Brian Uri!
 */
public class Day06 extends Puzzle {

	/**
	 * Returns input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2016/06", fileIndex));
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