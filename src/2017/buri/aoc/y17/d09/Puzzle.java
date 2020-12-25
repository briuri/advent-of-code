package buri.aoc.y17.d09;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 9: Stream Processing
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static String getInput(int fileIndex) {
		return (readFile(fileIndex).get(0));
	}

	/**
	 * Part 1:
	 * What is the total score for all groups in your input?
	 * 
	 * Part 2:
	 * How many non-cancelled characters are within the garbage in your puzzle input?
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
		// Convert whitespace to underscores so we can reserve whitespace to represent erased characters.
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
		// Convert whitespace to underscores so we can reserve whitespace to represent erased characters.
		StringBuffer buffer = new StringBuffer(input.replace(" ", "_"));
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
	 * Replaces cancellations with tabs.
	 */
	private static void eraseCancellations(StringBuffer buffer) {
		for (int i = 0; i < buffer.length(); i++) {
			if (buffer.charAt(i) == '!') {
				// Assume input is well-formed, so ! is never at end of buffer.
				replaceWithWhitespace(buffer, i, i + 1);
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
				replaceWithWhitespace(buffer, i, buffer.indexOf(">", i));
			}
		}
	}

	/**
	 * Replaces some subset of the buffer with whitespace.
	 */
	private static void replaceWithWhitespace(StringBuffer buffer, int start, int end) {
		end = end + 1; // exclusive index
		buffer.replace(start, end, String.format("%1$-" + (end - start) + "s", ""));
	}
}