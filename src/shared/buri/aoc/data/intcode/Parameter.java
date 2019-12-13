package buri.aoc.data.intcode;

/**
 * Data model for a parameter.
 * - y19d2
 * - y19d5
 * - y19d7
 * - y19d9
 * - y19d11
 * - y19d13
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