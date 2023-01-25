package buri.aoc.common.template;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 00: TITLE
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(0L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(0L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(0L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(0L, result);
	}

	/**
	 * Part 1:
	 * QUESTION
	 *
	 * Part 2:
	 * QUESTION
	 */
	public static long getResult(Part part, List<String> input) {
// all integers on first line
//		input = Arrays.asList(input.get(0).split(" "));
// 1 integer per line
//		List<Integer> values = convertStringsToInts(input);
// 1 data object per line
//		 List<Data> list = new ArrayList<>();
//		 for (String line : input) {
//			 list.add(new Data(line));
//		 }
// Grid
//		Grid grid = new Grid(input);


		long sum = 0;
		for (String line : input) {

		}
		if (part == Part.ONE) {
			return (sum);
		}
		return (sum);
	}

	/**
	 *
	 */
	protected static String doSomething() {
		return ("");
	}
}