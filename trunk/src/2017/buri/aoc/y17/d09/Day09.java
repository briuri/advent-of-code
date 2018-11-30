package buri.aoc.y17.d09;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Your goal is to find the total score for all groups in your input.
 * 
 * @author Brian Uri!
 */
public class Day09 extends Puzzle {

	/**
	 * Input: A long string of groups and garbage.
	 * Output: The string with last linebreak trimmed.
	 */
	public static String getInput(int fileIndex) {
		return (readFile("2017/09", fileIndex).get(0));
	}

	/**
	 * Calculates the score of the groups.
	 * 
	 * Each group is assigned a score which is one more than the score of the group that immediately contains it. The
	 * outermost group gets a score of 1.
	 */
	public static int getResult(Part part, String input) {
		input = stripGarbage(input);
		// TODO: Calculate score.
		return (0);
	}

	/**
	 * Strips the garbage from a string.
	 * 
	 * Garbage begins with < and ends with >. Between those angle brackets, almost any character can appear, including {
	 * and }. Within garbage, < has no special meaning. Garbage always terminates properly.
	 */
	public static String stripGarbage(String input) {
		// TODO: Strip cancellations.
		// TODO: Strip garbage.
		return ("");
	}

	/**
	 * Counts the number of groups after stripping garbage.
	 * 
	 * Groups begin with { and end with }.
	 * Groups contain 0 or more comma-separated things: nested groups or garbage
	 */
	public static int countGroups(String input) {
		input = stripGarbage(input);
		// TODO: Count the groups
		return (0);
	}
}