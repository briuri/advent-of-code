package buri.aoc.data;

import buri.aoc.y18.d11.Position;

/**
 * Base class for 2D square grid data models.
 * 
 * Based on Java's array indexing:
 * (0,0) is the upper left corner of a grid.
 * (x,0) is lower left corner of a grid.
 * (0,y) is upper right corner of grid.
 * (x,y) is lower right corner of grid.
 * 
 * @author Brian Uri!
 */
public abstract class AbstractGrid {
	private int _size;
	private long[][] _grid;

	/**
	 * Creates a new square grid with the specified width/length.
	 */
	public AbstractGrid(int size) {
		_size = size;
		_grid = new long[getSize()][getSize()];
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
				buffer.append(toOutput(getGrid()[x][y]));
			}
			buffer.append("\n");
		}
		buffer.append("\n");
		return (buffer.toString());
	}

	/**
	 * Implementation-specific means of outputting the value.
	 */
	protected String toOutput(long value) {
		return (String.valueOf(value) + "\t");
	}
	
	/**
	 * Returns the value at some position.
	 */
	public long get(Position p) {
		return getGrid()[p.getX()][p.getY()];
	}
	
	/**
	 * Marks a spot on the grid.
	 */
	public void set(AbstractPair p, long value) {
		getGrid()[p.getX()][p.getY()] = value;
	}
	
	/**
	 * Accessor for the size
	 */
	protected int getSize() {
		return _size;
	}
		
	/**
	 * Accessor for the raw grid.
	 */
	protected long[][] getGrid() {
		return (_grid);
	}
}
