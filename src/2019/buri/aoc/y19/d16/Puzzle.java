package buri.aoc.y19.d16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 16: Flawed Frequency Transmission
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	private static final int[] BASE_PATTERN = new int[] { 0, 1, 0, -1 };
	private static final Map<String, List<Integer>> CACHED_PATTERNS = new HashMap<>();

	/**
	 * Returns the input file as a list of digits
	 */
	public static List<Integer> getInput(int fileIndex) {
		List<Integer> input = new ArrayList<>();
		for (Character value : readFile(fileIndex).get(0).toCharArray()) {
			input.add(Character.getNumericValue(value));
		}
		return (input);
	}

	/**
	 * Part 1:
	 * After 100 phases of FFT, what are the first eight digits in the final output list?
	 * 
	 * Part 2:
	 * After repeating your input signal 10000 times and running 100 phases of FFT, what is the eight-digit message
	 * embedded in the final output list?
	 */
	public static String getResult(Part part, List<Integer> input, int phases) {
		List<Integer> current = new ArrayList<>(input);
		if (part == Part.ONE) {
			int length = current.size();
			for (int phase = 0; phase < phases; phase++) {
				List<Integer> next = new ArrayList<>();
				for (int outputDigit = 0; outputDigit < length; outputDigit++) {
					List<Integer> pattern = getPattern(outputDigit, current.size());
					int sum = 0;
					for (int position = 0; position < length; position++) {
						sum += current.get(position) * pattern.get(position);
					}
					next.add(Math.abs(sum % 10));
				}
				current = new ArrayList(next);
			}
			return (toString(current.subList(0, 8)));
		}

		// Part TWO
		int offset = Integer.valueOf(toString(input.subList(0, 7)));
		for (int i = 0; i < 10000 - 1; i++) {
			current.addAll(input);
		}

		/*
		 * After halfway point in pattern generation, 0-to-outputPosition is always 0 and outputPosition-to-end is
		 * always 1. Since the offset is greater than half the list size, we can just ignore the slots before the offset
		 * and use simple sums for the remaining slots.
		 */
		int length = current.size();
		for (int phase = 0; phase < phases; phase++) {
			// Pre-filled list with all 0s in it, so we can directly set at an index.
			List<Integer> next = Arrays.asList(new Integer[length]);

			// Reduce the math we do by going in reverse and cumulatively summing.
			int partialSum = 0;
			for (int pos = length - 1; pos >= offset; pos--) {
				partialSum += current.get(pos);
				next.set(pos, Math.abs(partialSum % 10));
			}
			current = new ArrayList(next);
		}
		return (toString(current.subList(offset, offset + 8)));
	}

	/**
	 * Generates a pattern based on output position (or looks it up in cache).
	 */
	protected static List<Integer> getPattern(int outputPosition, int length) {
		String key = outputPosition + "-" + length;
		List<Integer> pattern = CACHED_PATTERNS.get(key);
		if (pattern == null) {
			pattern = new ArrayList<>();
			while (pattern.size() < length + 1) {
				for (int i = 0; i < BASE_PATTERN.length; i++) {
					for (int j = 0; j < outputPosition + 1; j++) {
						pattern.add(BASE_PATTERN[i % BASE_PATTERN.length]);
					}
				}
			}
			// Remove head and any beyond the length.
			pattern = new ArrayList<>(pattern.subList(1, length + 1));
			CACHED_PATTERNS.put(key, pattern);
		}
		return (pattern);
	}

	/**
	 * Converts a list of 1-digit integers into a string.
	 */
	protected static String toString(List<Integer> numbers) {
		StringBuffer buffer = new StringBuffer();
		for (Integer number : numbers) {
			buffer.append(number);
		}
		return (buffer.toString());
	}
}