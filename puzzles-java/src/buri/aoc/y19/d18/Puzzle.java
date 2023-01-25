package buri.aoc.y19.d18;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 18: Many-Worlds Interpretation
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(8, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(86, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(132, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
		assertEquals(136, Puzzle.getResult(Part.ONE, Puzzle.getInput(4)));
		assertEquals(81, Puzzle.getResult(Part.ONE, Puzzle.getInput(5)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(5392, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(8, Puzzle.getResult(Part.TWO, Puzzle.getInput(7)));
		assertEquals(24, Puzzle.getResult(Part.TWO, Puzzle.getInput(8)));
		assertEquals(32, Puzzle.getResult(Part.TWO, Puzzle.getInput(9)));
		// assertEquals(72, Day18.getResult(Part.TWO, Day18.getInput(10)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(6));
		toConsole(result);
		assertEquals(1684, result);
	}

	/**
	 * Part 1:
	 * How many steps is the shortest path that collects all of the keys?
	 *
	 * Part 2:
	 * After updating your map and using the remote-controlled robots, what is the fewest steps necessary to collect all
	 * of the keys?
	 */
	public static int getResult(Part part, List<String> input) {
		Vault vault = new Vault(input);
		return (vault.getFewestSteps());
	}
}