package buri.aoc.y15.d21;

/**
 * @author Brian Uri!
 */
public class Unit {
	private int _health;
	private int _damage;
	private int _armor;
	private int _gold;

	/**
	 * Constructor
	 */
	public Unit(int health, int damage, int armor, int gold) {
		_health = health;
		_damage = damage;
		_armor = armor;
		_gold = gold;
	}

	/**
	 * Reduces health by attack less the unit's armor. Returns true if unit dies.
	 */
	public boolean reduceHealth(int damage) {
		damage = Math.max(1, damage - getArmor());
		setHealth(getHealth() - damage);
		return (getHealth() <= 0);
	}

	/**
	 * Accessor for the health
	 */
	public int getHealth() {
		return _health;
	}

	/**
	 * Accessor for the health
	 */
	private void setHealth(int health) {
		_health = health;
	}

	/**
	 * Accessor for the damage
	 */
	public int getDamage() {
		return _damage;
	}

	/**
	 * Accessor for the armor
	 */
	public int getArmor() {
		return _armor;
	}

	/**
	 * Accessor for the gold
	 */
	public int getGold() {
		return _gold;
	}
}
