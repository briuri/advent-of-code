package buri.aoc.y18.d24;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 24: Immune System Simulator 20XX
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(5216, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(26343, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(51, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(5549, result);
	}

	/**
	 * Part 1:
	 * How many units would the winning army have?
	 *
	 * Part 2:
	 * How many units does the immune system have left after getting the smallest boost it needs to win?
	 */
	public static int getResult(Part part, List<String> input) {
		if (part == Part.ONE) {
			Battle battle = new Battle(0, input);
			battle.fight();
			return (battle.getWinnerSize());
		}
		// Part TWO
		int i = 1;
		while (true) {
			Battle battle = new Battle(i, input);
			Battle.Outcome outcome = battle.fight();
			if (outcome == Battle.Outcome.IMMUNE_WINS) {
				return (battle.getWinnerSize());
			}
			i++;
		}
	}
}