package buri.aoc.y21.d21;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Quad;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Day 21: Dirac Dice
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(739785L, 1, false);
		assertRun(412344L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(444356092776315L, 1, false);
		assertRun(214924284932572L, 0, true);
	}

	/**
	 * Part 1:
	 * What do you get if you multiply the score of the losing player by the number of times the die was rolled during
	 * the game?
	 *
	 * Part 2:
	 * Find the player that wins in more universes; in how many universes does that player win?
	 */
	protected long runLong(Part part, List<String> input) {
		int start1 = Integer.parseInt(input.get(0).split(": ")[1]);
		int start2 = Integer.parseInt(input.get(1).split(": ")[1]);
		if (part == Part.ONE) {
			int score1 = 0;
			int score2 = 0;
			Die die = new Die();
			while (true) {
				start1 = (start1 + die.roll() + die.roll() + die.roll() - 1) % 10 + 1;
				score1 += start1;
				if (score1 >= 1000) {
					return ((long) score2 * die.getRollCount());
				}

				start2 = (start2 + die.roll() + die.roll() + die.roll() - 1) % 10 + 1;
				score2 += start2;
				if (score2 >= 1000) {
					return ((long) score2 * die.getRollCount());
				}
			}
		}

		// Part Two
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
		return (Math.max(wins1, wins2));
	}
}