package buri.aoc.template;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 00: TITLE
 * 
 * @author Brian Uri!
 */
public class Day00 extends Puzzle {

	/**
	 * Returns the input file HOW 
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2020a/00", fileIndex));
//		List<Data> list = new ArrayList<>();
//		for (String input : readFile("2020a/00", fileIndex)) {
//			list.add(new Data(input));
//		}
//		return (list);
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