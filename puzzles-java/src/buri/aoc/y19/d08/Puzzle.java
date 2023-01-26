package buri.aoc.y19.d08;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.CharFrequency;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 08: Space Image Format
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("1", 1, false);
		assertRun("2684", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(" ■\n■ ", 2, false);
		// YGRYZ
		assertRun("■   ■ ■■  ■■■  ■   ■■■■■ \n", 0, true);
	}

	/**
	 * Part 1:
	 * To make sure the image wasn't corrupted during transmission, the Elves would like you to find the layer that
	 * contains the fewest 0 digits. On that layer, what is the number of 1 digits multiplied by the number of 2 digits?
	 *
	 * Part 2:
	 * What message is produced after decoding your image?
	 */
	protected String runString(Part part, List<String> input) {
		int width = 25;
		if (input.get(0).length() < 15) {	// Part One Example
			width = 3;
		}
		else if (input.get(0).length() < 20) {	// Part Two Example
			width = 2;
		}
		int height = 6;
		if (input.get(0).length() < 20) {    // Part One and Two Examples
			height = 2;
		}

		// Load layers and reverse the order.
		StringBuffer buffer = new StringBuffer(input.get(0));
		List<String> layers = new ArrayList<>();
		while (buffer.length() > 0) {
			layers.add(buffer.substring(0, (width * height)));
			buffer.delete(0, width * height);
		}
		Collections.reverse(layers);

		if (part == Part.ONE) {
			Map<String, CharFrequency> frequencies = new HashMap<>();
			for (String layer : layers) {
				frequencies.put(layer, new CharFrequency(layer));
			}
			int minZeroes = Integer.MAX_VALUE;
			for (String layer : frequencies.keySet()) {
				minZeroes = Math.min(minZeroes, frequencies.get(layer).getFrequencyFor('0'));
			}
			int result = 0;
			for (String layer : frequencies.keySet()) {
				if (frequencies.get(layer).getFrequencyFor('0') == minZeroes) {
					CharFrequency frequency = new CharFrequency(layer);
					result = frequency.getFrequencyFor('1') * frequency.getFrequencyFor('2');
					break;
				}
			}
			return (String.valueOf(result));
		}

		// Part TWO
		// Load the lowest layer into output buffer then mask with upper layers.
		StringBuffer output = new StringBuffer(layers.get(0));
		for (String layer : layers) {
			for (int i = 0; i < layer.length(); i++) {
				char value = layer.charAt(i);
				if (value == '2') {
					continue;
				}
				output.setCharAt(i, value);
			}
		}
		for (int y = 1; y < height; y++) {
			output.insert(width * y + (y - 1), '\n');
		}
		// Use ' ' for black and '■' for white for ease of reading.
		return (output.toString().replace('0', ' ').replace('1', '■'));
	}

}