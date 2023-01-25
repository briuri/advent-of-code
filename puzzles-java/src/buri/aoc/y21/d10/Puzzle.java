package buri.aoc.y21.d10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 10: Syntax Scoring
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(26397L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(442131L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(288957L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3646451424L, result);
	}

	/**
	 * Part 1:
	 * What is the total syntax error score for those errors?
	 *
	 * Part 2:
	 * What is the middle score?
	 */
	public static long getResult(Part part, List<String> input) {
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