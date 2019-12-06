package buri.aoc.y19.d02;

/**
 * Representation of an opcode
 * 
 * @author Brian Uri!
 */
public enum Opcode {
	
	ADD(1, 3),
	MULTIPLY(2, 3),
	HALT(99, 0);	
	
	private int _code;
	private int _parameters;
	
	/**
	 * Constructor
	 */
	private Opcode(int code, int parameters) {
		_code = code;
		_parameters = parameters;
	}
	
	/**
	 * Looks up an opcode based on code
	 */
	public static Opcode getOpcodeFor(int code) {
		for (Opcode opcode : Opcode.values()) {
			if (code == opcode.getCode()) {
				return (opcode);
			}
		}
		return (null);
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

	/**
	 * Accessor for the number of parameters
	 */
	public int getParameters() {
		return _parameters;
	}
}
