package buri.aoc.data.intcode;

/**
 * Representation of an opcode
 * - y19d2, 5, 7, 9, 11, 13, 15, 17, 19,21, 23
 * 
 * @author Brian Uri!
 */
public enum Opcode {
	ADD(1, 3),
	MULTIPLY(2, 3),
	SAVE(3, 1),
	OUTPUT(4, 1),
	JUMP_IF_TRUE(5, 2),
	JUMP_IF_FALSE(6, 2),
	LESS_THAN(7, 3),
	EQUALS(8, 3),
	RELATIVE_BASE_OFFSET(9, 1),
	HALT(99, 0);	
	
	private int _code;
	private int _numParameters;
	
	/**
	 * Constructor
	 */
	private Opcode(int code, int numParameters) {
		_code = code;
		_numParameters = numParameters;
	}
	
	/**
	 * Looks up an opcode based on code
	 */
	public static Opcode getOpcodeFor(String fullOpcode) {
		// Handle classic opcodes with no parameter modes prefixed.
		int codeStart = Math.max(0, fullOpcode.length() - 2);
		int code = Integer.valueOf(fullOpcode.substring(codeStart));
		for (Opcode opcode : Opcode.values()) {
			if (code == opcode.getCode()) {
				return (opcode);
			}
		}
		throw new RuntimeException("No opcode for " + fullOpcode);
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
	public int getNumParameters() {
		return _numParameters;
	}
}
