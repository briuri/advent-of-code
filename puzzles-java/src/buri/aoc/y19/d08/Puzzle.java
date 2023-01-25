package buri.aoc.y19.d08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.CharFrequency;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 08: Space Image Format
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals("1", Puzzle.getResult(Part.ONE, Puzzle.getInput(1).get(0), 3, 2));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0).get(0), 25, 6);
		toConsole(result);
		assertEquals("2684", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(" ■\n■ ", Puzzle.getResult(Part.TWO, Puzzle.getInput(2).get(0), 2, 2));
	}

	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0).get(0), 25, 6);
		toConsole(result);
		// YGRYZ
		assertEquals(
				"■   ■ ■■  ■■■  ■   ■■■■■ \n■   ■■  ■ ■  ■ ■   ■   ■ \n ■ ■ ■    ■  ■  ■ ■   ■  \n  ■  ■ ■■ ■■■    ■   ■   \n  ■  ■  ■ ■ ■    ■  ■    \n  ■   ■■■ ■  ■   ■  ■■■■ ",
				result);
	}

	/**
	 * Part 1:
	 * To make sure the image wasn't corrupted during transmission, the Elves would like you to find the layer that
	 * contains the fewest 0 digits. On that layer, what is the number of 1 digits multiplied by the number of 2 digits?
	 *
	 * Part 2:
	 * What message is produced after decoding your image?
	 */
	public static String getResult(Part part, String input, int width, int height) {
		// Load layers and reverse the order.
		StringBuffer buffer = new StringBuffer(input);
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