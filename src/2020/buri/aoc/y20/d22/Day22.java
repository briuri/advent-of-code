package buri.aoc.y20.d22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 22: TITLE
 *
 * @author Brian Uri!
 */
public class Day22 extends BasePuzzle {

	/**
	 * Returns the input file HOW
	 */
	public static List<String> getInput(int fileIndex) {
// 1 string per line
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
//		CharGrid grid = new CharGrid(new Pair(input.get(0).length(), input.size()));
//		for (int y = 0; y < grid.getHeight(); y++) {
//			for (int x = 0; x < grid.getWidth(); x++) {
//				grid.set(x, y, input.get(y).charAt(x));
//			}
//		}

		long sum = 0;
		for (String line : input) {

		}
		if (part == Part.ONE) {
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