package buri.aoc.y16.d15;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 15: Timing is Everything
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(5L, 1, false);
		assertRun(203660L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(2408135L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the first time you can press the button to get a capsule?
	 *
	 * Part 2:
	 * With this new disc, and counting again starting from time=0 with the configuration in your puzzle input, what is
	 * the first time you can press the button to get another capsule?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Disc> discs = new ArrayList<>();
		for (String line : input) {
			discs.add(new Disc(line));
		}
		if (part == Part.TWO) {
			discs.add(new Disc("Disc #7 has 11 positions; at time=0, it is at position 0."));
		}
		int time = 0;
		while (true) {
			boolean allOpen = true;
			for (Disc disc : discs) {
				allOpen = allOpen && disc.isOpen(time);
			}
			if (allOpen) {
				return (time);
			}
			time++;
		}
	}
}