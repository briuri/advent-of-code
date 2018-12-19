package buri.aoc.y17.d20;

import buri.aoc.data.Triple;

/**
 * Data model for a particle with position, velocity, and acceleration.
 */
public class Particle {

	private Triple _position;
	private Triple _velocity;
	private Triple _acceleration;

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
		getVelocity().add(getAcceleration());
		getPosition().add(getVelocity());
		return (getPosition().toString());
	}
	
	/**
	 * Accessor for the position
	 */
	public Triple getPosition() {
		return _position;
	}

	/**
	 * Accessor for the velocity
	 */
	public Triple getVelocity() {
		return _velocity;
	}

	/**
	 * Accessor for the acceleration
	 */
	public Triple getAcceleration() {
		return _acceleration;
	}
}
