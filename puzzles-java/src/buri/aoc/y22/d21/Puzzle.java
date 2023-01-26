package buri.aoc.y22.d21;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Day 21: Monkey Math
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(152L, 1, false);
		assertRun(299983725663456L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(301L, 1, false);
		assertRun(3093175982595L, 0, true);
	}

	/**
	 * My original solution for this was to build up equations using Pattern and Matcher to replace names
	 * with values. I then took the resultant equations and pasted them into a Python script to take advantage
	 * of eval() for Part 1 and sympy for Part 2.
	 *
	 * I rewrote the solution after solving so it's all in boring Java.
	 *
	 * Part 1:
	 * What number will the monkey named root yell?
	 *
	 * Part 2:
	 * What number do you yell to pass root's equality test?
	 */
	protected long runLong(Part part, List<String> input) {
		Map<String, Monkey> monkeys = new HashMap<>();
		for (String line : input) {
			Monkey monkey = new Monkey(part, line);
			if (!(part == Part.TWO && monkey.getName().equals("humn"))) {
				monkeys.put(monkey.getName(), monkey);
			}
		}
		Monkey root = monkeys.get("root");

		// Start with the simple values and insert them in other monkeys' operations.
		// Continue until everything is simplified.
		List<Monkey> possibleSimpleMonkeys = new ArrayList<>(monkeys.values());
		while (true) {
			int originalSize = possibleSimpleMonkeys.size();
			for (Iterator<Monkey> iter = possibleSimpleMonkeys.iterator(); iter.hasNext(); ) {
				Monkey possibleMonkey = iter.next();
				if (possibleMonkey.isSimpleOperation()) {
					for (Monkey changeMonkey : monkeys.values()) {
						changeMonkey.reduceOperation(possibleMonkey);
					}
					iter.remove();
				}
			}
			// Stop when no more operations can be reduced.
			if (originalSize == possibleSimpleMonkeys.size()) {
				break;
			}
		}

		if (part == Part.ONE) {
			return (Long.parseLong(root.getOperation()));
		}

		// In part 2, work backwards through the remaining operations.
		// One side of the = equation will have a simple value.
		String[] tokens = root.getOperation().split(" = ");
		String name;
		long targetValue;
		try {
			targetValue = Long.parseLong(tokens[0]);
			name = tokens[1];
		} catch (NumberFormatException e) {
			name = tokens[0];
			targetValue = Long.parseLong(tokens[1]);
		}

		while (!name.equals("humn")) {
			tokens = monkeys.get(name).getOperation().split(" ");
			long simpleValue;
			// Reverse the operation so the simple value moves to the other side of the = sign.
			try {
				simpleValue = Long.parseLong(tokens[0]);
				name = tokens[2];
				// These operations are order-dependent.
				if (tokens[1].equals("-")) {
					targetValue = simpleValue - targetValue;
				}
				else if (tokens[1].equals("/")) {
					targetValue = simpleValue / targetValue;
				}
			} catch (NumberFormatException e) {
				name = tokens[0];
				simpleValue = Long.parseLong(tokens[2]);
				// These operations are order-dependent.
				if (tokens[1].equals("-")) {
					targetValue += simpleValue;
				}
				else if (tokens[1].equals("/")) {
					targetValue *= simpleValue;
				}
			}
			// These operations are the same regardless of order.
			if (tokens[1].equals("+")) {
				targetValue -= simpleValue;
			}
			else if (tokens[1].equals("*")) {
				targetValue /= simpleValue;
			}
		}
		return (targetValue);
	}
}