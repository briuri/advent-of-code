package buri.aoc.y20.d06;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 06: Custom Customs
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(11, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(6335, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(6, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3392, result);
	}

	/**
	 * Part 1:
	 * For each group, count the number of questions to which anyone answered "yes". What is the sum of those counts?
	 *
	 * Part 2:
	 * For each group, count the number of questions to which everyone answered "yes". What is the sum of those counts?
	 */
	public static int getResult(Part part, List<String> input) {
		List<AnswerGroup> list = new ArrayList<>();
		List<String> chunk = new ArrayList<>();
		for (String line : input) {
			if (line.length() > 0) {
				chunk.add(line);
			}
			else {
				list.add(new AnswerGroup(chunk));
				chunk.clear();
			}
		}

		int sum = 0;
		for (AnswerGroup group : list) {
			sum += group.getYesCount(part);
		}
		return (sum);
	}
}