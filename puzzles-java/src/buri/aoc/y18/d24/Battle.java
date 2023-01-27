package buri.aoc.y18.d24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Uri!
 */
public class Battle {
	private final List<Group> _immuneSystem;
	private final List<Group> _infection;

	public enum Outcome {
		STALEMATE, IMMUNE_WINS, IMMUNE_LOSES
	}

	/**
	 * Constructor
	 */
	public Battle(int boost, List<String> input) {
		_immuneSystem = new ArrayList<>();
		_infection = new ArrayList<>();

		boolean isImmune = true;
		int groupNumber = 1;
		for (String s : input) {
			if (s.startsWith("Immune") || s.startsWith("Infection")) {
				continue;
			}
			if (s.length() == 0) {
				isImmune = false;
				groupNumber = 1;
				continue;
			}
			Group group = new Group(groupNumber, isImmune, (isImmune ? boost : 0), s);
			groupNumber++;
			if (isImmune) {
				getImmuneSystem().add(group);
			}
			else {
				getInfection().add(group);
			}
		}
	}

	/**
	 * Each fight consists of two phases: target selection and attacking. After the fight is over, if both armies still
	 * contain units, a new fight begins; combat only ends once one army has lost all of its units.
	 */
	public Outcome fight() {
		while (!getImmuneSystem().isEmpty() && !getInfection().isEmpty()) {
			List<Group> allGroups = new ArrayList<>();
			allGroups.addAll(getImmuneSystem());
			allGroups.addAll(getInfection());

			// Target Selection
			allGroups.sort(Group.SELECTION_ORDER);
			for (Group group : allGroups) {
				group.setTarget(getBestTargetFor(group));
			}

			// Attack
			allGroups.sort(Group.ATTACK_ORDER);
			int totalKills = 0;
			for (Group group : allGroups) {
				totalKills += group.attack();
			}

			// Part 2: Stalemate occurs when no attacks kill anything.
			if (totalKills == 0) {
				return (Outcome.STALEMATE);
			}

			// Cleanup
			for (Group group : allGroups) {
				if (group.getSize() == 0) {
					if (group.isImmuneSystem()) {
						getImmuneSystem().remove(group);
					}
					else {
						getInfection().remove(group);
					}
				}
			}
		}
		return (getImmuneSystem().size() == 0 ? Outcome.IMMUNE_LOSES : Outcome.IMMUNE_WINS);
	}

	/**
	 * Target Selection:
	 * - Each group attempts to choose 1 target.
	 * - Target is enemy group which it would deal MOST damage to (after weaknesses / immunities). Group size ignored.
	 * - In ties with equal damage, pick group with the largest effective power, then highest initiative.
	 * - If it cannot deal any damage to any group, no target is chosen.
	 * - Defending groups can only be targeted by 1 attacker.
	 * At the end of the target selection phase, each group has selected zero or one groups to attack, and each group is
	 * being attacked by zero or one groups.
	 */
	private Group getBestTargetFor(Group group) {
		Map<Group, Integer> damageMap = new HashMap<>();
		int maxDamage = Integer.MIN_VALUE;

		// Calculate how much damage will be done to each potential defender.
		List<Group> enemies = new ArrayList<>(group.isImmuneSystem() ? getInfection() : getImmuneSystem());
		for (Group enemy : enemies) {
			if (!enemy.isTargeted()) {
				int damage = group.getDamageDealt(enemy);
				maxDamage = Math.max(maxDamage, damage);
				damageMap.put(enemy, damage);
			}
		}

		// Remove any defenders that are already targeted or aren't going to receive max damage.
		for (Iterator<Group> iterator = enemies.iterator(); iterator.hasNext();) {
			Group enemy = iterator.next();
			if (enemy.isTargeted() || damageMap.getOrDefault(enemy, Integer.MAX_VALUE) < maxDamage) {
				iterator.remove();
			}
		}

		// Sort remaining choices by descending effective power then initiative.
		enemies.sort(Group.DEFENDER_ORDER);

		// Don't select anyone if no damage can be done, or no enemies are available for selection.
		return ((maxDamage == 0 || enemies.isEmpty()) ? null : enemies.get(0));
	}

	/**
	 * Returns the total number of units left in the winner's team.
	 */
	public int getWinnerSize() {
		int units = 0;
		List<Group> winners = (getImmuneSystem().size() == 0 ? getInfection() : getImmuneSystem());
		for (Group group : winners) {
			units += group.getSize();
		}
		return (units);
	}

	/**
	 * Accessor for the immuneSystem
	 */
	private List<Group> getImmuneSystem() {
		return _immuneSystem;
	}

	/**
	 * Accessor for the infection
	 */
	private List<Group> getInfection() {
		return _infection;
	}
}