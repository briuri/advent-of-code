package buri.aoc.y17.d20;

import buri.aoc.data.tuple.Triple;

/**
 * Data model for a particle with position, velocity, and acceleration.
 */
public class Particle {

	private Triple<Long> _position;
	private Triple<Long> _velocity;
	private Triple<Long> _acceleration;

	/**
	 * Constructor
	 */
	public Particle(String input) {
		String[] tokens = input.split(",");
		_position = new Triple(Long.valueOf(tokens[0]), Long.valueOf(tokens[1]), Long.valueOf(tokens[2]));
		_velocity = new Triple(Long.valueOf(tokens[3]), Long.valueOf(tokens[4]), Long.valueOf(tokens[5]));
		_acceleration = new Triple(Long.valueOf(tokens[6]), Long.valueOf(tokens[7]), Long.valueOf(tokens[8]));
	}

	/**
	 * Increase the velocity by the acceleration.
	 * Increase the position by the velocity.
	 * 
	 * Returns a string representation of the position for comparison.
	 */
	public String move() {
		getVelocity().setX(getVelocity().getX() + getAcceleration().getX());
		getVelocity().setY(getVelocity().getY() + getAcceleration().getY());
		getVelocity().setZ(getVelocity().getZ() + getAcceleration().getZ());
		getPosition().setX(getPosition().getX() + getVelocity().getX());
		getPosition().setY(getPosition().getY() + getVelocity().getY());
		getPosition().setZ(getPosition().getZ() + getVelocity().getZ());
		return (getPosition().toString());
	}

	/**
	 * Accessor for the position
	 */
	public Triple<Long> getPosition() {
		return _position;
	}

	/**
	 * Accessor for the velocity
	 */
	public Triple<Long> getVelocity() {
		return _velocity;
	}

	/**
	 * Accessor for the acceleration
	 */
	public Triple<Long> getAcceleration() {
		return _acceleration;
	}
}
