package buri.aoc.y16.d21;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Day 21: Scrambled Letters and Hash
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("agcebfdh", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("afhdbegc", 0, true);
	}

	/**
	 * Part 1:
	 * Given the list of scrambling operations in your puzzle input, what is the result of scrambling abcdefgh?
	 *
	 * Part 2:
	 * What is the un-scrambled version of the scrambled password fbgdceah?
	 */
	protected String runString(Part part, List<String> input) {
		String password = (part == Part.ONE ? "abcdefgh" : "fbgdceah");
		List<Character> letters = new ArrayList<>();
		for (int i = 0; i < password.length(); i++) {
			letters.add(password.charAt(i));
		}
		if (part == Part.TWO) {
			Collections.reverse(input);
		}
		for (String command : input) {
			process(letters, command, part == Part.TWO);
		}

		StringBuffer buffer = new StringBuffer();
		for (Character letter : letters) {
			buffer.append(letter);
		}
		return (buffer.toString());
	}

	/**
	 * Processes a single command on the string.
	 */
	private static void process(List<Character> letters, String command, boolean undo) {
		String[] tokens = command.split(" ");
		if (tokens[0].equals("swap")) {
			// swap position X with position Y (symmetric)
			if (tokens[1].equals("position")) {
				int from = Integer.valueOf(tokens[2]);
				int to = Integer.valueOf(tokens[5]);
				char fromValue = letters.get(from);
				char toValue = letters.get(to);
				letters.set(from, toValue);
				letters.set(to, fromValue);
			}
			// swap letter X with letter Y (symmetric)
			else {
				int from = letters.indexOf(tokens[2].charAt(0));
				int to = letters.indexOf(tokens[5].charAt(0));
				letters.set(from, tokens[5].charAt(0));
				letters.set(to, tokens[2].charAt(0));
			}
		}
		// reverse positions X through Y (symmetric)
		else if (tokens[0].equals("reverse")) {
			int from = Integer.valueOf(tokens[2]);
			int to = Integer.valueOf(tokens[4]) + 1;
			List<Character> subset = letters.subList(from, to);
			Collections.reverse(subset);
			for (int i = from; i < to; i++) {
				letters.set(i, subset.get(i - from));
			}
		}
		// move position X to position Y (asymmetric)
		else if (tokens[0].equals("move")) {
			int from = (undo ? Integer.valueOf(tokens[5]) : Integer.valueOf(tokens[2]));
			int to = (undo ? Integer.valueOf(tokens[2]) : Integer.valueOf(tokens[5]));
			char value = letters.get(from);
			letters.remove(from);
			letters.add(to, value);
		}
		else if (tokens[0].equals("rotate") && tokens[1].equals("based")) {
			// rotate based on position of letter X (asymmetric)
			if (!undo) {
				int position = letters.indexOf(tokens[6].charAt(0));
				int rotation = 1 + position;
				if (position >= 4) {
					rotation++;
				}
				Collections.rotate(letters, rotation);
			}
			else {
				// Reverse-engineer the rotation to get back to where the letter used to be
				// This array is specific to 8-letter passwords.
				int[] rotations = new int[] { -1, -1, -6, -2, -7, -3, 0, -4 };
				char value = tokens[6].charAt(0);
				int rotation = rotations[letters.indexOf(value)];
				Collections.rotate(letters, rotation);
			}
		}
		// rotate left/right X steps (asymmetric)
		else if (tokens[0].equals("rotate")) {
			int rotation = Integer.valueOf(tokens[2]);
			if (tokens[1].equals("left")) {
				rotation = -1 * rotation;
			}
			Collections.rotate(letters, (undo ? -1 * rotation : rotation));
		}
	}
}