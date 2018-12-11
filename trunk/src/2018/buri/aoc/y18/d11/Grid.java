package buri.aoc.y18.d11;

import buri.aoc.data.AbstractGrid;

/**
 * Simple grid class for calculating fuel cell charges.
 * 
 * @author Brian Uri!
 */
public class Grid extends AbstractGrid {

	private int _serial;

	/**
	 * Constructor. Initializes all power levels.
	 */
	public Grid(int size, int serial) {
		super(size, 0);
		_serial = serial;
		for (int x = 0; x < getSize(); x++) {
			for (int y = 0; y < getSize(); y++) {
				int rackId = x + 10;
				int power = (rackId * y) + getSerial();
				power = power * rackId;
				if (power < 100) {
					power = 0;
				}
				else {
					String stringPower = String.valueOf(power);
					Character hundreds = stringPower.charAt(stringPower.length() - 3);
					power = Integer.valueOf(String.valueOf(hundreds));
				}
				power = power - 5;
				set(new Position(x, y), power);
			}
		}
	}

	/**
	 * Generates a reduced grid containing the sums of squares within the larger grid.
	 */
	public Grid getReduction(int squareSumSize) {
		Grid grid = new Grid(getGrid().length - (squareSumSize - 1), 0);
		for (int x = 0; x < grid.getSize(); x++) {
			for (int y = 0; y < grid.getSize(); y++) {
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
		int maxValue = Integer.MIN_VALUE;
		for (int x = 0; x < getSize(); x++) {
			for (int y = 0; y < getSize(); y++) {
				Position position = new Position(x, y);
				int value = get(position);
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
				sum += getGrid()[ul.getX() + dx][ul.getY() + dy];
			}
		}
		return (sum);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
				int value = getGrid()[x][y];
				buffer.append("\t" + value);
			}
			buffer.append("\n");
		}
		buffer.append("\n");
		return (buffer.toString());
	}
	
	/**
	 * Returns the value at some position.
	 */
	private int get(Position p) {
		return getGrid()[p.getX()][p.getY()];
	}

	/**
	 * Marks a spot on the grid.
	 */
	private void set(Position p, int power) {
		getGrid()[p.getX()][p.getY()] = power;
	}

	/**
	 * Accessor for the serial
	 */
	private int getSerial() {
		return _serial;
	}
}