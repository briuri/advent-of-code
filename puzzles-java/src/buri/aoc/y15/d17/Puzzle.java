package buri.aoc.y15.d17;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 17: No Such Thing as Too Much
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(4, Puzzle.getResult(Part.ONE, 25, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, 150, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1638, result);
	}
	@Test
	public void testPart2Examples() {
		assertEquals(3, Puzzle.getResult(Part.TWO, 25, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, 150, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(17, result);
	}

	/**
	 * Part 1:
	 * Filling all containers entirely, how many different combinations of containers can exactly fit all 150 liters of
	 * eggnog?
	 *
	 * Part 2:
	 * How many different ways can you fill that number of containers and still hold exactly 150 litres?
	 */
	public static int getResult(Part part, int end, List<String> input) {
		List<Integer> containers = new ArrayList<>();
		for (String line : input) {
			containers.add(Integer.valueOf(line));
		}
		Collections.sort(containers);
		Collections.reverse(containers);

		Map<Integer, Integer> frequency = new HashMap<>();
		int combos = getCombos(0, end, containers, 0, frequency);
		if (part == Part.ONE) {
			return (combos);
		}

		// Part TWO
		int minContainers = Integer.MAX_VALUE;
		for (Integer key : frequency.keySet()) {
			minContainers = Math.min(minContainers, key);
		}
		return (frequency.get(minContainers));
	}

	/**
	 * Recursively figure out how many combinations can be made to get to the target value.
	 */
	private static int getCombos(int start, int end, List<Integer> choices, int depth,
		Map<Integer, Integer> frequency) {
		if (start > end) {
			return (0);
		}
		if (start == end) {
			frequency.put(depth, frequency.getOrDefault(depth, 0) + 1);
			return (1);
		}
		int combos = 0;
		for (int i = 0; i < choices.size(); i++) {
			combos += getCombos(start + choices.get(i), end, choices.subList(i + 1, choices.size()), depth + 1,
				frequency);
		}
		return (combos);
	}
}