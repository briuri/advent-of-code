package buri.aoc.y18.d17;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 17: Reservoir Research
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(57, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(52800, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(29, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(45210, result);
	}

	/**
	 * Part 1:
	 * How many tiles can the water reach within the range of y values in your scan?
	 *
	 * Part 2:
	 * How many water tiles are left after the water spring stops producing water and all remaining water not at rest
	 * has drained?
	 */
	public static int getResult(Part part, List<String> input) {
		Reservoir reservoir = new Reservoir(input);
		return (reservoir.flow(part));
	}
}