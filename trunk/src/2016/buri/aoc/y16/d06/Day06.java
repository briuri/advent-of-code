package buri.aoc.y16.d06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.Puzzle;

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
	 */
	public static String getResult(Part part, List<String> input) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < input.get(0).length(); i++) {
			buffer.append(getFrequencyChar(part, i, input));
		}
		return (buffer.toString());
	}
	
	/**
	 * Finds the letter that occurs the most or leastat some index.
	 */
	public static char getFrequencyChar(Part part, int i, List<String> input) {
		Map<Character, Integer> frequency = new HashMap<>();
		for (String line : input) {
			char letter = line.charAt(i);
			if (frequency.get(letter) == null) {
				frequency.put(letter, 0);
			}
			frequency.put(letter, frequency.get(letter) + 1);
		}
		List<Map.Entry<Character, Integer>> list = new ArrayList<>(frequency.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
			@Override
			public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
				// Part one finds max, part two finds min.
				int compare = (part == Part.ONE ? o2.getValue() - o1.getValue() : o1.getValue() - o2.getValue());
				if (compare == 0) {
					compare = o1.getKey().compareTo(o2.getKey());
				}
				return (compare);
			}
		});
		return (list.get(0).getKey());
	}
}