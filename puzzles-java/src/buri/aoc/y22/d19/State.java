package buri.aoc.y22.d19;

import buri.aoc.common.data.tuple.Pair;

import java.util.Objects;

/**
 * Data class for a single state.
 *
 * @author Brian Uri!
 */
public class State {

	private int _minute;
	private final Pair<Integer> _ore;
	private final Pair<Integer> _clay;
	private final Pair<Integer> _obsidian;
	private final Pair<Integer> _geodes;

	/**
	 * Constructor
	 */
	public State(int minute, Pair<Integer> ore, Pair<Integer> clay, Pair<Integer> obsidian, Pair<Integer> geodes) {
		_minute = minute;
		_ore = ore;
		_clay = clay;
		_obsidian = obsidian;
		_geodes = geodes;
	}

	/**
	 * Creates a deep copy.
	 */
	public State copy() {
		return (new State(getMinute(), getOre().copy(), getClay().copy(), getObsidian().copy(), getGeodes().copy()));
	}

	@Override
	public String toString() {
		return ("[" + (getMinute() - 1) + ": " + getOre() + " " + getClay() + " " + getObsidian() + " " + getGeodes() + "]");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		State state = (State) o;
		return (getMinute() == state.getMinute()
				&& Objects.equals(getOre(), state.getOre())
				&& Objects.equals(getClay(), state.getClay())
				&& Objects.equals(getObsidian(), state.getObsidian())
				&& Objects.equals(getGeodes(), state.getGeodes()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMinute(), getOre(), getClay(), getObsidian(), getGeodes());
	}

	/**
	 * Accessor for the current minute.
	 */
	public int getMinute() {
		return _minute;
	}

	/**
	 * Accessor for the current minute.
	 */
	public void addMinute() {
		_minute += 1;
	}

	/**
	 * Accessor for the ore robots.
	 */
	public int getOreRobots() {
		return getOre().getX();
	}

	/**
	 * Accessor for the ore robots.
	 */
	public void addOreRobot() {
		getOre().setX(getOreRobots() + 1);
	}

	/**
	 * Accessor for the ore amount.
	 */
	public int getOreCollected() {
		return getOre().getY();
	}

	/**
	 * Accessor for the ore amount.
	 */
	public void addOreCollected(int increment) {
		getOre().setY(getOreCollected() + increment);
	}

	/**
	 * Accessor for the clay robots.
	 */
	public int getClayRobots() {
		return getClay().getX();
	}

	/**
	 * Accessor for the clay robots.
	 */
	public void addClayRobot() {
		getClay().setX(getClayRobots() + 1);
	}

	/**
	 * Accessor for the clay amount.
	 */
	public int getClayCollected() {
		return getClay().getY();
	}

	/**
	 * Accessor for the clay amount.
	 */
	public void addClayCollected(int increment) {
		getClay().setY(getClayCollected() + increment);
	}

	/**
	 * Accessor for the obsidian robots.
	 */
	public int getObsidianRobots() {
		return getObsidian().getX();
	}

	/**
	 * Accessor for the obsidian robots.
	 */
	public void addObsidianRobot() {
		getObsidian().setX(getObsidianRobots() + 1);
	}

	/**
	 * Accessor for the obsidian amount.
	 */
	public int getObsidianCollected() {
		return getObsidian().getY();
	}

	/**
	 * Accessor for the obsidian amount.
	 */
	public void addObsidianCollected(int increment) {
		getObsidian().setY(getObsidianCollected() + increment);
	}

	/**
	 * Accessor for the geode robots.
	 */
	public int getGeodeRobots() {
		return getGeodes().getX();
	}

	/**
	 * Accessor for the geode robots.
	 */
	public void addGeodeRobot() {
		getGeodes().setX(getGeodeRobots() + 1);
	}

	/**
	 * Accessor for the geode amount.
	 */
	public int getGeodesCollected() {
		return getGeodes().getY();
	}

	/**
	 * Accessor for the geode amount.
	 */
	public void addGeodesCollected(int increment) {
		getGeodes().setY(getGeodesCollected() + increment);
	}

	/**
	 * Accessor for the ore robots and amount.
	 */
	private Pair<Integer> getOre() {
		return _ore;
	}

	/**
	 * Accessor for the clay robots and amount.
	 */
	private Pair<Integer> getClay() {
		return _clay;
	}

	/**
	 * Accessor for the obsidian robots and amount.
	 */
	private Pair<Integer> getObsidian() {
		return _obsidian;
	}

	/**
	 * Accessor for the geode robots and amount.
	 */
	private Pair<Integer> getGeodes() {
		return _geodes;
	}
}