package buri.aoc.y22.d06;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 06: Tuning Trouble
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many characters need to be processed before the first start-of-packet marker is detected?
	 *
	 * Part 2:
	 * How many characters need to be processed before the first start-of-message marker is detected?
	 */
	public static long getResult(Part part, List<String> input) {
		String line = input.get(0);
		int size = (part == Part.ONE ? 4 : 14);
		for (int i = size - 1; i < line.length(); i++) {
			Set<Character> recent = new HashSet<>();
			for (int j = 0; j < size; j++) {
				recent.add(line.charAt(i - j));
			}
			if (recent.size() == size) {
				return (i + 1);
			}
		}
		throw new RuntimeException("Never found a unique string.");
	}
}