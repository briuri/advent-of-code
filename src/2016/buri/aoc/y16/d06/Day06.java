package buri.aoc.y16.d06;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;
import buri.aoc.data.CharFrequency;

/**
 * @author Brian Uri!
 */
public class Day06 extends Puzzle {

	/**
	 * Input: List of garbled words
	 * Output: List unmodified
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2016/06", fileIndex));
	}

	/**
	 * Part 1:
	 * Given the recording in your puzzle input, what is the error-corrected version of the message being sent?
	 * 
	 * Part 2:
	 * Given the recording in your puzzle input and this new decoding methodology, what is the original message that
	 * Santa is trying to send?
	 */
	public static String getResult(Part part, List<String> input) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < input.get(0).length(); i++) {
			buffer.append(getFrequencyChar(part, i, input));
		}
		return (buffer.toString());
	}

	/**
	 * Finds the letter that occurs the most or least at some index.
	 */
	public static char getFrequencyChar(Part part, int i, List<String> input) {
		CharFrequency frequency = new CharFrequency();
		for (String line : input) {
			frequency.add(line.charAt(i));
		}
		return (part == Part.ONE ? frequency.getHighestFrequency().getKey() : frequency.getLowestFrequency().getKey());
	}
}