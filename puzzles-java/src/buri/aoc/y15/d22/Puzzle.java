package buri.aoc.y15.d22;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 22: Wizard Simulator 20XX
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(953L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(1289L, 0, true);
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
	protected long runLong(Part part, List<String> input) {
		int bossHealth = Integer.parseInt(input.get(0).split(": ")[1]);
		int bossDamage = Integer.parseInt(input.get(1).split(": ")[1]);
		int minManaCost = Integer.MAX_VALUE;
		for (int i = 0; i < 1000000; i++) {
			Battle battle = new Battle(part, bossHealth, bossDamage);
			int manaCost = battle.run(minManaCost);
			minManaCost = Math.min(minManaCost, manaCost);
		}
		return (minManaCost);
	}
}