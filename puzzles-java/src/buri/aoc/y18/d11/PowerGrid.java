package buri.aoc.y18.d11;

import buri.aoc.common.data.grid.IntGrid;
import buri.aoc.common.data.tuple.Pair;

/**
 * Simple grid class for calculating fuel cell charges.
 *
 * @author Brian Uri!
 */
public class PowerGrid extends IntGrid {

	private final int _serial;

	/**
	 * Constructor. Initializes all power levels.
	 */
	public PowerGrid(int size, int serial) {
		super(new Pair<>(size, size));
		_serial = serial;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				int rackId = x + 10;
				int power = (rackId * y) + getSerial();
				power = power * rackId;
				if (power < 100) {
					power = 0;
				}
				else {
					String stringPower = String.valueOf(power);
					char hundreds = stringPower.charAt(stringPower.length() - 3);
					power = Character.getNumericValue(hundreds);
				}
				power = power - 5;
				set(x, y, power);
			}
		}
	}

	/**
	 * Searches the entire grid for the cell with the largest value.
	 */
	public Pair<Integer> getMaxValuePosition(int squareSumSize) {
		PowerGrid grid = new PowerGrid(getWidth() - (squareSumSize - 1), 0);
		for (int y = 0; y < grid.getHeight(); y++) {
			for (int x = 0; x < grid.getWidth(); x++) {
				Pair<Integer> upperLeft = new Pair<>(x, y);
				grid.set(upperLeft, getSquareSum(upperLeft, squareSumSize));
			}
		}

		Pair<Integer> maxPosition = null;
		long maxValue = Long.MIN_VALUE;
		for (int y = 0; y < grid.getHeight(); y++) {
			for (int x = 0; x < grid.getWidth(); x++) {
				Pair<Integer> position = new Pair<>(x, y);
				long value = grid.get(position);
				if (value > maxValue) {
					maxValue = value;
					maxPosition = position;
				}
			}
		}
		return (maxPosition);
	}

	/**
	 * Returns the sum of all cells in a square starting from the upper-left cell.
	 */
	public int getSquareSum(Pair<Integer> ul, int squareSumSize) {
		int sum = 0;
		for (int dx = 0; dx < squareSumSize; dx++) {
			for (int dy = 0; dy < squareSumSize; dy++) {
				sum += get(ul.getX() + dx, ul.getY() + dy);
			}
		}
		return (sum);
	}

	/**
	 * Accessor for the serial
	 */
	private int getSerial() {
		return _serial;
	}
}