package buri.aoc.y15.d21;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import buri.aoc.Part;
import buri.aoc.Puzzle;
import buri.aoc.data.Permutations;
import buri.aoc.data.Triple;

/**
 * Day 21: RPG Simulator 20XX
 * 
 * @author Brian Uri!
 */
public class Day21 extends Puzzle {

	// Triple x=gold, y=attack, z=armor
	private static final List<Triple> WEAPONS = new ArrayList<>();	// 1-1 weapons
	private static final List<Triple> ARMOR = new ArrayList<>();	// 0-1 armor
	private static final List<Triple> RINGS = new ArrayList<>();	// 0-2 rings
	static {
		WEAPONS.add(new Triple(8, 4, 0));
		WEAPONS.add(new Triple(10, 5, 0));
		WEAPONS.add(new Triple(25, 6, 0));
		WEAPONS.add(new Triple(40, 7, 0));
		WEAPONS.add(new Triple(74, 8, 0));

		ARMOR.add(new Triple(0, 0, 0));
		ARMOR.add(new Triple(13, 0, 1));
		ARMOR.add(new Triple(31, 0, 2));
		ARMOR.add(new Triple(53, 0, 3));
		ARMOR.add(new Triple(75, 0, 4));
		ARMOR.add(new Triple(102, 0, 5));

		RINGS.add(new Triple(0, 0, 0));
		RINGS.add(new Triple(0, 0, 0));
		RINGS.add(new Triple(25, 1, 0));
		RINGS.add(new Triple(50, 2, 0));
		RINGS.add(new Triple(100, 3, 0));
		RINGS.add(new Triple(20, 0, 1));
		RINGS.add(new Triple(40, 0, 2));
		RINGS.add(new Triple(80, 0, 3));
	}

	/**
	 * Part 1:
	 * What is the least amount of gold you can spend and still win the fight?
	 * 
	 * Part 2:
	 * What is the most amount of gold you can spend and still lose the fight?
	 */
	public static int getResult(Part part, Unit bossTemplate) {
		int minGold = Integer.MAX_VALUE;
		int maxGold = Integer.MIN_VALUE;
		Set<List<Integer>> ringPermutations = getRingPermutations();
		for (Triple weapon : WEAPONS) {
			for (Triple armor : ARMOR) {
				for (List<Integer> rings : ringPermutations) {
					Triple ring1 = RINGS.get(rings.get(0));
					Triple ring2 = RINGS.get(rings.get(1));
					int cost = (int) (weapon.getX() + armor.getX() + ring1.getX() + ring2.getX());
					int bonusAttack = (int) (weapon.getY() + armor.getY() + ring1.getY() + ring2.getY());
					int bonusArmor = (int) (weapon.getZ() + armor.getZ() + ring1.getZ() + ring2.getZ());

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
		boolean died = false;
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
	 * Returns all of the different ways to buy rings.
	 */
	private static Set<List<Integer>> getRingPermutations() {
		List<Integer> order = new ArrayList<>();
		for (int i = 0; i < RINGS.size(); i++) {
			order.add(i);
		}
		return (Permutations.getPairPermutations(order));
	}
}