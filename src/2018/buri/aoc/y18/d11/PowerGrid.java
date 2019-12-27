package buri.aoc.y18.d11;

import buri.aoc.data.grid.IntGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Simple grid class for calculating fuel cell charges.
 * 
 * @author Brian Uri!
 */
public class PowerGrid extends IntGrid {

	private int _serial;

	/**
	 * Constructor. Initializes all power levels.
	 */
	public PowerGrid(int size, int serial) {
		super(new Pair(size, size));
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
					Character hundreds = stringPower.charAt(stringPower.length() - 3);
					power = Character.getNumericValue(hundreds);
				}
				power = power - 5;
				set(new Position(x, y), power);
			}
		}
	}

	/**
	 * Generates a reduced grid containing the sums of squares within the larger grid.
	 */
	public PowerGrid getReduction(int squareSumSize) {
		PowerGrid grid = new PowerGrid(getWidth() - (squareSumSize - 1), 0);
		for (int y = 0; y < grid.getHeight(); y++) {
			for (int x = 0; x < grid.getWidth(); x++) {
				Position upperLeft = new Position(x, y);
				grid.set(upperLeft, getSquareSum(upperLeft, squareSumSize));
			}
		}
		return (grid);
	}

	/**
	 * Searches the entire grid for the cell with the largest value.
	 */
	public Position getMaxValuePosition() {
		Position maxPosition = null;
		long maxValue = Long.MIN_VALUE;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				Position position = new Position(x, y);
				long value = get(position);
				if (value > maxValue) {
					maxValue = value;
					position.setValue(value);
					maxPosition = position;
				}
			}
		}
		return (maxPosition);
	}

	/**
	 * Returns the sum of all cells in a square starting from the upper-left cell.
	 */
	public int getSquareSum(Position ul, int squareSumSize) {
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