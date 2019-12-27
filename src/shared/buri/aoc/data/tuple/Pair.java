package buri.aoc.data.tuple;

import buri.aoc.data.Direction;

/**
 * Base class for an X-Y coordinate pair.
 * 
 * Based on Java's array indexing:
 * (0,0) is the upper left corner of a grid.
 * (x,0) is lower left corner of a grid.
 * (0,y) is upper right corner of grid.
 * (x,y) is lower right corner of grid.
 * 
 * @author Brian Uri!
 */
public class Pair<T extends Number> implements Comparable<Pair> {
	private T _x;
	private T _y;
	private boolean _integerBased;

	/**
	 * Base constructor
	 */
	protected Pair() {}

	/**
	 * String-based Constructor with format "x, y"
	 */
	public Pair(String data, Class<T> clazz) {
		String tokens[] = data.split(",");
        if (clazz.isAssignableFrom(Integer.class)) {
			_x = (T) Integer.valueOf(tokens[0].trim());
			_y = (T) Integer.valueOf(tokens[1].trim());
			_integerBased = true;
        } else if (clazz.isAssignableFrom(Long.class)) {
			_x = (T) Long.valueOf(tokens[0].trim());
			_y = (T) Long.valueOf(tokens[1].trim());
			_integerBased = false;
        } else {
            throw new IllegalArgumentException("Unsupported type: " + clazz.getSimpleName());
        }
	}

	/**
	 * Number-based Constructor
	 */
	public Pair(T x, T y) {
		_x = x;
		_y = y;
		_integerBased = (x instanceof Integer);
	}

	/**
	 * Returns a copy of this pair.
	 */
	public Pair copy() {
		return (new Pair(getX(), getY()));
	}

	/**
	 * Moves the position 1 step in a direction. (0,0 is top-left).
	 */
	public void move(Direction direction) {
		if (!isIntegerBased()) {
			throw new IllegalArgumentException("move() only implemented for Integer-based Pairs.");
		}
		switch (direction) {
			case UP:
				setY((T) Integer.valueOf(getY().intValue() - 1));
				break;
			case RIGHT:
				setX((T) Integer.valueOf(getX().intValue() + 1));
				break;
			case DOWN:
				setY((T) Integer.valueOf(getY().intValue() + 1));
				break;
			case LEFT:
				setX((T) Integer.valueOf(getX().intValue() - 1));
				break;
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getX()).append(",").append(getY());
		return (buffer.toString());
	}

	/**
	 * Pairs are compared by y then x (top-left first).
	 */
	@Override
	public int compareTo(Pair o) {
		int compare = Long.valueOf(getY().longValue()).compareTo(Long.valueOf(o.getY().longValue()));
		if (compare == 0) {
			compare = Long.valueOf(getX().longValue()).compareTo(Long.valueOf(o.getX().longValue()));
		}
		return compare;
	}
	
	@Override
	public boolean equals(Object obj) {
		Pair pair = (Pair) obj;
		return (getX().equals(pair.getX())
			&& getY().equals(pair.getY()));
	}
	
	@Override
	public int hashCode() {
		int result = getX().hashCode();
		result += getY().hashCode();
		return (result);
	}
	
	/**
	 * Returns true of T is Integer-based.
	 */
	public boolean isIntegerBased() {
		return (_integerBased);
	}
	
	/**
	 * Accessor for X
	 */
	public T getX() {
		return _x;
	}

	/**
	 * Accessor for X
	 */
	public void setX(T x) {
		_x = x;
	}

	/**
	 * Accessor for Y
	 */
	public T getY() {
		return _y;
	}

	/**
	 * Accessor for Y
	 */
	public void setY(T y) {
		_y = y;
	}
}