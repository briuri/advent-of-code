package buri.aoc.y18.d23;

import buri.aoc.data.tuple.Triple;

/**
 * Data class for a nanobot (position and radius)
 * 
 * @author Brian Uri!
 */
public class Bot {
	private Triple _position;
	private long _radius;

	/**
	 * Constructor
	 */
	public Bot(String input) {
		String[] tokens = input.split(",");
		_position = new Triple(Long.valueOf(tokens[0]), Long.valueOf(tokens[1]), Long.valueOf(tokens[2]));
		_radius = Long.valueOf(tokens[3]);
	}

	@Override
	public String toString() {
		return "[" + getPosition().toString() + " r=" + getRadius() + "]";
	}

	/**
	 * Returns true if some position is in range of this bot's signal.
	 */
	public boolean inRange(Triple position) {
		return (getPosition().getManhattanDistance(position) <= getRadius());
	}

	/**
	 * Accessor for the position
	 */
	public Triple getPosition() {
		return _position;
	}

	/**
	 * Accessor for the radius
	 */
	public long getRadius() {
		return _radius;
	}
}
