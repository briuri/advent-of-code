package buri.aoc.y15.d22;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 22: Wizard Simulator 20XX
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE);
		toConsole(result);
		assertEquals(953, result);
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO);
		toConsole(result);
		assertEquals(1289, result);
	}

	/**
	 * Part 1:
	 * What is the least amount of mana you can spend and still win the fight? (Do not include mana recharge effects as
	 * "spending" negative mana.)
	 *
	 * Part 2:
	 * At the start of each player turn (before any other effects apply), you lose 1 hit point. If this brings you to or
	 * below 0 hit points, you lose.
	 */
	public static int getResult(Part part) {
		int minManaCost = Integer.MAX_VALUE;
		for (int i = 0; i < 1000000; i++) {
			Battle battle = new Battle(part);
			int manaCost = battle.run(minManaCost);
			minManaCost = Math.min(minManaCost, manaCost);
		}
		return (minManaCost);
	}
}