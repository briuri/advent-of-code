package buri.aoc.y17.d21;

import java.util.List;

import buri.aoc.data.grid.IntGrid;

/**
 * A simple 2x2 or 3x3 pattern used in a rule.
 * 
 * @author Brian Uri!
 */
public class Pattern extends IntGrid {

	/**
	 * Input-based Constructor
	 */
	public Pattern(List<String> rows) {
		super(rows.size());
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
				set(x, y, (rows.get(y).charAt(x) == '#' ? 1 : 0));
			}
		}
	}

	/**
	 * Array-based Constructor
	 */
	public Pattern(int[][] pattern) {
		super(pattern.length);
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
				set(x, y, pattern[x][y]);
			}
		}
	}

	/**
	 * Returns this pattern flipped across the vertical axis.
	 */
	public Pattern flip() {
		int[][] pattern = new int[getSize()][getSize()];
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
				pattern[getSize() - x - 1][y] = get(x, y);
			}
		}
		return (new Pattern(pattern));
	}

	/**
	 * Returns this pattern rotated 90 degrees counterclockwise
	 */
	public Pattern rotate() {
		int[][] pattern = new int[getSize()][getSize()];
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
				pattern[x][getSize() - 1 - y] = get(y, x);
			}
		}
		return new Pattern(pattern);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
				buffer.append(get(x, y));
			}
			buffer.append("\n");
		}
		return (buffer.toString());
	}
}
