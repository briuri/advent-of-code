package buri.aoc.y20.d02;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 02: Password Philosophy
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(2L, 1, false);
		assertRun(628L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(1L, 1, false);
		assertRun(705L, 0, true);
	}

	/**
	 * Part 1:
	 * How many passwords are valid according to their policies?
	 *
	 * Part 2:
	 * How many passwords are valid according to the new interpretation of the policies?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Policy> policies = new ArrayList<>();
		for (String line : input) {
			policies.add(new Policy(line));
		}

		int valid = 0;
		for (Policy policy : policies) {
			if (policy.isValid(part)) {
				valid++;
			}
		}
		return (valid);
	}
}