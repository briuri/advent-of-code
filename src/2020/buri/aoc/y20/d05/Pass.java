package buri.aoc.y20.d05;

/**
 * Data model for a boarding pass.
 *
 * @author Brian Uri!
 */
public class Pass {
	private int _row;
	private int _column;

	/**
	 * Constructor
	 *
	 * FBFBBFFRLR
	 */
	public Pass(String input) {
		input = input.replaceAll("F", "0").replaceAll("B", "1").replaceAll("L", "0").replaceAll("R", "1");
		_row = Integer.parseInt(input.substring(0, 7), 2);
		_column = Integer.parseInt(input.substring(7, 10), 2);
	}

	/**
	 * Accessor for the unique ID
	 */
	public int getId() {
		return (getRow() * 8 + getColumn());
	}

	/**
	 * Accessor for the row
	 */
	private int getRow() {
		return _row;
	}

	/**
	 * Accessor for the column
	 */
	private int getColumn() {
		return _column;
	}
}