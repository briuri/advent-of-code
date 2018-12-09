package buri.aoc.y17.d14;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;
import buri.aoc.y17.d10.Day10;

/**
 * The disk in question consists of a 128x128 grid; each square of the grid is either free or used. On this disk, the
 * state of the grid is tracked by the bits in a sequence of knot hashes.
 * 
 * A total of 128 knot hashes are calculated, each corresponding to a single row in the grid; each hash contains 128
 * bits which correspond to individual grid squares. Each bit of a hash indicates whether that square is free (0) or
 * used (1).
 * 
 * The hash inputs are a key string (your puzzle input), a dash, and a number from 0 to 127 corresponding to the row.
 * For example, if your key string were flqrgnkx, then the first row would be given by the bits of the knot hash of
 * flqrgnkx-0, the second row from the bits of the knot hash of flqrgnkx-1, and so on until the last row, flqrgnkx-127.
 * 
 * The output of a knot hash is traditionally represented by 32 hexadecimal digits; each of these digits correspond to 4
 * bits, for a total of 4 * 32 = 128 bits. To convert to bits, turn each hexadecimal digit to its equivalent binary
 * value, high-bit first: 0 becomes 0000, 1 becomes 0001, e becomes 1110, f becomes 1111, and so on; a hash that begins
 * with a0c2017... in hexadecimal would begin with 10100000110000100000000101110000... in binary.
 * 
 * @author Brian Uri!
 */
public class Day14 extends Puzzle {
	
	/**
	 * Part 1:
	 * Given your actual key string, how many squares are used?
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
			char character = input.charAt(i);
			data.add((int) character);
		}
		data.add(17);
		data.add(31);
		data.add(73);
		data.add(47);
		data.add(23);
		return (Day10.getResult(Part.TWO, 256, data));
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