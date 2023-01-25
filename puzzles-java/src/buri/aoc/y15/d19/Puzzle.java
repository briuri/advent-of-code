package buri.aoc.y15.d19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 19: Medicine for Rudolph
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(4, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(7, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(576, result);
	}
	@Test
	public void testPart2Examples() {
		assertEquals(3, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
		assertEquals(6, Puzzle.getResult(Part.TWO, Puzzle.getInput(4)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(207, result);
	}

	/**
	 * Part 1:
	 * How many distinct molecules can be created after all the different ways you can do one replacement on the
	 * medicine molecule?
	 *
	 * Part 2:
	 * What is the fewest number of steps to go from e to the medicine molecule?
	 */
	public static int getResult(Part part, List<String> input) {
		String molecule = input.remove(0);
		if (part == Part.ONE) {
			Set<String> results = new HashSet<>();
			for (String line : input) {
				String[] tokens = line.split(" => ");
				int index = molecule.indexOf(tokens[0]);
				while (index != -1) {
					StringBuilder builder = new StringBuilder(molecule);
					builder.replace(index, index + tokens[0].length(), tokens[1]);
					String result = builder.toString();
					results.add(result);
					index = molecule.indexOf(tokens[0], index + 1);
				}
			}
			return (results.size());
		}

		// Part TWO
		Map<String, String> replacements = new HashMap<>();
		for (String line : input) {
			String[] tokens = line.split(" => ");
			replacements.put(tokens[1], tokens[0]);
		}

		int steps = 0;
		while (steps == 0) {
			steps = getSteps(0, molecule, replacements);
		}
		return (steps);
	}

	/**
	 * Recursively try different random replacements until we get to e.
	 *
	 * Original solution was BFS that did not scale up to input size well.
	 */
	private static int getSteps(int depth, String molecule, Map<String, String> replacements) {
		if (molecule.equals("e")) {
			return (depth);
		}
		else {
			List<String> keys = new ArrayList<>(replacements.keySet());
			boolean replaced = false;
			while (!replaced) {
				int selection = new Random().nextInt(keys.size());
				String replacement = keys.remove(selection);
				Matcher matcher = Pattern.compile(replacement).matcher(molecule);
				if (matcher.find()) {
					StringBuilder builder = new StringBuilder(molecule);
					builder.replace(matcher.start(0), matcher.end(0), replacements.get(replacement));
					molecule = builder.toString();
					replaced = true;
				}
				if (keys.isEmpty()) {
					return (0);
				}
			}
			return (getSteps(depth + 1, molecule, replacements));
		}
	}
}