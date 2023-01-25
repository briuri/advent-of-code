package buri.aoc.y18.d01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 1: Chronal Calibration
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Boilerplate conversion for example input
	 */
	private static List<String> getInput(String values) {
		List<String> input = new ArrayList<>(Arrays.asList(values.split(" ")));
		return (input);
	}

	@Test
	public void testPart1Examples() {
		assertEquals(3, Puzzle.getResult(Part.ONE, getInput("+1 -2 +3 +1")));
		assertEquals(3, Puzzle.getResult(Part.ONE, getInput("+1 +1 +1")));
		assertEquals(0, Puzzle.getResult(Part.ONE, getInput("+1 +1 -2")));
		assertEquals(-6, Puzzle.getResult(Part.ONE, getInput("-1 -2 -3")));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(442, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Puzzle.getResult(Part.TWO, getInput("+1 -2 +3 +1")));
		assertEquals(0, Puzzle.getResult(Part.TWO, getInput("+1 -1")));
		assertEquals(10, Puzzle.getResult(Part.TWO, getInput("+3 +3 +4 -2 -4")));
		assertEquals(5, Puzzle.getResult(Part.TWO, getInput("-6 +3 +8 +5 -6")));
		assertEquals(14, Puzzle.getResult(Part.TWO, getInput("+7 +7 -2 -7 -4")));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(59908, result);
	}

	/**
	 * Part 1:
	 * Starting with a frequency of zero, what is the resulting frequency after all of the changes in frequency have
	 * been applied?
	 *
	 * Part 2:
	 * You notice that the device repeats the same frequency change list over and over. What is the first frequency your
	 * device reaches twice?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Integer> values = convertStringsToInts(input);
		Set<Integer> repeats = new HashSet<>();
		int sum = 0;
		while (true) {
			for (Integer value : values) {
				// Part 2: Stop as soon as a repeat occurs.
				if (part == Part.TWO && repeats.contains(sum)) {
					return (sum);
				}
				repeats.add(sum);
				sum += value;
			}
			// Part 1: Stop after one complete iteration.
			if (part == Part.ONE) {
				return (sum);
			}
		}
	}
}