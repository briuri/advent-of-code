package buri.aoc.y18.d18;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.data.Pair;
import buri.aoc.data.grid.CharGrid;

/**
 * Model for the acreage.
 * 
 * @author Brian Uri!
 */
public class Acreage extends CharGrid {
	public static final char OPEN = '.';
	public static final char TREES = '|';
	public static final char YARD = '#';

	/**
	 * Constructor
	 */
	public Acreage(List<String> input) {
		super(new Pair(input.get(0).length(), input.size()));
		for (int y = 0; y < getHeight(); y++) {
			String line = input.get(y);
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, line.charAt(x));
			}
		}
	}

	/**
	 * Evolves the landscape based on the evolution rules. Changes happen simultaneously using STATE AT BEGINNING of
	 * minute. Changes that happen during the minute don't affect each other
	 */
	public int evolve(int minutes) {
		List<String> outputs = new ArrayList<>();
		List<Integer> resourceValues = new ArrayList<>();
		for (int i = 0; i < minutes; i++) {
			// Make changes in a separate grid and copy back onto this one.
			Character[][] updatedGrid = new Character[getWidth()][getHeight()];
			for (int y = 0; y < getHeight(); y++) {
				for (int x = 0; x < getWidth(); x++) {
					updatedGrid[x][y] = getNewValue(x, y);
				}
			}
			System.arraycopy(updatedGrid, 0, getGrid(), 0, getWidth());

			// Keep records of old grid changes so we can detect cycles.
			String output = toString();
			int index = outputs.indexOf(output);
			// We found a cycle, so we can extrapolate ahead to the desired minutes.
			if (index != -1) {
				int cycleSize = i - index;
				int desiredIndex = index + ((minutes - i) % cycleSize) - 1;
				return (resourceValues.get(desiredIndex));
			}
			outputs.add(output);
			resourceValues.add(getTotalResourceValue());
		}
		return (getTotalResourceValue());
	}

	/**
	 * Returns the new value for a square based on its adjacent values (all 8 cells around are adjacent).
	 * 
	 * OPEN -> TREES if adjacent squares have >= 3 trees.
	 * TREES -> YARD if adjacent squares have >= 3+ yards.
	 * YARD -> OPEN if adjacent squares have (<1 yards or <1 trees).
	 */
	private char getNewValue(int x, int y) {
		char value = get(x, y);
		AdjacentValues adjacentValues = getAdjacentValues(x, y);
		char newValue;
		if (value == OPEN) {
			newValue = (adjacentValues.get(Acreage.TREES) >= 3 ? TREES : OPEN);
		}
		else if (value == TREES) {
			newValue = (adjacentValues.get(Acreage.YARD) >= 3 ? YARD : TREES);
		}
		// YARD
		else {
			newValue = (adjacentValues.get(Acreage.TREES) >= 1 && adjacentValues.get(Acreage.YARD) >= 1 ? YARD : OPEN);
		}
		return (newValue);
	}

	/**
	 * Counts up the tiles in the 8 surrounding cells.
	 */
	private AdjacentValues getAdjacentValues(int x, int y) {
		AdjacentValues adjacentValues = new AdjacentValues();
		if (y > 0) {
			// Upper Left
			if (x > 0) {
				adjacentValues.add(get(x - 1, y - 1));
			}
			// Up
			adjacentValues.add(get(x, y - 1));
			// Upper Right
			if (x < getWidth() - 1) {
				adjacentValues.add(get(x + 1, y - 1));
			}
		}
		if (x > 0) {
			// Left
			adjacentValues.add(get(x - 1, y));
		}
		if (x < getWidth() - 1) {
			// Right
			adjacentValues.add(get(x + 1, y));
		}
		if (y < getHeight() - 1) {
			// Lower Left
			if (x > 0) {
				adjacentValues.add(get(x - 1, y + 1));
			}
			// Down
			adjacentValues.add(get(x, y + 1));
			// Lower Right
			if (x < getWidth() - 1) {
				adjacentValues.add(get(x + 1, y + 1));
			}
		}
		return (adjacentValues);
	}

	/**
	 * Calculates the total resource value of the acreage.
	 */
	private int getTotalResourceValue() {
		int trees = 0;
		int yards = 0;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				Character value = get(x, y);
				if (value == TREES) {
					trees++;
				}
				else if (value == YARD) {
					yards++;
				}
			}
		}
		return (trees * yards);
	}
}