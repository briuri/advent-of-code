package buri.aoc.y15.d21;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.Permutations;
import buri.aoc.common.data.tuple.Triple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Day 21: RPG Simulator 20XX
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(121L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(201L, 0, true);
	}

	// Triple x=gold, y=attack, z=armor
	private static final List<Triple<Integer>> WEAPONS = new ArrayList<>(); // 1-1 weapons
	private static final List<Triple<Integer>> ARMOR = new ArrayList<>(); // 0-1 armor
	private static final List<Triple<Integer>> RINGS = new ArrayList<>(); // 0-2 rings
	static {
		WEAPONS.add(new Triple<>(8, 4, 0));
		WEAPONS.add(new Triple<>(10, 5, 0));
		WEAPONS.add(new Triple<>(25, 6, 0));
		WEAPONS.add(new Triple<>(40, 7, 0));
		WEAPONS.add(new Triple<>(74, 8, 0));

		ARMOR.add(new Triple<>(0, 0, 0));
		ARMOR.add(new Triple<>(13, 0, 1));
		ARMOR.add(new Triple<>(31, 0, 2));
		ARMOR.add(new Triple<>(53, 0, 3));
		ARMOR.add(new Triple<>(75, 0, 4));
		ARMOR.add(new Triple<>(102, 0, 5));

		RINGS.add(new Triple<>(0, 0, 0));
		RINGS.add(new Triple<>(0, 0, 0));
		RINGS.add(new Triple<>(25, 1, 0));
		RINGS.add(new Triple<>(50, 2, 0));
		RINGS.add(new Triple<>(100, 3, 0));
		RINGS.add(new Triple<>(20, 0, 1));
		RINGS.add(new Triple<>(40, 0, 2));
		RINGS.add(new Triple<>(80, 0, 3));
	}

	/**
	 * Part 1:
	 * What is the least amount of gold you can spend and still win the fight?
	 *
	 * Part 2:
	 * What is the most amount of gold you can spend and still lose the fight?
	 */
	protected long runLong(Part part, List<String> input) {
		Unit bossTemplate = new Unit(
				Integer.parseInt(input.get(0).split(": ")[1]),
				Integer.parseInt(input.get(1).split(": ")[1]),
				Integer.parseInt(input.get(2).split(": ")[1]),
				0
		);
		int minGold = Integer.MAX_VALUE;
		int maxGold = Integer.MIN_VALUE;
		Set<List<Integer>> ringPermutations = getRingPermutations();
		for (Triple<Integer> weapon : WEAPONS) {
			for (Triple<Integer> armor : ARMOR) {
				for (List<Integer> rings : ringPermutations) {
					Triple<Integer> ring1 = RINGS.get(rings.get(0));
					Triple<Integer> ring2 = RINGS.get(rings.get(1));
					int cost = weapon.getX() + armor.getX() + ring1.getX() + ring2.getX();
					int bonusAttack = weapon.getY() + armor.getY() + ring1.getY() + ring2.getY();
					int bonusArmor = weapon.getZ() + armor.getZ() + ring1.getZ() + ring2.getZ();

					Unit player = new Unit(100, bonusAttack, bonusArmor, cost);
					Unit boss = new Unit(bossTemplate.getHealth(), bossTemplate.getDamage(), bossTemplate.getArmor(),
						bossTemplate.getGold());

					if (playerWins(player, boss)) {
						minGold = Math.min(minGold, cost);
					}
					else {
						maxGold = Math.max(maxGold, cost);
					}
				}
			}
		}
		return (part == Part.ONE ? minGold : maxGold);
	}

	/**
	 * Runs the combat rounds and returns true if the player will win.
	 */
	private static boolean playerWins(Unit player, Unit boss) {
		Unit attacker = player;
		Unit defender = boss;
		boolean died;
		while (true) {
			died = defender.reduceHealth(attacker.getDamage());
			if (died) {
				return (attacker == player);
			}
			attacker = defender;
			defender = (defender == player ? boss : player);
		}
	}

	/**
	 * Returns all the different ways to buy rings.
	 */
	private static Set<List<Integer>> getRingPermutations() {
		List<Integer> order = new ArrayList<>();
		for (int i = 0; i < RINGS.size(); i++) {
			order.add(i);
		}
		return (Permutations.getPairPermutations(order));
	}
}