package buri.aoc.data.tuple;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.data.Direction;

/**
 * Data class for a pair tuple, intended for Integers or Longs.
 * 
 * @author Brian Uri!
 */
public class Pair<T extends Number> extends BaseTuple implements Comparable<Pair> {

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
			getValues().add((T) Integer.valueOf(tokens[0].trim()));
			getValues().add((T) Integer.valueOf(tokens[1].trim()));
		}
		else if (clazz.isAssignableFrom(Long.class)) {
			getValues().add((T) Long.valueOf(tokens[0].trim()));
			getValues().add((T) Long.valueOf(tokens[1].trim()));
		}
		else {
			throw new IllegalArgumentException("Unsupported pair type: " + clazz.getSimpleName());
		}
	}

	/**
	 * Number-based Constructor
	 */
	public Pair(T x, T y) {
		getValues().add(x);
		getValues().add(y);
		if (!(x instanceof Integer) && !(x instanceof Long)) {
			throw new IllegalArgumentException("Unsupported pair type: " + x.getClass().getSimpleName());
		}
	}

	/**
	 * Returns a copy of this pair.
	 */
	public Pair copy() {
		return (new Pair(getX(), getY()));
	}

	/**
	 * Returns a list of adjacent tuples in an X-Y grid in reading order.
	 */
	public List<Pair<T>> getAdjacent() {
		List<Pair<T>> adjacent = new ArrayList<>();
		if (getX() instanceof Integer) {
			adjacent.add(new Pair(getX(), getY().intValue() - 1));
			adjacent.add(new Pair(getX().intValue() - 1, getY()));
			adjacent.add(new Pair(getX().intValue() + 1, getY()));
			adjacent.add(new Pair(getX(), getY().intValue() + 1));
		}
		else {
			adjacent.add(new Pair(getX(), getY().longValue() - 1L));
			adjacent.add(new Pair(getX().longValue() - 1L, getY()));
			adjacent.add(new Pair(getX().longValue() + 1L, getY()));
			adjacent.add(new Pair(getX(), getY().longValue() + 1L));
		}
		return (adjacent);
	}

	/**
	 * Moves the position 1 step in a 2D direction.
	 * 
	 * Based on Java's array indexing:
	 * (0,0) is the upper left corner of a grid.
	 * (x,0) is lower left corner of a grid.
	 * (0,y) is upper right corner of grid.
	 * (x,y) is lower right corner of grid.
	 */
	public void move(Direction direction) {
		if (getX() instanceof Integer) {
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
		else {
			switch (direction) {
				case UP:
					setY((T) Long.valueOf(getY().longValue() - 1L));
					break;
				case RIGHT:
					setX((T) Long.valueOf(getX().longValue() + 1L));
					break;
				case DOWN:
					setY((T) Long.valueOf(getY().longValue() + 1L));
					break;
				case LEFT:
					setX((T) Long.valueOf(getX().longValue() - 1L));
					break;
			}
		}
	}

	/**
	 * Sort order: by Y, then X (top-left first)
	 */
	@Override
	public int compareTo(Pair pair) {
		int compare = compare(getY(), pair.getY());
		if (compare == 0) {
			compare = compare(getX(), pair.getX());
		}
		return compare;
	}

	/**
	 * Accessor for the x
	 */
	public T getX() {
		return ((T) getValues().get(0));
	}

	/**
	 * Accessor for the x
	 */
	public void setX(T x) {
		getValues().set(0, x);
	}

	/**
	 * Accessor for the y
	 */
	public T getY() {
		return ((T) getValues().get(1));
	}

	/**
	 * Accessor for the y
	 */
	public void setY(T y) {
		getValues().set(1, y);
	}
}