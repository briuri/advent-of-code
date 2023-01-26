package buri.aoc.y18.d09;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Day 9: Marble Mania
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(37305L, 1, false);
		assertRun(429943L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(3615691746L, 0, true);
	}

	/**
	 * Part 1: What is the winning Elf's score?
	 *
	 * Part 2: What would the new winning Elf's score be if the number of the last marble were 100 times larger?
	 */
	protected long runLong(Part part, List<String> input) {
		int players = Integer.parseInt(input.get(0).split(" ")[0]);
		int max = Integer.parseInt(input.get(0).split("worth ")[1].split(" ")[0]);
		if (part == Part.TWO) {
			max = max * 100;
		}
		Circle circle = new Circle(max);
		List<Long> scores = new ArrayList<>(Collections.nCopies(players, 0L));

		int currentPlayer = 1;
		for (int i = 1; i < max + 1; i++) {
			Long score = circle.addMarble(i);
			Long currentScore = scores.get(currentPlayer - 1);
			scores.set(currentPlayer - 1, currentScore + score);

			currentPlayer++;
			if (currentPlayer > players) {
				currentPlayer = 1;
			}
		}
		return (Collections.max(scores));
	}
}