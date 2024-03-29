package buri.aoc.y16.d20;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 20: Firewall Rules
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(14975795L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(101L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the lowest-valued IP that is not blocked?
	 *
	 * Part 2:
	 * How many IPs are allowed by the blacklist?
	 */
	protected long runLong(Part part, List<String> input) {
		long max = 4294967295L;
		List<Range> ranges = new ArrayList<>();
		for (String line : input) {
			ranges.add(new Range(line));
		}

		// Merge ranges until no more ranges can be merged.
		int rangesMerged = -1;
		while (rangesMerged != 0) {
			rangesMerged = mergeRanges(ranges);
		}
		Collections.sort(ranges);

		if (part == Part.ONE) {
			// Return first value outside the lowest range
			return (ranges.get(0).getMax() + 1);
		}

		// Part TWO
		// Add a final dummy range representing the upper bounds.
		ranges.add(new Range((max + 1) + "-" + (max + 2)));
		long count = 0;
		for (int i = 0; i < ranges.size() - 1; i++) {
			long leftMax = ranges.get(i).getMax();
			long rightMin = ranges.get(i + 1).getMin();
			count += (rightMin - leftMax - 1);
		}
		return (count);
	}

	/**
	 * Merges any overlapping ranges. Returns the number of ranges merged.
	 */
	private static int mergeRanges(List<Range> ranges) {
		int rangesMerged = 0;
		Set<Range> obeRanges = new HashSet<>();
		for (Range range : ranges) {
			if (obeRanges.contains(range)) {
				continue;
			}
			for (Range range2 : ranges) {
				if (range == range2 || obeRanges.contains(range2)) {
					continue;
				}
				boolean merged = range.merge(range2);
				if (merged) {
					obeRanges.add(range2);
					rangesMerged++;
				}
			}
		}
		ranges.removeAll(obeRanges);
		return (rangesMerged);
	}
}