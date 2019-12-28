package buri.aoc.y18.d15;

import buri.aoc.data.tuple.Pair;

/**
 * Data model for a unit (elf or goblin).
 * 
 * @author Brian Uri!
 */
public class Unit implements Comparable<Unit> {
	private char _type;
	private Pair<Integer> _position;
	private int _health;
	private int _attackPower;

	public static final char ELF = 'E';
	public static final char GOBLIN = 'G';

	/**
	 * Constructor
	 */
	public Unit(char type, Pair<Integer> position) {
		_type = type;
		_position = position;
		_health = 200;
		_attackPower = 3;
	}

	/**
	 * Checks if some unit is adjacent to this one.
	 */
	public boolean isAdjacent(Unit unit) {
		int x = getPosition().getX();
		int y = getPosition().getY();
		int x2 = unit.getPosition().getX();
		int y2 = unit.getPosition().getY();
		boolean isAbove = ((x == x2) && (y + 1 == y2));
		boolean isLeft = ((x + 1 == x2) && (y == y2));
		boolean isRight = ((x - 1 == x2) && (y == y2));
		boolean isBelow = ((x == x2) && (y - 1 == y2));
		return (!isDead() && (isAbove || isLeft || isRight || isBelow));
	}

	/**
	 * Compares based on position in reading order.
	 */
	@Override
	public int compareTo(Unit o) {
		return (getPosition().compareTo(o.getPosition()));
	}

	/**
	 * Convenience method for checking if this is an elf.
	 */
	public boolean isElf() {
		return (getType() == ELF);
	}

	/**
	 * Convenience method for checking if this unit is dead.
	 */
	public boolean isDead() {
		return (getHealth() <= 0);
	}

	/**
	 * Accessor for the unit's type
	 */
	public char getType() {
		return _type;
	}

	/**
	 * Accessor for the position
	 */
	public Pair<Integer> getPosition() {
		return _position;
	}

	/**
	 * Accessor for the position
	 */
	public void setPosition(Pair position) {
		_position = position;
	}

	/**
	 * Accessor for the unit's health
	 */
	public int getHealth() {
		return _health;
	}

	/**
	 * Accessor for the unit's health
	 */
	public void setHealth(int health) {
		_health = health;
	}

	/**
	 * Accessor for the unit's attack power
	 */
	public int getAttackPower() {
		return _attackPower;
	}

	/**
	 * Accessor for the unit's attack power
	 */
	public void setAttackPower(int attackPower) {
		_attackPower = attackPower;
	}
}