package buri.aoc.y18.d09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 9: Marble Mania
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(32, Puzzle.getResult(9, 25));
		assertEquals(8317, Puzzle.getResult(10, 1618));
		assertEquals(146373, Puzzle.getResult(13, 7999));
		assertEquals(2764, Puzzle.getResult(17, 1104));
		assertEquals(54718, Puzzle.getResult(21, 6111));
		assertEquals(37305, Puzzle.getResult(30, 5807));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(411, 72059);
		toConsole(result);
		assertEquals(429943, result);
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(411, 7205900);
		toConsole(result);
		assertEquals(3615691746L, result);
	}

	/**
	 * Part 1: What is the winning Elf's score?
	 *
	 * Part 2: What would the new winning Elf's score be if the number of the last marble were 100 times larger?
	 */
	public static long getResult(int players, int max) {
		Circle circle = new Circle(max);
		List<Long> scores = new ArrayList<>(Collections.nCopies(players, Long.valueOf(0)));

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