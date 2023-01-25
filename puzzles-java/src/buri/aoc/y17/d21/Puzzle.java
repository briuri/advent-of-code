package buri.aoc.y17.d21;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 21: Fractal Art
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(12, Puzzle.getResult(Puzzle.getInput(1), 2));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Puzzle.getInput(0), 5);
		toConsole(result);
		assertEquals(139, result);
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Puzzle.getInput(0), 18);
		toConsole(result);
		assertEquals(1857134, result);
	}

	/**
	 * Part 1:
	 * How many pixels stay on after 5 iterations?
	 *
	 * Part 2:
	 * How many pixels stay on after 18 iterations?
	 */
	public static int getResult(List<String> input, int iterations) {
		List<Rule> rules = new ArrayList<>();
		for (String line : input) {
			rules.add(new Rule(line));
		}

		Image image = new Image(rules);
		for (int i = 0; i < iterations; i++) {
			image.fractalize();
		}
		return (image.getLitPixels());
	}
}