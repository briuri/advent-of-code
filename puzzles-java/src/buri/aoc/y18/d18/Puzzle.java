package buri.aoc.y18.d18;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 18: Settlers of the North Pole
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(1147, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(481290, result);
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(180752, result);
	}

	/**
	 * Part 1:
	 * What will the total resource value of the lumber collection area be after 10 minutes?
	 *
	 * Part 2:
	 * What will the total resource value of the lumber collection area be after 1000000000 minutes?
	 */
	public static int getResult(Part part, List<String> input) {
		int minutes = (part == Part.ONE ? 10 : 1000000000);
		Acreage acreage = new Acreage(input);
		return (acreage.evolve(minutes));
	}
}