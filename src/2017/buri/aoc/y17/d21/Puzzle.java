package buri.aoc.y17.d21;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;

/**
 * Day 21: Fractal Art
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

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