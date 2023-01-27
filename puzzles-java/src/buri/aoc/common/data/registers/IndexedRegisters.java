package buri.aoc.common.data.registers;

import java.util.List;

/**
 * Registers used in:
 * - y18d16 (just the registers alone with no instructions)
 * - y18d19 (registers with instructions and string-based codes)
 * - y18d21 (registers with instructions and string-based codes and infinity potential)
 *
 * @author Brian Uri!
 */
public class IndexedRegisters {
	private final int[] _registers;

	// Associated instructions
	private int _ip;
	private int _ipRegister;
	private List<String> _codes;

	// For readability of the opcodes.
	public static final boolean REGISTER = false;
	public static final boolean VALUE = true;

	/**
	 * Constructor (y18d16)
	 */
	public IndexedRegisters(int size) {
		_registers = new int[size];
	}

	/**
	 * Constructor with instructions (y18d19, d21)
	 */
	public IndexedRegisters(int ipRegister, List<String> codes) {
		this(6);
		_ipRegister = ipRegister;
		_codes = codes;
		reset();
	}

	/**
	 * Clears all registers and resets pointers.
	 */
	public void reset() {
		setIp(0);
		for (int i = 0; i < getRegisters().length; i++) {
			set(i, 0);
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
	protected void set(int index, int value) {
		getRegisters()[index] = value;
	}

	/**
	 * Day 19 / 21:
	 * When the instruction pointer is bound to a register:
	 * - its value is written to that register just before each instruction is executed
	 * - the value of that register is written back to the instruction pointer immediately after each instruction
	 * finishes execution.
	 * - Afterward, move to the next instruction by adding one to the instruction pointer, even if the value in the
	 * instruction pointer was just updated by an instruction.
	 *
	 * In Day 21, this might run forever with the wrong initialZeroValue value.
	 */
	public void run(int initialZeroValue) {
		set(0, initialZeroValue);
		while (getIp() >= 0 && getIp() < getCodes().size()) {
			String code = getCodes().get(getIp());
			set(getIpRegister(), getIp());
			runStringCode(code.split(" "));
			setIp(get(REGISTER, getIpRegister()) + 1);
		}
	}

	/**
	 * Outputs the instruction data as pseudocode.
	 */
	public static void convertInput(List<String> input) {
		for (int i = 0; i < input.size(); i++) {
			if (i == 0) {
				System.out.println("Address Register is reg[" + input.get(0).split(" ")[1] + "]");
			}
			else {
				int address = i - 1;
				String[] code = input.get(i).split(" ");
				String opcode = code[0];
				int a = Integer.parseInt(code[1]);
				int b = Integer.parseInt(code[2]);
				int c = Integer.parseInt(code[3]);
				switch (opcode) {
					case "addr":
						System.out.printf("%d\treg[%d] = reg[%d] + reg[%d]%n", address, c, a, b);
						break;
					case "addi":
						System.out.printf("%d\treg[%d] = reg[%d] + %d%n", address, c, a, b);
						break;
					case "mulr":
						System.out.printf("%d\treg[%d] = reg[%d] * reg[%d]%n", address, c, a, b);
						break;
					case "muli":
						System.out.printf("%d\treg[%d] = reg[%d] * %d%n", address, c, a, b);
						break;
					case "banr":
						System.out.printf("%d\treg[%d] = reg[%d] & reg[%d]%n", address, c, a, b);
						break;
					case "bani":
						System.out.printf("%d\treg[%d] = reg[%d] & %d%n", address, c, a, b);
						break;
					case "borr":
						System.out.printf("%d\treg[%d] = reg[%d] | reg[%d]%n", address, c, a, b);
						break;
					case "bori":
						System.out.printf("%d\treg[%d] = reg[%d] | %d%n", address, c, a, b);
						break;
					case "setr":
						System.out.printf("%d\treg[%d] = reg[%d]%n", address, c, a);
						break;
					case "seti":
						System.out.printf("%d\treg[%d] = %d%n", address, c, a);
						break;
					case "gtir":
						System.out.printf("%d\treg[%d] = (%d > reg[%d] ? 1 : 0)%n", address, c, a, b);
						break;
					case "gtri":
						System.out.printf("%d\treg[%d] = (reg[%d] > %d ? 1 : 0)%n", address, c, a, b);
						break;
					case "gtrr":
						System.out.printf("%d\treg[%d] = (reg[%d] > reg[%d] ? 1 : 0)%n", address, c, a, b);
						break;
					case "eqir":
						System.out.printf("%d\treg[%d] = (%d == reg[%d] ? 1 : 0)%n", address, c, a, b);
						break;
					case "eqri":
						System.out.printf("%d\treg[%d] = (reg[%d] == %d ? 1 : 0)%n", address, c, a, b);
						break;
					case "eqrr":
						System.out.printf("%d\treg[%d] = (reg[%d] == reg[%d] ? 1 : 0)%n", address, c, a, b);
						break;
				}
			}
		}
	}

	/**
	 * Executes an actual instruction against the registers, based on data explored in Day 16.
	 */
	public void runIntCode(String[] code) {
		int opcode = Integer.parseInt(code[0]);
		int a = Integer.parseInt(code[1]);
		int b = Integer.parseInt(code[2]);
		int c = Integer.parseInt(code[3]);
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
	 * Converts string opcodes into the integer values found on Day 16.
	 */
	public void runStringCode(String[] code) {
		String opcode = code[0];
		switch (opcode) {
			case "mulr":
				code[0] = String.valueOf(0);
				break;
			case "eqri":
				code[0] = String.valueOf(1);
				break;
			case "setr":
				code[0] = String.valueOf(2);
				break;
			case "eqrr":
				code[0] = String.valueOf(3);
				break;
			case "gtrr":
				code[0] = String.valueOf(4);
				break;
			case "muli":
				code[0] = String.valueOf(5);
				break;
			case "borr":
				code[0] = String.valueOf(6);
				break;
			case "bani":
				code[0] = String.valueOf(7);
				break;
			case "addr":
				code[0] = String.valueOf(8);
				break;
			case "banr":
				code[0] = String.valueOf(9);
				break;
			case "eqir":
				code[0] = String.valueOf(10);
				break;
			case "gtir":
				code[0] = String.valueOf(11);
				break;
			case "addi":
				code[0] = String.valueOf(12);
				break;
			case "gtri":
				code[0] = String.valueOf(13);
				break;
			case "seti":
				code[0] = String.valueOf(14);
				break;
			case "bori":
				code[0] = String.valueOf(15);
				break;
		}
		runIntCode(code);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i : getRegisters()) {
			builder.append(i).append(" ");
		}
		return (builder.toString().trim());
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
	protected int[] getRegisters() {
		return _registers;
	}

	/**
	 * Accessor for the instruction pointer
	 */
	protected int getIp() {
		return _ip;
	}

	/**
	 * Accessor for the instruction pointer
	 */
	protected void setIp(int ip) {
		_ip = ip;
	}

	/**
	 * Accessor for the ipRegister
	 */
	protected int getIpRegister() {
		return _ipRegister;
	}

	/**
	 * Accessor for the codes
	 */
	protected List<String> getCodes() {
		return _codes;
	}
}