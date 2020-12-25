package buri.aoc.y20.d15;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.tuple.Pair;

/**
 * Day 15: Rambunctious Recitation
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file as a list of integers
	 */
	public static List<Integer> getInput(int fileIndex) {
		String[] stringInts = readFile(fileIndex).get(0).split(",");
		return (convertStringsToInts(Arrays.asList(stringInts)));
	}

	/**
	 * Part 1:
	 * Given your starting numbers, what will be the 2020th number spoken?
	 *
	 * Part 2:
	 * Given your starting numbers, what will be the 30,000,000th number spoken?
	 */
	public static int getResult(Part part, List<Integer> input) {
		int lastNum = 0;

		Map<Integer, Pair<Integer>> game = new HashMap<>();
		for (int i = 0; i < input.size(); i++) {
			lastNum = addRound(game, input.get(i), i);
		}

		int target = (part == Part.ONE ? 2020 : 30000000);
		for (int i = input.size(); i < target; i++) {
			Pair<Integer> history = game.get(lastNum);
			// If lastNum's occurrence was the first time it was spoken, nextNum is 0.
			// Otherwise, it is the age between the previous two occurrences.
			int nextNum = (history.getX() == history.getY() ? 0 : history.getY() - history.getX());
			lastNum = addRound(game, nextNum, i);
		}
		return (lastNum);
	}

	/**
	 * Adds a 0-based position to the game record (converting it to 1-based) and returns the last spoken number.
	 */
	protected static int addRound(Map<Integer, Pair<Integer>> game, int spoken, int position) {
		// Number has only been spoken once when both positions in the pair are identical.
		if (!game.containsKey(spoken)) {
			game.put(spoken, new Pair<>(position + 1, position + 1));
		}
		// Shift position to the left when a new position is added.
		else {
			Pair<Integer> history = game.get(spoken);
			history.setX(history.getY());
			history.setY(position + 1);
		}
		return (spoken);
	}
}