package buri.aoc.y18.d14;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

/**
 * Day 14: Chocolate Charts
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * After all new recipes are added to the scoreboard, each Elf picks a new current recipe. To do this, the Elf steps
	 * forward through the scoreboard a number of recipes equal to 1 plus the score of their current recipe. So, after
	 * the first round, the first Elf moves forward 1 + 3 = 4 times, while the second Elf moves forward 1 + 7 = 8 times.
	 * If they run out of recipes, they loop back around to the beginning. After the first round, both Elves happen to
	 * loop around until they land on the same recipe that they had in the beginning; in general, they will move to
	 * different recipes.
	 *
	 * Part 1:
	 * What are the scores of the ten recipes immediately after the number of recipes in your puzzle input?
	 *
	 * Part 2:
	 * As it turns out, you got the Elves' plan backwards. They actually want to know how many recipes appear on the
	 * scoreboard to the left of the first recipes whose scores are the digits from your puzzle input. How many recipes
	 * appear on the scoreboard to the left of the score sequence in your puzzle input?
	 */
	public static String getResult(Part part, String input) {
		final int outputLength = 10;
		final int part1Iterations = Integer.valueOf(input);

		int first = 0;
		int second = 1;
		List<Integer> list = new ArrayList<Integer>();
		list.add(3);
		list.add(7);

		int part1Iteration = 0;
		while (true) {
			String sum = String.valueOf(list.get(first) + list.get(second));
			for (int sumChar = 0; sumChar < sum.length(); sumChar++) {
				list.add(Character.getNumericValue(sum.charAt(sumChar)));
				if (part == Part.TWO && list.size() > input.length()) {
					int existingRecipes = list.size() - input.length();
					String endString = toString(list.subList(existingRecipes, list.size()));
					if (endString.equals(input)) {
						return (String.valueOf(existingRecipes));
					}
				}
			}
			first = (first + 1 + list.get(first)) % list.size();
			second = (second + 1 + list.get(second)) % list.size();

			part1Iteration++;
			if (part == Part.ONE && (part1Iteration == part1Iterations + outputLength)) {
				return (toString(list.subList(part1Iterations, part1Iterations + outputLength)));
			}
		}
	}

	/**
	 * Converts a list of integers into a string of digits. Assumes single digit integers.
	 */
	private static String toString(List<Integer> list) {
		StringBuffer buffer = new StringBuffer();
		for (Integer value : list) {
			buffer.append(value);
		}
		return (buffer.toString());
	}
}