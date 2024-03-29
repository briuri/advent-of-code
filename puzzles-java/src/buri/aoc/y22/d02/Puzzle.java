package buri.aoc.y22.d02;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 02: Rock Paper Scissor
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(15L, 1, false);
		assertRun(12586L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(12L, 1, false);
		assertRun(13193L, 0, true);
	}

	private static final int ROCK = 1;
	private static final int PAPER = 2;
	private static final int SCISSORS = 3;
	private static final int WIN = 6;
	private static final int DRAW = 3;

	/**
	 * Part 1:
	 * What would your total score be if everything goes exactly according to your strategy guide?
	 *
	 * Part 2:
	 * What would your total score be if everything goes exactly according to your strategy guide?
	 */
	protected long runLong(Part part, List<String> input) {
		long score = 0;
		for (String line : input) {
			String[] tokens = line.split(" ");
			int play = getPlay(tokens[0]);
			int counterplay = (part == Part.ONE ? getPlay(tokens[1]) : getCounterplay(play, tokens[1]));

			score += counterplay;
			if (play == counterplay) {
				score += DRAW;
			}
			else if (play == ROCK && counterplay == PAPER
					|| play == PAPER && counterplay == SCISSORS
					|| play == SCISSORS && counterplay == ROCK) {
				score += WIN;
			}
		}
		return (score);
	}

	/**
	 * Converts an input symbol into one of the possible RPS plays.
	 */
	protected static int getPlay(String symbol) {
		if (symbol.equals("A") || symbol.equals("X")) {
			return (ROCK);
		}
		if (symbol.equals("B") || symbol.equals("Y")) {
			return (PAPER);
		}
		return (SCISSORS);
	}

	/**
	 * Returns the counterplay that will result in the specified outcome.
	 */
	protected static int getCounterplay(int play, String outcome) {
		int counter;
		if (outcome.equals("X")) {    // WIN
			counter = play - 1;
			if (counter == 0) {
				counter = 3;
			}
		}
		else if (outcome.equals("Y")) { // DRAW
			counter = play;
		}
		else { // LOSE
			counter = play + 1;
			if (counter > 3) {
				counter = 1;
			}
		}
		return (counter);
	}
}