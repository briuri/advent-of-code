package buri.aoc.y20.d23;

/**
 * Data model for a cup that knows its clockwise neighbor. Neighbor pointers are mutable.
 *
 * @author Brian Uri!
 */
public class Cup {
	int _value;
	private Cup _next;

	/**
	 * Constructor
	 */
	public Cup(int value) {
		_value = value;
	}

	/**
	 * Accessor for the value
	 */
	public int getValue() {
		return _value;
	}

	/**
	 * Accessor for the cup to the right
	 */
	public Cup getNext() {
		return _next;
	}

	/**
	 * Accessor for the cup to the right
	 */
	public void setNext(Cup next) {
		_next = next;
	}
}