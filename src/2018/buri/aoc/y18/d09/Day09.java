package buri.aoc.y18.d09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buri.aoc.BasePuzzle;

/**
 * Day 9: Marble Mania
 * 
 * @author Brian Uri!
 */
public class Day09 extends BasePuzzle {

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