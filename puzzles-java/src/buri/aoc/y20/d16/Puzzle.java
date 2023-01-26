package buri.aoc.y20.d16;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Day 16: Ticket Translation
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(71L, 1, false);
		assertRun(21978L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(1053686852011L, 0, true);
	}

	/**
	 * Part 1:
	 * Consider the validity of the nearby tickets you scanned. What is your ticket scanning error rate?
	 *
	 * Part 2:
	 * What do you get if you multiply those six values together?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Rule> rules = new ArrayList<>();
		Ticket mine = null;
		List<Ticket> nearby = new ArrayList<>();
		for (String line : input) {
			if (line.contains(": ")) {
				rules.add(new Rule(line));
			}
			// Modified input file slightly for easier parsing.
			else if (line.startsWith("your ticket# ")) {
				mine = new Ticket(line.split("# ")[1]);
			}
			else {
				nearby.add(new Ticket(line));
			}
		}
		if (part == Part.ONE) {
			int sum = 0;
			for (Ticket ticket : nearby) {
				sum += ticket.getInvalidSum(rules);
			}
			return (sum);
		}

		// Remove invalid tickets
		List<Ticket> tickets = new ArrayList<>();
		tickets.add(mine);
		for (Ticket ticket : nearby) {
			if (ticket.getInvalidSum(rules) == 0) {
				tickets.add(ticket);
			}
		}

		// Iteratively label fields
		Map<String, Integer> namesToIndexes = new HashMap<>();
		while (namesToIndexes.size() < mine.getSize()) {
			for (Rule rule : rules) {
				List<Integer> validFields = rule.getValidFields(tickets, new HashSet<Integer>(namesToIndexes.values()));
				if (validFields.size() == 1) {
					namesToIndexes.put(rule.getName(), validFields.get(0));
				}
			}
		}

		// With all fields labeled, multiply the six that start with departure from my ticket.
		long result = 1;
		for (String name : namesToIndexes.keySet()) {
			if (name.startsWith("departure")) {
				result *= mine.getField(namesToIndexes.get(name));
			}
		}
		return (result);
	}
}