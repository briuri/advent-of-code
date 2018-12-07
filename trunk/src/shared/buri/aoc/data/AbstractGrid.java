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

	private int[][] _grid;
	private int _padding;
	/**
	 * Creates a new square grid with the specified width/length.
	 */
	public AbstractGrid(int size, int padding) {
		_grid = new int[size][size];
		_padding = padding;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < getGrid().length; i++) {
			buffer.append("\t");
			for (int j = 0; j < getGrid()[i].length; j++) {
				String value = String.valueOf(getGrid()[i][j]);
				buffer.append(String.format("%1$-" + getPadding() + "s", value));
			}
			buffer.append("\n");
		}
		return (buffer.toString());
	}
	
	/**
	 * Accessor for the raw grid.
	 */
	protected int[][] getGrid() {
		return (_grid);
	}

	/**
	 * Accessor for the padding
	 */
	private int getPadding() {
		return _padding;
	}
}
