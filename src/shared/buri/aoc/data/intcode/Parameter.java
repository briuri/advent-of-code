package buri.aoc.data.intcode;

/**
 * Data model for a parameter.
 * - y19d2
 * - y19d5
 * - y19d7
 * 
 * @author Brian Uri!
 */
public class Parameter {
	private int _value;
	private int _mode;

	/**
	 * Constructor
	 */
	public Parameter(int value, int mode) {
		_value = value;
		_mode = mode;
	}

	@Override
	public String toString() {
		String output = isPositional() ? String.format("p[%d]", getValue()) : String.valueOf(getValue());
		return (output);
	}

	/**
	 * Accessor for the value
	 */
	public int getValue() {
		return _value;
	}

	/**
	 * Accessor for the mode
	 */
	public boolean isPositional() {
		return (_mode == 0);
	}
	
	/**
	 * Accessor for the mode
	 */
	public boolean isImmediate() {
		return (!isPositional());
	}
}