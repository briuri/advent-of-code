package buri.aoc.y21.d10;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Day 10: Syntax Scoring
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(26397L, 1, false);
		assertRun(442131L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(288957L, 1, false);
		assertRun(3646451424L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the total syntax error score for those errors?
	 *
	 * Part 2:
	 * What is the middle score?
	 */
	protected long runLong(Part part, List<String> input) {
		long corruptScore = 0;
		List<Long> incompleteScores = new ArrayList<>();
		for (String line : input) {
			Stack<Character> stack = new Stack<>();
			long incompleteScore = 0;
			boolean isCorrupt = false;
			for (Character value : line.toCharArray()) {
				if (Group.ALL_OPEN.contains(value)) {
					stack.push(value);
				}
				else {
					Group group = Group.getGroupForClose(value);
					if (group.getOpen() == stack.peek()) {
						stack.pop();
					}
					// Score corrupt lines.
					else {
						corruptScore += group.getCorruptScore();
						isCorrupt = true;
						break;
					}
				}
			}

			// Score incomplete lines.
			if (!isCorrupt) {
				while (!stack.isEmpty()) {
					Character value = stack.pop();
					incompleteScore = incompleteScore * 5 + Group.getGroupForOpen(value).getIncompleteScore();
				}
				incompleteScores.add(incompleteScore);
			}
		}
		if (part == Part.ONE) {
			return (corruptScore);
		}
		Collections.sort(incompleteScores);
		return (incompleteScores.get(incompleteScores.size() / 2));
	}
}