package buri.aoc.y20.d19;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 19: Monster Messages
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(2L, 1, false);
		assertRun(3L, 2, false);
		assertRun(216L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(12L, 2, false);
		assertRun(400L, 0, true);
	}

	/**
	 * Part 1:
	 * How many messages completely match rule 0?
	 *
	 * Part 2:
	 * After updating rules 8 and 11, how many messages completely match rule 0?
	 */
	protected long runLong(Part part, List<String> input) {
		// Load data
		Map<Integer, Rule> rules = new HashMap<>();
		List<String> messages = new ArrayList<>();
		boolean inRules = true;
		for (String line : input) {
			if (line.length() == 0) {
				inRules = false;
				continue;
			}
			if (inRules) {
				Rule rule = new Rule(line);
				rules.put(rule.getId(), rule);
			}
			else {
				messages.add(line);
			}
		}

		// Count number of messages that match rule 0.
		int count = 0;
		for (String message : messages) {
			if (message.matches(getPattern(part, rules, new HashMap<>(), 0))) {
				count++;
			}
		}
		return (count);
	}

	/**
	 * Builds a regex pattern for one of the rules, recursively gathering nested rules.
	 *
	 * In part two, rules 8 and 11 are overridden with recursive nightmares.
	 */
	protected static String getPattern(Part part, Map<Integer, Rule> rules, Map<Integer, String> cache, int id) {
		if (!cache.containsKey(id)) {
			Rule rule = rules.get(id);
			StringBuilder builder = new StringBuilder();

			/*
			 * In part two, intercept the written rule 8 with new logic.
			 *
			 * 8: 42 | 42 8
			 * becomes
			 * 42 | 42 42 | 42*n
			 *
			 * In other words, "at least 1 instance of 42"
			 */
			if (part == Part.TWO && rule.getId() == 8) {
				builder.append("(").append(getPattern(part, rules, cache, 42)).append("+)");
			}

			/*
			 * In part two, intercept the written rule 11 with new logic.
			 *
			 * 11: 42 31 | 42 11 31
			 * becomes
			 * 42 31 | 42 42 31 31 | 42*n 31*n
			 *
			 * Since this only needs to match the longest string (96 chars), I just gradually increased DEPTH until the
			 * count of valid messages stabilized.
			 */
			else if (part == Part.TWO && rule.getId() == 11) {
				builder.append("(");
				final int DEPTH = 6;
				for (int i = 1; i < DEPTH; i++) {
					if (i > 1) {
						builder.append('|');
					}
					builder.append('(');
					for (int n = 0; n < i; n++) {
						builder.append(getPattern(part, rules, cache, 42));
					}
					for (int n = 0; n < i; n++) {
						builder.append(getPattern(part, rules, cache, 31));
					}
					builder.append(')');
				}
				builder.append(')');
			}

			// Simplest form rule "a" or "b"
			else if (rule.isSimple()) {
				builder.append(rule.getRule());
			}
			// All other rules
			else {
				builder.append("(");
				for (String token : rule.getRule().split(" ")) {
					if (token.equals("|")) {
						builder.append(token);
					}
					else {
						builder.append(getPattern(part, rules, cache, Integer.parseInt(token)));
					}
				}
				builder.append(')');
			}

			// Save this in the cache for next time.
			cache.put(id, builder.toString());
		}
		return (cache.get(id));
	}
}