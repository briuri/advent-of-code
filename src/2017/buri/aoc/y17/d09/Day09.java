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
		if (part == Part.ONE) {
			input = destroyGarbage(input);
			int total = 0;
			int levelScore = 1;
			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) == '{') {
					total += levelScore;
					levelScore++;
				}
				else if (input.charAt(i) == '}') {
					levelScore--;
				}
				// Ignore commas.
			}
			return (total);
		}
		// Part.TWO
		return (countGarbage(input));
	}

	/**
	 * Counts the number of groups after erasing garbage.
	 * 
	 * Groups begin with { and end with }.
	 * Groups contain 0 or more comma-separated things: nested groups or garbage
	 */
	public static int countGroups(String input) {
		input = destroyGarbage(input);
		// Counts number of occurrences of {
		int groupOpenerCount = input.length() - input.replace("{", "").length();
		return (groupOpenerCount);
	}

	/**
	 * Strips garbage from a string.
	 * 
	 * Garbage begins with < and ends with >. Between those angle brackets, almost any character can appear, including {
	 * and }. Within garbage, < has no special meaning. Garbage always terminates properly.
	 * 
	 * Any character that comes after ! should be ignored, including <, >, and even another !.
	 */
	public static String destroyGarbage(String input) {
		// Allow us to use whitespace to represent erased characters.
		StringBuffer buffer = new StringBuffer(input.replace(" ", "_"));
		eraseCancellations(buffer);
		eraseGarbage(buffer);
		// After all loops, cut all the spaces out.
		return (buffer.toString().replaceAll(" ", ""));
	}

	/**
	 * Counts the garbage characters, ignoring !, the char cancelled by that !, and the enclosing <>s.
	 */
	public static int countGarbage(String input) {
		// Allow us to use whitespace to represent erased characters.
		StringBuffer buffer = new StringBuffer(input.replace(" ",  "_"));
		eraseCancellations(buffer);
		String inputWithoutCancellations = buffer.toString().replaceAll(" ", "");
		int lengthWithoutCancellations = inputWithoutCancellations.length();
		
		buffer = new StringBuffer(inputWithoutCancellations);
		int garbageCloserCount = inputWithoutCancellations.length() - inputWithoutCancellations.replace(">",
			"").length();
		eraseGarbage(buffer);
		String cleanInput = buffer.toString().replaceAll(" ", "");
		
		// Count how many spaces were removed, but ignore 2 (<>) for each group of garbage.
		return (lengthWithoutCancellations - cleanInput.length() - (2 * garbageCloserCount));
	}

	/**
	 * Replaces some subset of the buffer with whitespace.
	 */
	private static void replaceWhitespace(StringBuffer buffer, int start, int end) {
		buffer.replace(start, end + 1, String.format("%1$-" + (end + 1 - start) + "s", ""));
	}
	
	/**
	 * Replaces cancellations with tabs.
	 */
	private static void eraseCancellations(StringBuffer buffer) {
		for (int i = 0; i < buffer.length(); i++) {
			if (buffer.charAt(i) == '!') {
				// Assume input is well-formed, so ! is never at end of buffer.
				replaceWhitespace(buffer, i, i + 1);
			}
		}
	}

	/**
	 * Replaces garbage with tabs.
	 */
	private static void eraseGarbage(StringBuffer buffer) {
		for (int i = 0; i < buffer.length(); i++) {
			if (buffer.charAt(i) == '<') {
				// Assume input is well-formed, so there is always a closing >.
				replaceWhitespace(buffer, i, buffer.indexOf(">", i));
			}
		}
	}
}