package buri.aoc.y15.d18;

import java.util.List;

import buri.aoc.common.Part;
import buri.aoc.common.data.grid.IntGrid;
import buri.aoc.common.data.tuple.Pair;

/**
 * @author Brian Uri!
 */
public class Grid extends IntGrid {

	/**
	 * Constructor
	 */
	public Grid(List<String> input) {
		super(new Pair(input.get(0).length(), input.size()));
		for (int y = 0; y < getHeight(); y++) {
			String line = input.get(y);
			for (int x = 0; x < getWidth(); x++) {
				char value = line.charAt(x);
				set(x, y, (value == '#' ? 1 : 0));
			}
		}
	}

	/**
	 * Animates the grid.
	 *
	 * A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
	 * A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
	 */
	public void animate(Part part, int steps) {
		for (int i = 0; i <= steps; i++) {
			if (part == Part.TWO) {
				set(0, 0, 1);
				set(0, getHeight() - 1, 1);
				set(getWidth() - 1, 0, 1);
				set(getWidth() - 1, getHeight() - 1, 1);
			}
			if (i == steps) {
				break;
			}
			Integer[][] newGrid = new Integer[getWidth()][getHeight()];
			for (int y = 0; y < getHeight(); y++) {
				for (int x = 0; x < getWidth(); x++) {
					int litNeighbors = getLitNeighbors(x, y);
					int value = get(x, y);
					if (value == 0) {
						value = (litNeighbors == 3) ? 1 : 0;
					}
					else if (value == 1) {
						value = (litNeighbors == 2 || litNeighbors == 3) ? 1 : 0;
					}
					newGrid[x][y] = value;
				}
			}
			setGrid(newGrid);
		}
	}

	/**
	 * Returns the number of lights lit.
	 */
	public int getLit() {
		int sum = 0;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				sum += get(x, y);
			}
		}
		return (sum);
	}

	/**
	 * Returns the number of lit neighbors around a cell.
	 */
	private int getLitNeighbors(int x, int y) {
		int sum = 0;
		if (y > 0) {
			// Upper Left
			if (x > 0) {
				sum += get(x - 1, y - 1);
			}
			// Up
			sum += get(x, y - 1);
			// Upper Right
			if (x < getWidth() - 1) {
				sum += get(x + 1, y - 1);
			}
		}
		if (x > 0) {
			// Left
			sum += get(x - 1, y);
		}
		if (x < getWidth() - 1) {
			// Right
			sum += get(x + 1, y);
		}
		if (y < getHeight() - 1) {
			// Lower Left
			if (x > 0) {
				sum += get(x - 1, y + 1);
			}
			// Down
			sum += get(x, y + 1);
			// Lower Right
			if (x < getWidth() - 1) {
				sum += get(x + 1, y + 1);
			}
		}
		return (sum);
	}
}