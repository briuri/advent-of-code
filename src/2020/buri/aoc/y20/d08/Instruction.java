package buri.aoc.y20.d08;

/**
 * Data model for an instruction.
 *
 * @author Brian Uri!
 */
public class Instruction {
	private Opcode _opcode;
	private int _argument;

	/**
	 * Constructor
	 */
	public Instruction(String line) {
		this(line.split(" ")[0], Integer.valueOf(line.split(" ")[1]));
	}

	/**
	 * Data Constructor
	 */
	public Instruction(String opcode, int argument) {
		_opcode = Opcode.getOpcodeFor(opcode);
		_argument = argument;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (getOpcode() + " " + getArgument());
	}

	/**
	 * Accessor for the operation
	 */
	public Opcode getOpcode() {
		return _opcode;
	}

	/**
	 * Accessor for the argument
	 */
	public int getArgument() {
		return _argument;
	}
}