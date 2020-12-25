package buri.aoc.y20.d06;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 06: Custom Customs
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file as a list of answer groups.
	 */
	public static List<AnswerGroup> getInput(int fileIndex) {
		List<AnswerGroup> list = new ArrayList<>();
		List<String> chunk = new ArrayList<>();
		for (String line : readFile(fileIndex)) {
			if (line.length() > 0) {
				chunk.add(line);
			}
			else {
				list.add(new AnswerGroup(chunk));
				chunk.clear();
			}
		}
		return (list);
	}

	/**
	 * Part 1:
	 * For each group, count the number of questions to which anyone answered "yes". What is the sum of those counts?
	 *
	 * Part 2:
	 * For each group, count the number of questions to which everyone answered "yes". What is the sum of those counts?
	 */
	public static int getResult(Part part, List<AnswerGroup> input) {
		int sum = 0;
		for (AnswerGroup group : input) {
			sum += group.getYesCount(part);
		}
		return (sum);
	}
}