package buri.aoc.model;

/**
 * Day03
 * 
 * Data class for the spiral memory grid.
 * 
 * @author Brian Uri!
 */
public class SpiralGrid {

	private int[][] _grid;
	
	/**
	 * Creates a new square grid with the specified width/length.
	 */
	public SpiralGrid(int size) {
		_grid = new int[size][size];
	}
	
	/**
	 * Sets the value at some position.
	 */
	public void set(GridPosition position, int value) {
		getGrid()[position.getX()][position.getY()] = value;
	}
	
	/**
	 * Gets the sum of all adjacent and diagonal values from a position. (0,0) is the top left corner and (x, y) is the
	 * lower right corner.
	 */
	public int getSurroundingValues(GridPosition position) {
		int sum = 0;
		if (position.getY() > 0) {
			// Upper Left
			if (position.getX() > 0) {
				sum += getGrid()[position.getX() - 1][position.getY() - 1];
			}
			// Left
			sum += getGrid()[position.getX()][position.getY() - 1];
			// Lower Left
			if (position.getX() < getGrid().length - 1) {
				sum += getGrid()[position.getX() + 1][position.getY() - 1];
			}
		}
		if (position.getX() > 0) {
			// Up
			sum += getGrid()[position.getX() - 1][position.getY()];
		}
		if (position.getX() < getGrid().length - 1) {
			// Down
			sum += getGrid()[position.getX() + 1][position.getY()];
		}
		if (position.getY() < getGrid().length - 1) {
			// Upper Right
			if (position.getX() > 0) {
				sum += getGrid()[position.getX() - 1][position.getY() + 1];
			}
			// Right
			sum += getGrid()[position.getX()][position.getY() + 1];
			// Lower Right
			if (position.getX() < getGrid().length - 1) {
				sum += getGrid()[position.getX() + 1][position.getY() + 1];
			}
		}
		return (sum);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < getGrid().length; i++) {
			buffer.append("\t");
			for (int j = 0; j < getGrid()[i].length; j++) {
				String value = String.valueOf(getGrid()[i][j]);
				buffer.append(String.format("%1$-5s", value));
			}
			buffer.append("\n");
		}
		return (buffer.toString());
	}
	
	/**
	 * Accessor for the raw grid.
	 */
	private int[][] getGrid() {
		return (_grid);
	}
}
