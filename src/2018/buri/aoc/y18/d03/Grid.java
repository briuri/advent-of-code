package buri.aoc.y18.d03;

/**
 * Day03
 * 
 * Based on Java's array indexing:
 * (0,0) is the upper left corner of a grid.
 * (x,0) is lower left corner of a grid.
 * (0,y) is upper right corner of grid.
 * (x,y) is lower right corner of grid.
 * 
 * 
 * @author Brian Uri!
 */
public class Grid {

	private int[][] _grid;

	/**
	 * Creates a new square grid with the specified width/length.
	 */
	public Grid(int size) {
		_grid = new int[size][size];
	}

	/**
	 * Mark a spot on the grid as claimed by incrementing its value.
	 */
	public void claimSquare(int x, int y) {
		getGrid()[x][y] = getGrid()[x][y] + 1;
	}

	/**
	 * Counts the squares that overlap between 2 or more claims.
	 */
	public int countOverlaps() {
		int sum = 0;
		for (int i = 0; i < getGrid().length; i++) {
			for (int j = 0; j < getGrid()[i].length; j++) {
				if (getGrid()[i][j] >= 2) {
					sum++;
				}
			}
		}
		return (sum);
	}

	/**
	 * Returns true if a claim has no overlap. When this is true, the sum of all values in the claim add up to width x
	 * height.
	 */
	public boolean hasNoOverlap(Claim claim) {
		int sum = 0;
		for (int w = 0; w < claim.getWidth(); w++) {
			for (int h = 0; h < claim.getHeight(); h++) {
				sum += getGrid()[claim.getX() + w][claim.getY() + h];
			}
		}
		return (sum == claim.getSize());
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < getGrid().length; i++) {
			buffer.append("\t");
			for (int j = 0; j < getGrid()[i].length; j++) {
				String value = String.valueOf(getGrid()[i][j]);
				buffer.append(String.format("%1$-1s", value));
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
