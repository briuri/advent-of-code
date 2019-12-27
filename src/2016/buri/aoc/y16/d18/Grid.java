package buri.aoc.y16.d18;

/**
 * @author Brian Uri!
 */
public class Grid {
	private Character[][] _grid;
	private static final char TRAP = '^';
	private static final char SAFE = '.';

	/**
	 * Constructor
	 */
	public Grid(String firstRow, int height) {
		_grid = new Character[firstRow.length()][height];
		for (int x = 0; x < firstRow.length(); x++) {
			getGrid()[x][0] = firstRow.charAt(x);
		}
		fillRows();
	}

	/**
	 * Let's call these three tiles from the previous row the left, center, and right tiles, respectively. Then, a new
	 * tile is a trap only in one of the following situations:
	 * 
	 * - Its left and center tiles are traps, but its right tile is not.
	 * - Its center and right tiles are traps, but its left tile is not.
	 * - Only its left tile is a trap.
	 * - Only its right tile is a trap.
	 */
	private void fillRows() {
		for (int y = 1; y < getGrid()[0].length; y++) {
			for (int x = 0; x < getGrid().length; x++) {
				boolean leftIsTrap = (x == 0 ? false : getGrid()[x - 1][y - 1] == TRAP);
				boolean centerIsTrap = (getGrid()[x][y - 1] == TRAP);
				boolean rightIsTrap = (x == getGrid().length - 1 ? false : getGrid()[x + 1][y - 1] == TRAP);
				boolean isTrap = (leftIsTrap && centerIsTrap && !rightIsTrap);
				isTrap = isTrap || (!leftIsTrap && centerIsTrap && rightIsTrap);
				isTrap = isTrap || (leftIsTrap && !centerIsTrap && !rightIsTrap);
				isTrap = isTrap || (!leftIsTrap && !centerIsTrap && rightIsTrap);
				getGrid()[x][y] = (isTrap ? TRAP : SAFE);
			}
		}
	}

	/**
	 * Returns the number of safe cells in the top rows.
	 */
	public int getSafeCount(int rows) {
		int count = 0;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < getGrid().length; x++) {
				if (getGrid()[x][y] == SAFE) {
					count++;
				}
			}
		}
		return (count);
	}

	private Character[][] getGrid() {
		return (_grid);
	}
}
