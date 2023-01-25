package buri.aoc.y18.d15;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 15: Beverage Bandits
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(27730, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(36334, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(39514, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
		assertEquals(27755, Puzzle.getResult(Part.ONE, Puzzle.getInput(4)));
		assertEquals(28944, Puzzle.getResult(Part.ONE, Puzzle.getInput(5)));
		assertEquals(18740, Puzzle.getResult(Part.ONE, Puzzle.getInput(6)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(190777, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(4988, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
		assertEquals(31284, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
		assertEquals(3478, Puzzle.getResult(Part.TWO, Puzzle.getInput(4)));
		assertEquals(6474, Puzzle.getResult(Part.TWO, Puzzle.getInput(5)));
		assertEquals(1140, Puzzle.getResult(Part.TWO, Puzzle.getInput(6)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(47388, result);
	}

	/**
	 * Part 1:
	 * What is the outcome of the combat described in your puzzle input?
	 *
	 * Part 2:
	 * After increasing the Elves' attack power until it is just barely enough for them to win without any Elves dying,
	 * what is the outcome of the combat described in your puzzle input?
	 */
	public static int getResult(Part part, List<String> input) {
		if (part == Part.ONE) {
			Grid grid = new Grid(input, 3);
			return (grid.run());
		}

		// Part TWO
		int elfAttackPower = 4;
		while (true) {
			Grid grid = new Grid(input, elfAttackPower);
			int outcome = grid.run();
			if (!grid.getElfDied()) {
				return (outcome);
			}
			elfAttackPower++;
		}
	}
}