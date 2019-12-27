package buri.aoc.y19.intcode;

/**
 * Data model for a parameter.
 * - y19d2, 5, 7, 9, 11, 13, 15, 17, 19,21, 23, 25
 * 
 * @author Brian Uri!
 */
public class Parameter {
	private long _value;
	private Mode _mode;
	
	/**
	 * Constructor
	 */
	public Parameter(long value, Mode mode) {
		_value = value;
		_mode = mode;
	}

	@Override
	public String toString() {
		if (getMode() == Mode.POSITIONAL) {
			return (String.format("p[%d]", getValue()));
		}
		else if (getMode() == Mode.IMMEDIATE) {
			return (String.valueOf(getValue()));
		}
		// RELATIVE
		return (String.format("p[%d + rB]", getValue()));
	}

	/**
	 * Accessor for the value
	 */
	public long getValue() {
		return _value;
	}

	/**
	 * Accessor for the mode
	 */
	public Mode getMode() {
		return (_mode);
	}
}