package buri.aoc.data.intcode;

/**
 * Representation of a parameter mode
 * - y19d2
 * - y19d5
 * - y19d7
 * - y19d9
 * 
 * @author Brian Uri!
 */
public enum Mode {
	POSITIONAL(0), IMMEDIATE(1), RELATIVE(2);

	private int _code;

	/**
	 * Constructor
	 */
	private Mode(int code) {
		_code = code;
	}

	/**
	 * Looks up a mode based on code
	 */
	public static Mode getModeFor(int code) {
		for (Mode mode : Mode.values()) {
			if (code == mode.getCode()) {
				return (mode);
			}
		}
		throw new RuntimeException("No mode for " + code);
	}

	@Override
	public String toString() {
		return (String.valueOf(getCode()));
	}

	/**
	 * Accessor for the code
	 */
	public int getCode() {
		return _code;
	}
}