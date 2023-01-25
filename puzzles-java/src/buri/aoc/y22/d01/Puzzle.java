package buri.aoc.y22.d01;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Day 01: Calorie Counting
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many total Calories is that Elf carrying?
	 *
	 * Part 2:
	 * How many Calories are those Elves carrying in total?
	 */
	public static long getResult(Part part, List<String> input) {
		List<Integer> elves = new ArrayList<>();
		int elf = 0;
		for (String line : input) {
			// Reached the end of one elf's backpack.
			if (line.length() == 0) {
				elves.add(elf);
				elf = 0;
			}
			else {
				elf += Integer.parseInt(line);
			}
		}
		// Last backpack has no line break afterwards.
		if (elf != 0) {
			elves.add(elf);
		}

		Collections.sort(elves);
		Collections.reverse(elves);
		int sum = elves.get(0);
		if (part == Part.TWO) {
			sum += elves.get(1) + elves.get(2);
		}
		return (sum);
	}
}