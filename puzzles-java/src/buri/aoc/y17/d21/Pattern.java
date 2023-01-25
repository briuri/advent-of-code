package buri.aoc.y17.d21;

import java.util.List;

import buri.aoc.common.data.grid.IntGrid;
import buri.aoc.common.data.tuple.Pair;

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
		super(new Pair(rows.get(0).length(), rows.size()));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, (rows.get(y).charAt(x) == '#' ? 1 : 0));
			}
		}
	}

	/**
	 * Array-based Constructor
	 */
	public Pattern(int[][] pattern) {
		super(new Pair(pattern[0].length, pattern.length));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, pattern[x][y]);
			}
		}
	}

	/**
	 * Returns this pattern flipped across the vertical axis.
	 */
	public Pattern flip() {
		int[][] pattern = new int[getWidth()][getHeight()];
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				pattern[getWidth() - x - 1][y] = get(x, y);
			}
		}
		return (new Pattern(pattern));
	}

	/**
	 * Returns this pattern rotated 90 degrees counterclockwise
	 */
	public Pattern rotate() {
		int[][] pattern = new int[getWidth()][getHeight()];
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				pattern[x][getHeight() - 1 - y] = get(y, x);
			}
		}
		return new Pattern(pattern);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				buffer.append(get(x, y));
			}
			buffer.append("\n");
		}
		return (buffer.toString());
	}
}