package buri.aoc.y22.d05;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Day 05: Supply Stacks
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * After the rearrangement procedure completes, what crate ends up on top of each stack?
	 *
	 * Part 2:
	 * After the rearrangement procedure completes, what crate ends up on top of each stack?
	 */
	public static String getResult(Part part, List<String> input) {
		// Isolate stacks in input file.
		int stackNumberLine = 0;
		while (input.get(stackNumberLine + 1).length() > 0) {
			stackNumberLine++;
		}
		String[] stackNumbers = input.get(stackNumberLine).trim().split(" ");
		int numStacks = Integer.parseInt(stackNumbers[stackNumbers.length - 1]);

		// Stacks in the list are 0-based, while input file is 1-based.
		List<Stack<Character>> stacks = new ArrayList<>();
		for (int i = 0; i < numStacks; i++) {
			stacks.add(new Stack<>());
		}

		// Load stacks from the bottom up.
		for (int height = stackNumberLine - 1; height >= 0; height--) {
			for (int stack = 0; stack < numStacks; stack++) {
				int index = stack * 4 + 1;
				if (index < input.get(height).length()) {
					char crate = input.get(height).charAt(index);
					if (crate != ' ') {
						stacks.get(stack).push(crate);
					}
				}
			}
		}

		// Parse instructions.
		for (int i = stackNumberLine + 2; i < input.size(); i++) {
			String[] tokens = input.get(i).split(" ");
			int count = Integer.parseInt(tokens[1]);
			int from = Integer.parseInt(tokens[3]) - 1;
			int to = Integer.parseInt(tokens[5]) - 1;

			// In part two, we retain crate order with a temporary stack.
			Stack<Character> orderedGroup = new Stack<>();
			Stack<Character> target = (part == Part.ONE ? stacks.get(to) : orderedGroup);
			for (int moves = 0; moves < count; moves++) {
				char value = stacks.get(from).pop();
				target.push(value);
			}
			// Empty out the orderedGroup if it was used in part two.
			while (!orderedGroup.isEmpty()) {
				char value = orderedGroup.pop();
				stacks.get(to).push(value);
			}
		}
		StringBuilder builder = new StringBuilder();
		for (Stack stack : stacks) {
			builder.append(stack.peek());
		}
		return (builder.toString());
	}
}