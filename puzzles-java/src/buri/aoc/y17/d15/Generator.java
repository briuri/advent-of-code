package buri.aoc.y17.d15;

import buri.aoc.common.Part;

/**
 * The generators both work on the same principle. To create its next value, a generator will take the previous value it
 * produced, multiply it by a factor (generator A uses 16807; generator B uses 48271), and then keep the remainder of
 * dividing that resulting product by 2147483647. That final remainder is the value it produces next.
 *
 * @author Brian Uri!
 */
public class Generator {
	private final long _factor;
	private final long _multiples;
	private long _previousValue;

	private static final long DIVISOR = 2147483647;

	/**
	 * Factory method to construct generator A or B
	 */
	public static Generator getInstance(String name, long previousValue) {
		long factor = (name.equals("A") ? 16807 : 48271);
		long multiples = (name.equals("A") ? 4 : 8);
		return (new Generator(factor, multiples, previousValue));
	}

	/**
	 * Constructor
	 */
	public Generator(long factor, long multiples, long previousValue) {
		_factor = factor;
		_multiples = multiples;
		_previousValue = previousValue;
	}

	/**
	 * Computes the next value and returns the lowest 16 digits of its binary representation.
	 */
	public String nextValue(Part part) {
		long value;
		do {
			value = (getPreviousValue() * getFactor()) % DIVISOR;
			_previousValue = value;
		} while (part != Part.ONE && (value % getMultiples() != 0));
		StringBuilder binary = new StringBuilder(Long.toBinaryString(value));
		while (binary.length() < 16) {
			binary.insert(0, "0");
		}
		return (binary.substring(binary.length() - 16));
	}

	/**
	 * Accessor for the previousValue
	 */
	private long getPreviousValue() {
		return _previousValue;
	}

	/**
	 * Accessor for the factor
	 */
	private long getFactor() {
		return _factor;
	}

	/**
	 * Accessor for the multiples
	 */
	private long getMultiples() {
		return _multiples;
	}
}