package buri.aoc.y15.d14;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 14: Reindeer Olympics
 * 
 * @author Brian Uri!
 */
public class Day14 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * Given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, what distance has the
	 * winning reindeer traveled?
	 * 
	 * Part 2:
	 * QUESTION
	 */
	public static int getResult(Part part, List<String> input) {
		int duration = 2503;
		List<Deer> deer = new ArrayList<>();
		for (String line : input) {
			deer.add(new Deer(line));
		}

		if (part == Part.ONE) {
			return (getMaxDistance(deer, duration));
		}

		// Part TWO
		for (int i = 1; i <= duration; i++) {
			int maxDistance = getMaxDistance(deer, i);
			for (Deer doe : deer) {
				if (doe.getDistance(i) == maxDistance) {
					doe.addPoint();
				}
			}
		}

		int maxScore = Integer.MIN_VALUE;
		for (Deer doe : deer) {
			maxScore = Math.max(maxScore, doe.getPoints());
		}
		return (maxScore);
	}

	/**
	 * Returns the max distance of all deer at some time.
	 */
	private static int getMaxDistance(List<Deer> deer, int duration) {
		int maxDistance = Integer.MIN_VALUE;
		for (Deer doe : deer) {
			maxDistance = Math.max(maxDistance, doe.getDistance(duration));
		}
		return (maxDistance);
	}
}