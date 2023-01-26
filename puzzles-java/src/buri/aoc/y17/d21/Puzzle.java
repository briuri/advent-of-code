package buri.aoc.y17.d21;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 21: Fractal Art
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(139L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(1857134L, 0, true);
	}

	/**
	 * Part 1:
	 * How many pixels stay on after 5 iterations?
	 *
	 * Part 2:
	 * How many pixels stay on after 18 iterations?
	 */
	protected long runLong(Part part, List<String> input) {
		final int iterations = (part == Part.ONE ? 5 : 18);
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