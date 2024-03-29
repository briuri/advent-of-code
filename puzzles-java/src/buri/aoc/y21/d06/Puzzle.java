package buri.aoc.y21.d06;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 06: Lanternfish
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(5934L, 1, false);
		assertRun(376194L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(26984457539L, 1, false);
		assertRun(1693022481538L, 0, true);
	}

	/**
	 * Part 1:
	 * How many lanternfish would there be after 80 days?
	 *
	 * Part 2:
	 * How many lanternfish would there be after 256 days?
	 */
	protected long runLong(Part part, List<String> input) {
		String[] stringInts = input.get(0).split(",");
		List<Integer> values = PuzzleMath.toInts(Arrays.asList(stringInts));

		int days = (part == Part.ONE ? 80 : 256);
		Map<Integer, Long> counts = new HashMap<>();
		for (int fish : values) {
			counts.put(fish, counts.getOrDefault(fish, 0L) + 1);
		}

		for (int day = 0; day < days; day++) {
			// Decrease each fish timer by 1.
			for (int timer = 0; timer <= 8; timer++) {
				counts.put(timer - 1, counts.getOrDefault(timer, 0L));
			}

			// Reallocated expired fish timers.
			long expired = counts.getOrDefault(-1, 0L);
			counts.put(6, counts.getOrDefault(6, 0L) + expired);
			counts.put(8, expired);
			counts.remove(-1);
		}

		long sum = 0;
		for (int fish = 0; fish <= 8; fish++) {
			sum += counts.getOrDefault(fish, 0L);
		}
		return (sum);
	}
}