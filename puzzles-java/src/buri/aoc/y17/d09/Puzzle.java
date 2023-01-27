package buri.aoc.y17.d09;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 9: Stream Processing
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(12396L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(6346L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the total score for all groups in your input?
	 *
	 * Part 2:
	 * How many non-cancelled characters are within the garbage in your puzzle input?
	 */
	protected long runLong(Part part, List<String> input) {
		String line = input.get(0);
		if (part == Part.ONE) {
			line = destroyGarbage(line);
			int total = 0;
			int levelScore = 1;
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '{') {
					total += levelScore;
					levelScore++;
				}
				else if (line.charAt(i) == '}') {
					levelScore--;
				}
				// Ignore commas.
			}
			return (total);
		}
		// Part.TWO
		return (countGarbage(line));
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
		// Convert whitespace to underscores, so we can reserve whitespace to represent erased characters.
		StringBuilder builder = new StringBuilder(input.replace(" ", "_"));
		eraseCancellations(builder);
		eraseGarbage(builder);
		// After all loops, cut all the spaces out.
		return (builder.toString().replaceAll(" ", ""));
	}

	/**
	 * Counts the garbage characters, ignoring !, the char cancelled by that !, and the enclosing <>s.
	 */
	public static int countGarbage(String input) {
		// Convert whitespace to underscores, so we can reserve whitespace to represent erased characters.
		StringBuilder builder = new StringBuilder(input.replace(" ", "_"));
		eraseCancellations(builder);
		String inputWithoutCancellations = builder.toString().replaceAll(" ", "");
		int lengthWithoutCancellations = inputWithoutCancellations.length();

		builder = new StringBuilder(inputWithoutCancellations);
		int garbageCloserCount = inputWithoutCancellations.length() - inputWithoutCancellations.replace(">",
			"").length();
		eraseGarbage(builder);
		String cleanInput = builder.toString().replaceAll(" ", "");

		// Count how many spaces were removed, but ignore 2 (<>) for each group of garbage.
		return (lengthWithoutCancellations - cleanInput.length() - (2 * garbageCloserCount));
	}

	/**
	 * Replaces cancellations with tabs.
	 */
	private static void eraseCancellations(StringBuilder builder) {
		for (int i = 0; i < builder.length(); i++) {
			if (builder.charAt(i) == '!') {
				// Assume input is well-formed, so ! is never at end of builder.
				replaceWithWhitespace(builder, i, i + 1);
			}
		}
	}

	/**
	 * Replaces garbage with tabs.
	 */
	private static void eraseGarbage(StringBuilder builder) {
		for (int i = 0; i < builder.length(); i++) {
			if (builder.charAt(i) == '<') {
				// Assume input is well-formed, so there is always a closing >.
				replaceWithWhitespace(builder, i, builder.indexOf(">", i));
			}
		}
	}

	/**
	 * Replaces some subset of the builder with whitespace.
	 */
	private static void replaceWithWhitespace(StringBuilder builder, int start, int end) {
		end = end + 1; // exclusive index
		builder.replace(start, end, String.format("%1$-" + (end - start) + "s", ""));
	}
}