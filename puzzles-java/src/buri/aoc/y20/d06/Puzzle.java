package buri.aoc.y20.d06;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 06: Custom Customs
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(11L, 1, false);
		assertRun(6335L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(6L, 1, false);
		assertRun(3392L, 0, true);
	}

	/**
	 * Part 1:
	 * For each group, count the number of questions to which anyone answered "yes". What is the sum of those counts?
	 *
	 * Part 2:
	 * For each group, count the number of questions to which everyone answered "yes". What is the sum of those counts?
	 */
	protected long runLong(Part part, List<String> input) {
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