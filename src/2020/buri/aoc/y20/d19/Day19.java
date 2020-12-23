package buri.aoc.y20.d19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 19: Monster Messages
 *
 * @author Brian Uri!
 */
public class Day19 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * How many messages completely match rule 0?
	 *
	 * Part 2:
	 * After updating rules 8 and 11, how many messages completely match rule 0?
	 */
	public static long getResult(Part part, List<String> input) {
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
			StringBuffer buffer = new StringBuffer();

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
				buffer.append("(").append(getPattern(part, rules, cache, 42)).append("+)");
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
				buffer.append("(");
				final int DEPTH = 6;
				for (int i = 1; i < DEPTH; i++) {
					if (i > 1) {
						buffer.append('|');
					}
					buffer.append('(');
					for (int n = 0; n < i; n++) {
						buffer.append(getPattern(part, rules, cache, 42));
					}
					for (int n = 0; n < i; n++) {
						buffer.append(getPattern(part, rules, cache, 31));
					}
					buffer.append(')');
				}
				buffer.append(')');
			}

			// Simplest form rule "a" or "b"
			else if (rule.isSimple()) {
				buffer.append(rule.getRule());
			}
			// All other rules
			else {
				buffer.append("(");
				for (String token : rule.getRule().split(" ")) {
					if (token.equals("|")) {
						buffer.append(token);
					}
					else {
						buffer.append(getPattern(part, rules, cache, Integer.valueOf(token)));
					}
				}
				buffer.append(')');
			}

			// Save this in the cache for next time.
			cache.put(id, buffer.toString());
		}
		return (cache.get(id));
	}
}