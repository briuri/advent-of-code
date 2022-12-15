package buri.aoc.y22.d11;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Day 11: Monkey in the Middle
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the level of monkey business after 20 rounds of stuff-slinging simian shenanigans?
	 *
	 * Part 2:
	 * What is the level of monkey business after 10000 rounds?
	 */
	public static long getResult(Part part, List<String> input) {
		int i = 0;
		List<Monkey> monkeys = new ArrayList<>();
		while (i < input.size()) {
			monkeys.add(new Monkey(input.subList(i, i + 6)));
			i += 7;
		}

		// Used in Part 2 to tame big numbers.
		long gcd = 1L;
		for (Monkey monkey : monkeys) {
			gcd = gcd * monkey.getTestDivisor();
		}

		int numRounds = (part == Part.ONE ? 20 : 10000);
		for (int round = 1; round <= numRounds; round++) {
			for (Monkey monkey : monkeys) {
				for (long item : monkey.getItems()) {
					long worryLevel = monkey.inspect(part, item, gcd);
					int target = monkey.getTarget(worryLevel);
					monkeys.get(target).getItems().add(worryLevel);
				}
				monkey.getItems().clear();
			}
		}

		List<Long> inspectCounts = new ArrayList<>();
		for (Monkey monkey : monkeys) {
			inspectCounts.add(monkey.getInspectCount());
		}
		Collections.sort(inspectCounts);
		Collections.reverse(inspectCounts);
		return (inspectCounts.get(0) * inspectCounts.get(1));
	}
}