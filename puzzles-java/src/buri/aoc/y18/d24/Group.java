package buri.aoc.y18.d24;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Brian Uri!
 */
public class Group {
	private int _number;
	private boolean _isImmuneSystem;
	private int _size;
	private int _health;
	private int _attack;
	private AttackType _attackType;
	private int _initiative;
	private Set<AttackType> _weaknesses;
	private Set<AttackType> _immunities;
	private Group _target;
	private boolean _targeted;

	private static final String WEAK_TOKEN = "weak to ";
	private static final String IMMUNE_TOKEN = "immune to ";

	private enum AttackType {
		BLUDGEONING, COLD, FIRE, RADIATION, SLASHING
	}

	/**
	 * Take selection turns in decreasing order of effective power, then highest initiative.
	 */
	public static final Comparator<Group> SELECTION_ORDER = new Comparator<Group>() {
		@Override
		public int compare(Group o1, Group o2) {
			int compare = o2.getEffectivePower() - o1.getEffectivePower();
			if (compare == 0) {
				compare = o2.getInitiative() - o1.getInitiative();
			}
			return (compare);
		}
	};

	/**
	 * Break ties of potential damage done by effective power, then highest initiative.
	 */
	public static final Comparator<Group> DEFENDER_ORDER = new Comparator<Group>() {
		@Override
		public int compare(Group o1, Group o2) {
			int compare = o2.getEffectivePower() - o1.getEffectivePower();
			if (compare == 0) {
				compare = o2.getInitiative() - o1.getInitiative();
			}
			return (compare);
		}
	};

	/**
	 * Attack in decreasing order of initiative.
	 */
	public static final Comparator<Group> ATTACK_ORDER = new Comparator<Group>() {
		@Override
		public int compare(Group o1, Group o2) {
			return (o2.getInitiative() - o1.getInitiative());
		}
	};

	/**
	 * Constructor
	 */
	public Group(int num, boolean isImmuneSystem, int boost, String input) {
		_number = num;
		_isImmuneSystem = isImmuneSystem;
		String[] tokens = input.split(" ");
		String attack = input.split("an attack that does ")[1];
		_size = Integer.valueOf(tokens[0]);
		_health = Integer.valueOf(tokens[4]);
		_attack = Integer.valueOf(attack.split(" ")[0]) + boost;
		_attackType = getTypeFor(attack.split(" ")[1]);
		_initiative = Integer.valueOf(input.split(" at initiative ")[1]);
		_weaknesses = new HashSet<>();
		_immunities = new HashSet<>();
		if (input.indexOf(WEAK_TOKEN) != -1) {
			String[] weaknesses = input.substring(input.indexOf(WEAK_TOKEN) + WEAK_TOKEN.length()).split(
				"\\)")[0].split(";")[0].split(", ");
			for (String weakness : weaknesses) {
				_weaknesses.add(getTypeFor(weakness));
			}
		}
		if (input.indexOf(IMMUNE_TOKEN) != -1) {
			String[] immunities = input.substring(input.indexOf(IMMUNE_TOKEN) + IMMUNE_TOKEN.length()).split(
				"\\)")[0].split(";")[0].split(", ");
			for (String immunity : immunities) {
				_immunities.add(getTypeFor(immunity));
			}
		}
	}

	/**
	 * Converts a string attack type into an enumeration value.
	 */
	private static AttackType getTypeFor(String type) {
		if (type.equals("bludgeoning")) {
			return AttackType.BLUDGEONING;
		}
		if (type.equals("cold")) {
			return AttackType.COLD;
		}
		if (type.equals("fire")) {
			return AttackType.FIRE;
		}
		if (type.equals("radiation")) {
			return AttackType.RADIATION;
		}
		// default: slashing
		return AttackType.SLASHING;
	}

	/**
	 * If a group contains no units, it cannot attack. Each group deals damage to the target it selected, if any.
	 *
	 * Defending group only loses whole units. If a unit takes remainder damage, it remains at full health. (10x10
	 * attacked with 75 = 3x10 with 5 ignored)
	 *
	 * Returns number of units killed.
	 */
	public int attack() {
		if (getSize() > 0 && getTarget() != null) {
			int kills = Math.min(getDamageDealt(getTarget()) / getTarget().getHealth(), getTarget().getSize());
			getTarget().setSize(getTarget().getSize() - kills);

			// If defender dies but hasn't attacked yet, make sure to free up its target for the next round.
			if (getTarget().getSize() == 0) {
				getTarget().setTarget(null);
			}
			setTarget(null);
			return (kills);
		}
		return (0);
	}

	/**
	 * Calculates the damage from an attack based on weaknesses and immunities.
	 * - If defender is immune, 0x damage.
	 * - If defender is weak, 2x damage.
	 * - Otherwise, 1x damage.
	 */
	public int getDamageDealt(Group enemy) {
		int multiplier = 1;
		if (enemy.getImmunities().contains(getAttackType())) {
			multiplier = 0;
		}
		if (enemy.getWeaknesses().contains(getAttackType())) {
			multiplier = 2;
		}
		return (getEffectivePower() * multiplier);
	}

	@Override
	public String toString() {
		return (getName() + "[size=" + getSize() + " health=" + getHealth() + " " + getAttackType() + " power="
			+ getEffectivePower() + " initiative=" + getInitiative() + " weak=" + getWeaknesses() + " immune="
			+ getImmunities() + "]");
	}

	/**
	 * Returns a descriptive name
	 */
	public String getName() {
		return ((isImmuneSystem() ? "Immune" : "Infection") + getNumber());
	}

	/**
	 * Returns the effective power (size * attack)
	 */
	public int getEffectivePower() {
		return (getSize() * getAttack());
	}

	/**
	 * Accessor for whether this group is on the Immune System team.
	 */
	public boolean isImmuneSystem() {
		return _isImmuneSystem;
	}

	/**
	 * Accessor for the number of units living.
	 */
	public int getSize() {
		return _size;
	}

	/**
	 * Accessor for the number of units living.
	 */
	private void setSize(int size) {
		_size = size;
	}

	/**
	 * Accessor for the health of each unit
	 */
	private int getHealth() {
		return _health;
	}

	/**
	 * Accessor for the attack power of each unit
	 */
	private int getAttack() {
		return _attack;
	}

	/**
	 * Accessor for the attack type
	 */
	public AttackType getAttackType() {
		return _attackType;
	}

	/**
	 * Accessor for the initiative of this group
	 */
	private int getInitiative() {
		return _initiative;
	}

	/**
	 * Accessor for the weaknesses
	 */
	public Set<AttackType> getWeaknesses() {
		return _weaknesses;
	}

	/**
	 * Accessor for the immunities
	 */
	public Set<AttackType> getImmunities() {
		return _immunities;
	}

	/**
	 * Accessor for the currently selected target of attack
	 */
	public Group getTarget() {
		return _target;
	}

	/**
	 * Accessor for the currently selected target of attack
	 */
	public void setTarget(Group target) {
		if (_target != null) {
			_target.setTargeted(false);
		}
		if (target != null) {
			target.setTargeted(true);
		}
		_target = target;
	}

	/**
	 * Accessor for the number of this group for debugging.
	 */
	public int getNumber() {
		return _number;
	}

	/**
	 * Accessor for whether this group has been targeted in the current round.
	 */
	public boolean isTargeted() {
		return _targeted;
	}

	/**
	 * Accessor for whether this group has been targeted in the current round.
	 */
	private void setTargeted(boolean targeted) {
		_targeted = targeted;
	}
}