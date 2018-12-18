package buri.aoc.data;

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
public abstract class AbstractCharGrid {
	private char[][] _grid;

	/**
	 * Creates a new square grid with the specified width/length.
	 */
	public AbstractCharGrid(int size) {
		_grid = new char[size][size];
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < getGrid().length; y++) {
			for (int x = 0; x < getGrid().length; x++) {
				buffer.append(get(x, y));
			}
			buffer.append("\n");
		}
		buffer.append("\n");
		return (buffer.toString());
	}
		
	/**
	 * Gets a value on the grid.
	 */
	protected char get(int x, int y) {
		return (getGrid()[x][y]);
	}

	/**
	 * Sets a value on the grid.
	 */
	protected void set(int x, int y, char value) {
		getGrid()[x][y] = value;
	}

	/**
	 * Accessor for the raw grid.
	 */
	protected char[][] getGrid() {
		return (_grid);
	}
}
