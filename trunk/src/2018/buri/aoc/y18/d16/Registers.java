package buri.aoc.y18.d16;

/**
 * Representation of the 4 registers containing integer values.
 * 
 * @author Brian Uri!
 */
public class Registers {
	private int[] _registers;

	// For readability of the opcodes.
	public static final boolean REGISTER = false;
	public static final boolean VALUE = true;

	/**
	 * Constructor for Part 2, with registers initialized to zero.
	 */
	public Registers() {
		this("0, 0, 0, 0");
	}

	/**
	 * String-based Constructor
	 */
	public Registers(String values) {
		String[] tokens = values.split(",");
		_registers = new int[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			_registers[i] = Integer.valueOf(tokens[i].trim());
		}
	}

	/**
	 * Copy constructor
	 */
	private Registers(int[] registers) {
		_registers = new int[registers.length];
		System.arraycopy(registers, 0, _registers, 0, registers.length);
	}

	/**
	 * Creates a copy of a register that can be modified without affecting the original.
	 */
	private Registers copy() {
		return (new Registers(getRegisters()));
	}

	/**
	 * Tries all possible opcodes using the a,b,c values to determine how many possible matches there are. Compares
	 * result of operation against the expected Registers to identify a match.
	 */
	public int tryCode(String[] code, Registers expected) {
		int numCodes = 16;
		int matches = 0;

		// Test each of the 15 opcodes.
		for (int i = 0; i < numCodes; i++) {
			Registers copy = copy();
			code[0] = String.valueOf(i);
			copy.runCode(code);
			if (expected.equals(copy)) {
				matches++;
			}
		}
		if (matches == 1) {
			/**
			 * To map numbers to the actual text codes, I ran this tryCode() method and printed out the codes with just
			 * 1 match. I then removed that match from the pool of possible codes to test (causing instructions with 2
			 * matches to now have 1, and so forth) and iteratively reran until I had all the codes.
			 * 
			 * I was then able to write runCode() and finally refactored tryCode() to use runCode().
			 */
		}
		return (matches);
	}

	/**
	 * Executes an actual instruction against the registers.
	 */
	public void runCode(String[] code) {
		int opcode = Integer.valueOf(code[0]);
		int a = Integer.valueOf(code[1]);
		int b = Integer.valueOf(code[2]);
		int c = Integer.valueOf(code[3]);
		if (opcode == 0) { // mulr
			set(c, get(REGISTER, a) * get(REGISTER, b));
		}
		else if (opcode == 1) { // eqri
			set(c, (get(REGISTER, a) == get(VALUE, b) ? 1 : 0));
		}
		else if (opcode == 2) { // setr
			set(c, get(REGISTER, a));
		}
		else if (opcode == 3) {// eqrr
			set(c, (get(REGISTER, a) == get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode == 4) {// gtrr
			set(c, (get(REGISTER, a) > get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode == 5) {// muli
			set(c, get(REGISTER, a) * get(VALUE, b));
		}
		else if (opcode == 6) {// borr
			set(c, get(REGISTER, a) | get(REGISTER, b));
		}
		else if (opcode == 7) {// bani
			set(c, get(REGISTER, a) & get(VALUE, b));
		}
		else if (opcode == 8) {// addr
			set(c, get(REGISTER, a) + get(REGISTER, b));
		}
		else if (opcode == 9) {// banr
			set(c, get(REGISTER, a) & get(REGISTER, b));
		}
		else if (opcode == 10) {// eqir
			set(c, (get(VALUE, a) == get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode == 11) {// gtir
			set(c, (get(VALUE, a) > get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode == 12) {// addi
			set(c, get(REGISTER, a) + get(VALUE, b));
		}
		else if (opcode == 13) {// gtri
			set(c, (get(REGISTER, a) > get(VALUE, b) ? 1 : 0));
		}
		else if (opcode == 14) {// seti
			set(c, get(VALUE, a));
		}
		else if (opcode == 15) {// bori
			set(c, get(REGISTER, a) | get(VALUE, b));
		}
	}

	/**
	 * Returns either the value itself (immediate/value) or the value from the register with that index (register).
	 */
	public int get(boolean isImmediate, int value) {
		return (isImmediate ? value : getRegisters()[value]);
	}

	/**
	 * Updates the value in a register.
	 */
	private void set(int index, int value) {
		getRegisters()[index] = value;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i : getRegisters()) {
			buffer.append(i).append(" ");
		}
		return (buffer.toString().trim());
	}

	/**
	 * String-based equality used to quickly compare register states.
	 */
	@Override
	public boolean equals(Object obj) {
		return (toString().equals(obj.toString()));
	}

	/**
	 * Accessor for the registers
	 */
	private int[] getRegisters() {
		return _registers;
	}
}