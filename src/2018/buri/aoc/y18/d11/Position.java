package buri.aoc.y18.d11;

import buri.aoc.data.AbstractPair;

/**
 * Integer-based ordered pair class representing a position on the grid.
 * 
 * A secondary "value" can be set showing the value of the grid at that spot.
 * 
 * @author Brian Uri!
 */
public class Position extends AbstractPair {

	private Long _value = null;

	/**
	 * Constructor
	 */
	public Position(int x, int y) {
		super(x, y);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getX()).append(",").append(getY());
		return (buffer.toString());
	}

	/**
	 * Accessor for the value
	 */
	public Long getValue() {
		return _value;
	}

	/**
	 * Accessor for the value
	 */
	public void setValue(Long value) {
		_value = value;
	}
}
