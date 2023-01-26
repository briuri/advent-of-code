package buri.aoc.common.template;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 00: TITLE
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(1L, 1, false);
		assertRun(1L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(1L, 1, false);
		assertRun(1L, 0, true);
	}

	/**
	 * Runs a part of the puzzle.
	 */
	protected long runLong(Part part, List<String> input) {
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