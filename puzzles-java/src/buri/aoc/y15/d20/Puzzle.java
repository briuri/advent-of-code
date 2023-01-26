package buri.aoc.y15.d20;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 20: Infinite Elves and Infinite Houses
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(776160L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(786240L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the lowest house number of the house to get at least as many presents as the number in your puzzle input?
	 *
	 * Part 2:
	 * Instead, each Elf will stop after delivering presents to 50 houses. To make up for it, they decide to deliver
	 * presents equal to eleven times their number at each house.
	 */
	protected long runLong(Part part, List<String> input) {
		int presents = Integer.parseInt(input.get(0));
		Map<Integer, Integer> houses = new HashMap<>();
		int numPresents = (part == Part.ONE ? 10 : 11);

		// Part 1 Worst Case: Elf 3,310,000 visits House 3,310,000 and leaves 33,100,000 presents.
		// Part 2 Worst Case: Elf 3,009,091 visits House 3,009,091 and leaves 33,100,001 presents.
		int maxElf = presents / 10;

		// After I got the answers, I raised the house lower bound to speed up run time.
		int minHouse = part == Part.ONE ? 776000 : 786000;

		int house = minHouse;
		while (true) {
			for (int elf = 1; elf < maxElf; elf++) {
				boolean allowVisit = (part == Part.ONE || (part == Part.TWO && house <= elf * 50));
				if (house >= elf && house % elf == 0 && allowVisit) {
					Integer totalPresents = houses.getOrDefault(house, 0) + (elf * numPresents);
					houses.put(house, totalPresents);
					if (totalPresents >= presents) {
						return (house);
					}
				}
			}
			house++;
		}
	}
}