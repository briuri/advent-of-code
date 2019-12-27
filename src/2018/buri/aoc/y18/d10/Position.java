package buri.aoc.y18.d10;

import buri.aoc.data.tuple.Pair;

/**
 * Integer-based ordered pair class representing a movable position on a grid.
 * 
 * @author Brian Uri!
 */
public class Position extends Pair {
	private int _originalX;
	private int _originalY;
	private int _velocityX;
	private int _velocityY;

	/**
	 * Constructor
	 */
	public Position(String data) {
		String[] tokens = data.split(",");
		setX(Integer.valueOf(tokens[0]));
		setY(Integer.valueOf(tokens[1]));
		_originalX = getX().intValue();
		_originalY = getY().intValue();
		_velocityX = Integer.valueOf(tokens[2]);
		_velocityY = Integer.valueOf(tokens[3]);
	}

	/**
	 * Int-based constructor
	 */
	public Position(int x, int y) {
		super(x, y);
	}

	/**
	 * Returns this point back to the original location.
	 */
	public void reset() {
		setX(getOriginalX());
		setY(getOriginalY());
	}

	/**
	 * Shifts the output back to the origin for ease of reading.
	 */
	public void offset(Position position) {
		setX(getX().intValue() - position.getX().intValue());
		setY(getY().intValue() - position.getY().intValue());
	}

	/**
	 * Moves this point some number of times.
	 */
	public void move(int times) {
		setX(getX().intValue() + (times * getVelocityX()));
		setY(getY().intValue() + (times * getVelocityY()));
	}

	/**
	 * Accessor for the original X coordinate.
	 */
	private int getOriginalX() {
		return _originalX;
	}

	/**
	 * Accessor for the original Y coordinate.
	 */
	private int getOriginalY() {
		return _originalY;
	}

	/**
	 * Accessor for the velocity in the X direction
	 */
	private int getVelocityX() {
		return _velocityX;
	}

	/**
	 * Accessor for the velocity in the Y direction
	 */
	private int getVelocityY() {
		return _velocityY;
	}
}
