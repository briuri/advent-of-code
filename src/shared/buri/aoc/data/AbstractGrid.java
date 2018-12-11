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
public abstract class AbstractGrid {
	private int _size;
	private int[][] _grid;

	/**
	 * Creates a new square grid with the specified width/length.
	 */
	public AbstractGrid(int size) {
		_size = size;
		_grid = new int[getSize()][getSize()];
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int x = 0; x < getSize(); x++) {
			buffer.append("\t");
			for (int y = 0; y < getSize(); y++) {
				String value = String.valueOf(getGrid()[x][y]);
				buffer.append(value).append("\t");
			}
			buffer.append("\n");
		}
		return (buffer.toString());
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
	protected int[][] getGrid() {
		return (_grid);
	}
}
