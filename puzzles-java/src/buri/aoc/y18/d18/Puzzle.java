package buri.aoc.y18.d18;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 18: Settlers of the North Pole
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(1147L, 1, false);
		assertRun(481290L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(180752L, 0, true);
	}

	/**
	 * Part 1:
	 * What will the total resource value of the lumber collection area be after 10 minutes?
	 *
	 * Part 2:
	 * What will the total resource value of the lumber collection area be after 1000000000 minutes?
	 */
	protected long runLong(Part part, List<String> input) {
		int minutes = (part == Part.ONE ? 10 : 1000000000);
		Acreage acreage = new Acreage(input);
		return (acreage.evolve(minutes));
	}
}