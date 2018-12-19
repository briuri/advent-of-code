package buri.aoc.data.grid;

import buri.aoc.data.Pair;

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
	private int _size;

	/**
	 * Constructor
	 */
	protected AbstractGrid(int size) {
		_size = size;
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
	public void set(Pair position, T value) {
		set(position.getX(), position.getY(), value);
	}

	/**
	 * Sets a value on the grid.
	 */
	public void set(int x, int y, T value) {
		getGrid()[x][y] = value;
	}

	/**
	 * Gets a value on the grid.
	 */
	public T get(Pair position) {
		return (get(position.getX(), position.getY()));
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
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
	 * Accessor for the size
	 */
	public int getSize() {
		return _size;
	}
	
	/**
	 * Accessor for the size
	 */
	protected void setSize(int size) {
		_size = size;
	}
}
