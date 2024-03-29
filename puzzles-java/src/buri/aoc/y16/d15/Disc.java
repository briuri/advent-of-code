package buri.aoc.y16.d15;

/**
 * @author Brian Uri!
 */
public class Disc {

	private final int _number;
	private final int _positions;
	private final int _start;

	/**
	 * Constructor
	 */
	public Disc(String input) {
		String[] tokens = input.split(" ");
		_number = Character.getNumericValue(tokens[1].charAt(1));
		_positions = Integer.parseInt(tokens[3]);
		_start = Integer.parseInt(tokens[11].replaceAll("\\.", ""));
	}

	/**
	 * Returns true if the slot is open by the time the capsule reaches this disc. The button is pressed at the given
	 * time.
	 */
	public boolean isOpen(int buttonTime) {
		int position = (getStart() + buttonTime + getNumber()) % getPositions();
		return (position == 0);
	}

	/**
	 * Accessor for the number
	 */
	private int getNumber() {
		return _number;
	}

	/**
	 * Accessor for the positions
	 */
	private int getPositions() {
		return _positions;
	}

	/**
	 * Accessor for the start
	 */
	private int getStart() {
		return _start;
	}
}