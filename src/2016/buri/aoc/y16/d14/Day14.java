package buri.aoc.y16.d14;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.MD5Hash;

/**
 * Day 14: One-Time Pad
 * 
 * @author Brian Uri!
 */
public class Day14 extends BasePuzzle {

	private static final Pattern THREES = Pattern.compile("([a-f0-9])\\1{2}");

	/**
	 * Part 1:
	 * Given the actual salt in your puzzle input, what index produces your 64th one-time pad key?
	 * 
	 * Part 2:
	 * Using 2016 extra MD5 calls of key stretching, what index now produces your 64th one-time pad key?
	 * 
	 * TODO: Optimize Part 2 (takes 33 minutes for real input).
	 */
	public static int getResult(Part part, String salt) {
		MD5Hash hasher = new MD5Hash();
		Map<Integer, String> hashes = new HashMap<>();

		int keyCount = 0;
		int i = -1;
		while (keyCount < 64) {
			i++;
			if (hashes.get(i) == null) {
				hashes.put(i, getHash(part, hasher, salt + i));
			}
			Matcher matcher = THREES.matcher(hashes.get(i));
			if (matcher.find()) {
				char repeating = matcher.group(0).charAt(0);

				for (int j = i + 1; j < i + 1001; j++) {
					hashes.put(j, getHash(part, hasher, salt + j));
					Pattern pattern = Pattern.compile("(" + repeating + ")\\1{4}");
					matcher = pattern.matcher(hashes.get(j));
					if (matcher.find()) {
						keyCount++;
						break;
					}
				}
			}
		}
		return (i);
	}

	/**
	 * Returns the hash according to the part-specific rules.
	 */
	private static String getHash(Part part, MD5Hash hasher, String input) {
		int times = (part == Part.ONE ? 1 : 2017);
		for (int i = 0; i < times; i++) {
			input = hasher.getHash(input);
		}
		return (input);
	}
}