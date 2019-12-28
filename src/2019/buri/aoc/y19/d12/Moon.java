package buri.aoc.y19.d12;

import buri.aoc.data.tuple.Triple;

/**
 * Data model for one of Jupiter's moons
 * 
 * @author Brian Uri!
 */
public class Moon {
	private Triple<Integer> _position;
	private Triple<Integer> _velocity;

	private static final Triple<Integer> ORIGIN = new Triple(0, 0, 0);

	/**
	 * Constructor
	 * 
	 * <x=-8, y=-10, z=0>
	 */
	public Moon(String input) {
		String[] tokens = input.split(", ");
		String x = tokens[0].split("=")[1];
		String y = tokens[1].split("=")[1];
		String z = tokens[2].split("=")[1].split(">")[0];
		_position = new Triple(Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
		_velocity = new Triple(0, 0, 0);
	}

	/**
	 * Adjusts velocity based on the gravity of another moon.
	 * 
	 * To apply gravity, consider every pair of moons. On each axis (x, y, and z), the velocity of each
	 * moon changes by exactly +1 or -1 to pull the moons together. For example, if Ganymede has an x
	 * position of 3, and Callisto has a x position of 5, then Ganymede's x velocity changes by +1
	 * (because 5 > 3) and Callisto's x velocity changes by -1 (because 3 < 5). However, if the
	 * positions on a given axis are the same, the velocity on that axis does not change for that pair
	 * of moons.
	 */
	public void applyGravity(Moon moon) {
		int dx = calculateGravity(getPosition().getX(), moon.getPosition().getX());
		int dy = calculateGravity(getPosition().getY(), moon.getPosition().getY());
		int dz = calculateGravity(getPosition().getZ(), moon.getPosition().getZ());
		getVelocity().setX(getVelocity().getX() + dx);
		getVelocity().setY(getVelocity().getY() + dy);
		getVelocity().setZ(getVelocity().getZ() + dz);
	}

	/**
	 * On each axis (x, y, and z), the velocity of each moon changes by exactly +1 or -1 to pull the moons together. For
	 * example, if Ganymede has an x position of 3, and Callisto has a x position of 5, then Ganymede's x velocity
	 * changes by +1 (because 5 > 3) and Callisto's x velocity changes by -1 (because 3 < 5). However, if the
	 * positions on a given axis are the same, the velocity on that axis does not change for that pair
	 * of moons.
	 */
	private int calculateGravity(Integer posA, Integer posB) {
		if (posA > posB) {
			return (-1);
		}
		else if (posA < posB) {
			return (1);
		}
		return (0);
	}

	/**
	 * Adjusts the position based on the current velocity.
	 */
	public void move() {
		getPosition().setX(getPosition().getX() + getVelocity().getX());
		getPosition().setY(getPosition().getY() + getVelocity().getY());
		getPosition().setZ(getPosition().getZ() + getVelocity().getZ());
	}

	/**
	 * Calculates the total energy of the moon.
	 */
	public long getEnergy() {
		long pe = getPosition().getManhattanDistance(ORIGIN);
		long ke = getVelocity().getManhattanDistance(ORIGIN);
		return (pe * ke);
	}

	/**
	 * Returns a snapshot of the x-values.
	 */
	public String getXState() {
		return (getPosition().getX() + "|" + getVelocity().getX());
	}

	/**
	 * Returns a snapshot of the y-values.
	 */
	public String getYState() {
		return (getPosition().getY() + "|" + getVelocity().getY());
	}

	/**
	 * Returns a snapshot of the z-values.
	 */
	public String getZState() {
		return (getPosition().getZ() + "|" + getVelocity().getZ());
	}

	@Override
	public String toString() {
		return (getPosition() + "|" + getVelocity());
	}

	/**
	 * Accessor for the position
	 */
	public Triple<Integer> getPosition() {
		return _position;
	}

	/**
	 * Accessor for the velocity
	 */
	public Triple<Integer> getVelocity() {
		return _velocity;
	}

}
