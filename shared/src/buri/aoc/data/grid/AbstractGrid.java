package buri.aoc.data.grid;

import buri.aoc.data.tuple.Pair;

/**
 * Base abstract class for 2D square grids.
 *
 * Based on Java's array indexing:
 * (0,0) is the upper left corner of a grid.
 * (x,0) is lower left corner of a grid.
 * (0,y) is upper right corner of grid.
 * (x,y) is lower right corner of grid.
 *
 * @author Brian Uri!
 */
public abstract class AbstractGrid<T> {
	private Pair _size;

	/**
	 * Constructor
	 */
	protected AbstractGrid(Pair size) {
		if (!(size.getX() instanceof Integer)) {
			throw new IllegalArgumentException("Size must be integer-based.");
		}
		_size = size;
	}

	/**
	 * Returns the point nearest to the center of the grid.
	 */
	public Pair getCenterPosition() {
		return (new Pair(getWidth() / 2, getHeight() / 2));
	}

	/**
	 * Counts the number of occurrences of some value in the grid.
	 */
	public int count(T value) {
		int count = 0;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if (value.equals(get(x, y))) {
					count++;
				}
			}
		}
		return (count);
	}

	/**
	 * Returns true if the point is in-bounds of the grid.
	 */
	public boolean isInBounds(Pair<Integer> point) {
		return (point.getX() >= 0 && point.getX() < getWidth() && point.getY() >= 0 && point.getY() < getHeight());
	}

	/**
	 * Gets a value on the grid.
	 */
	public T get(Pair<Integer> position) {
		return (get(position.getX(), position.getY()));
	}

	/**
	 * Gets a value on the grid.
	 */
	public T get(int x, int y) {
		return (getGrid()[x][y]);
	}

	/**
	 * Sets a value on the grid.
	 */
	public void set(Pair<Integer> position, T value) {
		set(position.getX(), position.getY(), value);
	}

	/**
	 * Sets a value on the grid.
	 */
	public void set(int x, int y, T value) {
		getGrid()[x][y] = value;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				buffer.append(toOutput((T) get(x, y)));
			}
			buffer.append("\n");
		}
		buffer.append("\n");
		return (buffer.toString());
	}

	/**
	 * Implementation-specific means of outputting the value.
	 */
	protected String toOutput(T value) {
		return (String.valueOf(value));
	}

	/**
	 * Accessor for the raw grid.
	 */
	protected abstract T[][] getGrid();

	/**
	 * Accessor for the raw grid.
	 */
	protected abstract void setGrid(T[][] grid);

	/**
	 * Accessor for the horizontal size
	 */
	public int getWidth() {
		return _size.getX().intValue();
	}

	/**
	 * Accessor for the vertical size
	 */
	public int getHeight() {
		return _size.getY().intValue();
	}

	/**
	 * Accessor for the size
	 */
	protected void setSize(Pair size) {
		_size = size;
	}
}
