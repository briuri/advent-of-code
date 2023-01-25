package buri.aoc.y18.d24;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

/**
 * Day 24: Immune System Simulator 20XX
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many units would the winning army have?
	 *
	 * Part 2:
	 * How many units does the immune system have left after getting the smallest boost it needs to win?
	 */
	public static int getResult(Part part, List<String> input) {
		if (part == Part.ONE) {
			Battle battle = new Battle(0, input);
			battle.fight();
			return (battle.getWinnerSize());
		}
		// Part TWO
		int i = 1;
		while (true) {
			Battle battle = new Battle(i, input);
			Battle.Outcome outcome = battle.fight();
			if (outcome == Battle.Outcome.IMMUNE_WINS) {
				return (battle.getWinnerSize());
			}
			i++;
		}
	}
}