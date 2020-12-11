package buri.aoc.y20.d08;

/**
 * Representation of an opcode
 *
 * @author Brian Uri!
 */
public enum Opcode {
	ACC("acc"), JMP("jmp"), NOP("nop");

	private String _code;

	/**
	 * Constructor
	 */
	private Opcode(String code) {
		_code = code;
	}

	/**
	 * Looks up an opcode based on code
	 */
	public static Opcode getOpcodeFor(String code) {
		for (Opcode opcode : Opcode.values()) {
			if (code.equals(opcode.getCode())) {
				return (opcode);
			}
		}
		throw new RuntimeException("No opcode for " + code);
	}

	@Override
	public String toString() {
		return (getCode());
	}

	/**
	 * Accessor for the code
	 */
	public String getCode() {
		return _code;
	}
}
