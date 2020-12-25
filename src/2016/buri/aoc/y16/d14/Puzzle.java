package buri.aoc.y16.d14;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 14: One-Time Pad
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	private static final Pattern THREES = Pattern.compile("([a-f0-9])\\1{2}");

	private static final int[] HEX_ASCII = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };

	/**
	 * Part 1:
	 * Given the actual salt in your puzzle input, what index produces your 64th one-time pad key?
	 * 
	 * Part 2:
	 * Using 2016 extra MD5 calls of key stretching, what index now produces your 64th one-time pad key?
	 */
	public static int getResult(Part part, String salt) {
		// Using our MD5Hash class is too slow for this puzzle.
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		List<Integer> keys = new ArrayList<>();
		Map<Integer, String> hashes = new HashMap<>();
		int index = -1;
		while (keys.size() < 64) {
			index++;
			if (!hashes.containsKey(index)) {
				String current = salt + index;
				hashes.put(index, getHash(part, digest, current.getBytes()));
			}
			Matcher matcher = THREES.matcher(hashes.get(index));
			if (matcher.find()) {
				char repeating = matcher.group(0).charAt(0);
				for (int i = index + 1; i < index + 1000; i++) {
					if (!hashes.containsKey(i)) {
						String current = salt + i;
						hashes.put(i, getHash(part, digest, current.getBytes()));		
					}
					Pattern pattern = Pattern.compile("(" + repeating + ")\\1{4}");
					matcher = pattern.matcher(hashes.get(i));
					if (matcher.find()) {
						keys.add(index);
						break;
					}
				}
			}
		}
		return (index);
	}

	/**
	 * Returns the hash according to the part-specific rules.
	 */
	private static String getHash(Part part, MessageDigest digest, byte[] input) {
		byte[] hash = digest.digest(input);
		int times = (part == Part.ONE ? 0 : 2016);
		for (int i = 0; i < times; i++) {
			hash = toHash(digest, hash);
		}
		return (toHex(hash));
	}

	/**
	 * Hashes the bytes.
	 */
	private static byte[] toHash(MessageDigest digest, byte[] bytes) {
		byte[] output = new byte[bytes.length * 2];
		for (int i = 0, j = 0; i < bytes.length; i++, j += 2) {
			output[j] = (byte) HEX_ASCII[(bytes[i] >> 4) & 0xf];
			output[j + 1] = (byte) HEX_ASCII[bytes[i] & 0xf];
		}
		digest.update(output);
		return (digest.digest());
	}

	/**
	 * Converts bytes to hex.
	 */
	private static String toHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte value : bytes) {
			sb.append((char) HEX_ASCII[(value >> 4) & 0xf]);
			sb.append((char) HEX_ASCII[value & 0xf]);
		}
		return (sb.toString());
	}
}