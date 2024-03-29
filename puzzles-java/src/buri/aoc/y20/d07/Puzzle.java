package buri.aoc.y20.d07;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 07: Handy Haversacks
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(4L, 1, false);
		assertRun(289L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(32L, 1, false);
		assertRun(126L, 2, false);
		assertRun(30055L, 0, true);
	}

	/**
	 * Part 1:
	 * How many bag colors can eventually contain at least one shiny gold bag?
	 *
	 * Part 2:
	 * How many individual bags are required inside your single shiny gold bag?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Rule> rules = new ArrayList<>();
		for (String line : input) {
			rules.add(new Rule(line));
		}

		if (part == Part.ONE) {
			return (getContainers(rules, "shiny gold").size());
		}
		// Subtract 1 for the shiny gold bag itself.
		return (getAllBags(rules, "shiny gold") - 1);
	}

	/**
	 * Finds the rule for the specific bagType
	 */
	protected static Rule getRule(List<Rule> rules, String bagType) {
		for (Rule rule : rules) {
			if (rule.getBagType().equals(bagType)) {
				return (rule);
			}
		}
		throw new RuntimeException("Could not find rule for " + bagType);
	}

	/**
	 * Recursive method to get all rules that contain a specific type of bag.
	 */
	protected static Set<String> getContainers(List<Rule> rules, String bagType) {
		Set<String> containers = new HashSet<>();
		for (Rule rule : rules) {
			if (rule.canContain(bagType)) {
				containers.add(rule.getBagType());
				containers.addAll(getContainers(rules, rule.getBagType()));
			}
		}
		return (containers);
	}

	/**
	 * Recursive method to get the number of bags inside of a bag (including itself).
	 */
	protected static int getAllBags(List<Rule> rules, String bagType) {
		int count = 1;
		Rule rule = getRule(rules, bagType);
		for (String innerBagType : rule.getBagsInside().keySet()) {
			count += rule.getBagsInside().get(innerBagType) * getAllBags(rules, innerBagType);
		}
		return (count);
	}
}