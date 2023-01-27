package buri.aoc.y17.d14;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 14: Disk Defragmentation
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(8108L, 1, false);
		assertRun(8140L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(1242L, 1, false);
		assertRun(1182L, 0, true);
	}

	/**
	 * Part 1:
	 * Given your actual key string, how many squares are used?
	 *
	 * Part 2:
	 * How many regions are present given your key string?
	 */
	protected long runLong(Part part, List<String> input) {
		List<String> diskRows = new ArrayList<>();
		for (int i = 0; i < 128; i++) {
			String hex = getKnotHashFor(input.get(0) + "-" + i);
			diskRows.add(convertToBinary(hex));
		}
		if (part == Part.ONE) {
			int used = 0;
			for (String row : diskRows) {
				used += row.replaceAll("0", "").length();
			}
			return (used);
		}
		// Part TWO
		Disk disk = new Disk(diskRows);
		return (disk.countRegions());
	}

	/**
	 * Marshals the raw data into the form needed for a knot hash and returns the knot hash hex string.
	 */
	private static String getKnotHashFor(String input) {
		return (new buri.aoc.y17.d10.Puzzle().getKnotHash(input));
	}

	/**
	 * Converts each character of a 32-character hex string into a 4-digit binary representation.
	 */
	private static String convertToBinary(String hex) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < hex.length(); i++) {
			int base10 = Integer.parseInt(String.valueOf(hex.charAt(i)), 16);
			String binary = Integer.toBinaryString(base10);
			for (int j = 0; j < 4 - binary.length(); j++) {
				builder.append("0");
			}
			builder.append(binary);
		}
		return (builder.toString());
	}
}