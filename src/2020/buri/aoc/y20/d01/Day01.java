package buri.aoc.y20.d01;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 01: TITLE
 *
 * @author Brian Uri!
 */
public class Day01 extends BasePuzzle {

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
	public static int getResult(Part part, List<String> input) {
		if (part == Part.ONE) {
			return (0);
		}
		return (0);
	}

	/**
	 *
	 */
	protected static String doSomething() {
		return ("");
	}
}