package buri.aoc.y15.d08;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 8: Matchsticks
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(12L, 1, false);
		assertRun(1333L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(19L, 1, false);
		assertRun(2046L, 0, true);
	}

	/**
	 * Part 1:
	 * Disregarding the whitespace in the file, what is the number of characters of code for string literals minus the
	 * number of characters in memory for the values of the strings in total for the entire file?
	 *
	 * Part 2:
	 * Your task is to find the total number of characters to represent the newly encoded strings minus the number of
	 * characters of code in each original string literal.
	 */
	protected long runLong(Part part, List<String> input) {
		int numCharsCode = 0;
		int numCharsMemory = 0;
		int numCharsEncoded = 0;
		for (String line : input) {
			numCharsCode += line.length();
			String memory = line.substring(1, line.length() - 1).replace("\\\"", "1").replace("\\\\", "1").replaceAll(
				"\\\\x[a-f0-9]{2}", "1");
			numCharsMemory += memory.length();
			String encoded = line.substring(1, line.length() - 1).replace("\\\\", "1234").replace("\\\"",
				"1234").replace("\\x", "123");
			encoded = "123" + encoded + "123";
			numCharsEncoded += encoded.length();
		}
		if (part == Part.ONE) {
			return (numCharsCode - numCharsMemory);
		}
		// Part TWO
		return (numCharsEncoded - numCharsCode);
	}
}