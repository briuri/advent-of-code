package buri.aoc.y17.d20;

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
		setPosition(new Triple(Long.valueOf(tokens[0]), Long.valueOf(tokens[1]), Long.valueOf(tokens[2])));
		setVelocity(new Triple(Long.valueOf(tokens[3]), Long.valueOf(tokens[4]), Long.valueOf(tokens[5])));
		_acceleration = new Triple(Long.valueOf(tokens[6]), Long.valueOf(tokens[7]), Long.valueOf(tokens[8]));
	}

	/**
	 * Increase the X velocity by the X acceleration.
	 * Increase the Y velocity by the Y acceleration.
	 * Increase the Z velocity by the Z acceleration.
	 * Increase the X position by the X velocity.
	 * Increase the Y position by the Y velocity.
	 * Increase the Z position by the Z velocity.
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
	public Triple getPosition() {
		return _position;
	}

	/**
	 * Accessor for the position
	 */
	private void setPosition(Triple position) {
		_position = position;
	}

	/**
	 * Accessor for the velocity
	 */
	public Triple getVelocity() {
		return _velocity;
	}

	/**
	 * Accessor for the velocity
	 */
	private void setVelocity(Triple velocity) {
		_velocity = velocity;
	}

	/**
	 * Accessor for the acceleration
	 */
	public Triple getAcceleration() {
		return _acceleration;
	}
}
