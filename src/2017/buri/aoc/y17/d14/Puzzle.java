package buri.aoc.y17.d14;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 14: Disk Defragmentation
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * Given your actual key string, how many squares are used?
	 *
	 * Part 2:
	 * How many regions are present given your key string?
	 */
	public static int getResult(Part part, String input) {
		List<String> diskRows = new ArrayList<>();
		for (int i = 0; i < 128; i++) {
			String hex = getKnotHashFor(input + "-" + i);
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
		List<Integer> data = new ArrayList<>();
		for (int i = 0; i < input.length(); i++) {
			data.add((int) input.charAt(i));
		}
		return (buri.aoc.y17.d10.Puzzle.getResult(Part.TWO, 256, data));
	}

	/**
	 * Converts each character of a 32-character hex string into a 4-digit binary representation.
	 */
	private static String convertToBinary(String hex) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < hex.length(); i++) {
			int base10 = Integer.parseInt(String.valueOf(hex.charAt(i)), 16);
			String binary = Integer.toBinaryString(base10);
			for (int j = 0; j < 4 - binary.length(); j++) {
				buffer.append("0");
			}
			buffer.append(binary);
		}
		return (buffer.toString());
	}
}