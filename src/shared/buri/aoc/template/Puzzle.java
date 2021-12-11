package buri.aoc.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 00: TITLE
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));

// 1 integer per line
		// return (convertStringsToInts(readFile(fileIndex)));
// All integers on first line
		// String[] stringInts = readFile(fileIndex).get(0).split(" ");
		// return (convertStringsToInts(Arrays.asList(stringInts)));
// 1 data object per line
		// List<Data> list = new ArrayList<>();
		// for (String input : readFile(fileIndex)) {
		// list.add(new Data(input));
		// }
		// return (list);
	}

	/**
	 * Part 1:
	 * QUESTION
	 *
	 * Part 2:
	 * QUESTION
	 */
	public static long getResult(Part part, List<String> input) {
//		Grid grid = new Grid(input);

		long sum = 0;
		for (String line : input) {

		}
		if (part == Part.ONE) {
//			return (grid.getValue());
			return (sum);
		}
		return (sum);
	}

	/**
	 *
	 */
	protected static String doSomething() {
		return ("");
	}
}