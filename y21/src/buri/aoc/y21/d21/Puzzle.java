package buri.aoc.y21.d21;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import buri.aoc.BasePuzzle;
import buri.aoc.data.tuple.Quad;

/**
 * Day 21: Dirac Dice
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What do you get if you multiply the score of the losing player by the number of times the die was rolled during
	 * the game?
	 */
	public static long getPart1Result(int position1, int position2) {
		int score1 = 0;
		int score2 = 0;
		Die die = new Die();
		while (true) {
			position1 = (position1 + die.roll() + die.roll() + die.roll() - 1) % 10 + 1;
			score1 += position1;
			if (score1 >= 1000) {
				return (score2 * die.getRollCount());
			}

			position2 = (position2 + die.roll() + die.roll() + die.roll() - 1) % 10 + 1;
			score2 += position2;
			if (score2 >= 1000) {
				return (score2 * die.getRollCount());
			}
		}
	}

	/**
	 * Part 2:
	 * Find the player that wins in more universes; in how many universes does that player win?
	 */
	public static long getPart2Result(int start1, int start2) {
		// All 27 dice roll possibilities and how frequently each sum of the 3 rolls occurs.
		final Map<Integer, Integer> ALL_ROLLS = new TreeMap<>();
		ALL_ROLLS.put(3, 1);
		ALL_ROLLS.put(4, 3);
		ALL_ROLLS.put(5, 6);
		ALL_ROLLS.put(6, 7);
		ALL_ROLLS.put(7, 6);
		ALL_ROLLS.put(8, 3);
		ALL_ROLLS.put(9, 1);

		// Keep track of all different game states.
		Map<Quad<Integer>, Long> universes = new HashMap<>();
		long wins1 = 0;
		long wins2 = 0;

		// Initial state (p1, s1, p2, s2)
		universes.put(new Quad<>(start1, 0, start2, 0), 1L);

		while (!universes.isEmpty()) {
			Map<Quad<Integer>, Long> newUniverses = new HashMap<>();
			for (Quad<Integer> state : universes.keySet()) {
				long count = universes.get(state);
				int position1 = state.getX();
				int score1 = state.getY();
				int position2 = state.getZ();
				int score2 = state.getT();

				for (Integer roll1 : ALL_ROLLS.keySet()) {
					int newPosition1 = (position1 + roll1 - 1) % 10 + 1;
					int newScore1 = score1 + newPosition1;
					long increment = count * ALL_ROLLS.get(roll1);
					if (newScore1 >= 21) {
						wins1 += increment;
						continue;
					}
					for (Integer roll2 : ALL_ROLLS.keySet()) {
						int newPosition2 = (position2 + roll2 - 1) % 10 + 1;
						int newScore2 = score2 + newPosition2;
						increment = count * ALL_ROLLS.get(roll1) * ALL_ROLLS.get(roll2);
						if (newScore2 >= 21) {
							wins2 += increment;
							continue;
						}
						Quad<Integer> nextState = new Quad<>(newPosition1, newScore1, newPosition2, newScore2);
						long newCount = newUniverses.getOrDefault(nextState, 0L) + increment;
						newUniverses.put(nextState, newCount);
					}
				}
			}
			universes = newUniverses;
		}
		return (wins1 > wins2 ? wins1 : wins2);
	}
}