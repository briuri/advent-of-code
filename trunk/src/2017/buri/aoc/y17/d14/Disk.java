package buri.aoc.y17.d14;

import java.util.List;

import buri.aoc.data.AbstractGrid;

/**
 * @author Brian Uri!
 */
public class Disk extends AbstractGrid {

	/**
	 * Constructor
	 */
	public Disk(List<String> rows) {
		super(128);
		for (int x = 0; x < getGrid().length; x++) {
			for (int y = 0; y < getGrid()[x].length; y++) {
				// Convert to 0/1.
				int value = Integer.valueOf(rows.get(x).charAt(y)) - 48;
				// Switch 1 to -1 to represent "unevaluated".
				value = (value == 1) ? -1 : value;
				getGrid()[x][y] = value;
			}
		}
	}

	/**
	 * Evaluates each cell in the disk to find adjacent cells that make up regions. Used cells start as -1, while free
	 * cells are 0. As the regions are found, the -1 is replaced by the number of the region.
	 */
	public int countRegions() {
		int regions = 0;
		for (int x = 0; x < getGrid().length; x++) {
			for (int y = 0; y < getGrid()[x].length; y++) {
				int value = getGrid()[x][y];
				if (value == -1) {
					regions++;
					touchAdjacentCells(x, y, regions);
				}
				else if (value != 0) {
					touchAdjacentCells(x, y, value);
				}
			}
		}
		return (regions);
	}
	
	/**
	 * Fills this cell with a value, then fills any immediately adjacent -1 cells with the value. Expands virally out
	 * until nothing is left to touch.
	 */
	private void touchAdjacentCells(int x, int y, int value) {
		// Center
		fillUnevaluatedCell(x, y, value);
		boolean changed = false;
		// Left
		if (y > 0) {
			changed = fillUnevaluatedCell(x, y - 1, value);
			if (changed) {
				touchAdjacentCells(x, y - 1, value);
			}
		}
		// Up
		if (x > 0) {
			changed = fillUnevaluatedCell(x - 1, y, value);
			if (changed) {
				touchAdjacentCells(x - 1, y, value);
			}
		}
		// Down
		if (x < getGrid().length - 1) {
			changed = fillUnevaluatedCell(x + 1, y, value);
			if (changed) {
				touchAdjacentCells(x + 1, y, value);
			}
		}
		// Right
		if (y < getGrid().length - 1) {
			changed = fillUnevaluatedCell(x, y + 1, value);
			if (changed) {
				touchAdjacentCells(x, y + 1, value);
			}
		}
	}
	
	/**
	 * Fills a cell with some value, but only if it is currently -1. Returns true if a cell changed.
	 */
	private boolean fillUnevaluatedCell(int x, int y, int value) {
		int currentValue = getGrid()[x][y];
		if (currentValue == -1) {
			getGrid()[x][y] = value;
			return (true);
		}
		return (false);
	}
}
