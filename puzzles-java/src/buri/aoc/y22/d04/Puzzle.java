package buri.aoc.y22.d04;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;

import java.util.List;

/**
 * Day 04: Camp Cleanup
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * In how many assignment pairs does one range fully contain the other?
	 * <p>
	 * Part 2:
	 * In how many assignment pairs do the ranges overlap?
	 */
	public static long getResult(Part part, List<String> input) {
		long sum = 0;
		for (String line : input) {
			String[] tokens = line.split(",");
			String[] aTokens = tokens[0].split("-");
			String[] bTokens = tokens[1].split("-");
			Pair<Integer> a = new Pair<>(Integer.parseInt(aTokens[0]), Integer.parseInt(aTokens[1]));
			Pair<Integer> b = new Pair<>(Integer.parseInt(bTokens[0]), Integer.parseInt(bTokens[1]));

			boolean partOneCondition = (part == Part.ONE && (pairContains(b, a) || pairContains(a, b)));
			boolean partTwoCondition = (part == Part.TWO && (pairContains(b, a.getX()) || pairContains(b, a.getY())
					|| pairContains(a, b.getX()) || pairContains(a, b.getY())));
			if (partOneCondition || partTwoCondition) {
				sum++;
			}
		}
		return (sum);
	}

	/**
	 * Returns true if one pair is completely contained by another pair.
	 */
	protected static boolean pairContains(Pair<Integer> outer, Pair<Integer> inner) {
		return (inner.getX() >= outer.getX() && inner.getY() <= outer.getY());
	}

	/**
	 * Returns true if some value is between the X and Y values of a pair.
	 */
	protected static boolean pairContains(Pair<Integer> pair, int value) {
		return (value >= pair.getX() && value <= pair.getY());
	}
}