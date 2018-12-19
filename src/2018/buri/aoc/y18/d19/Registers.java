package buri.aoc.y18.d19;

import java.util.List;

import buri.aoc.Part;

/**
 * Representation of the 5 registers containing integer values.
 * 
 * @author Brian Uri!
 */
public class Registers {
	private int _ip;
	private int _ipRegister;
	private int[] _registers;
	private List<String> _codes;

	// For readability of the opcodes.
	public static final boolean REGISTER = false;
	public static final boolean VALUE = true;

	/**
	 * String-based Constructor
	 */
	public Registers(Part part, int ipRegister, List<String> codes) {
		_ip = 0;
		_ipRegister = ipRegister;
		_registers = new int[6];
		_codes = codes;
		for (int i = 0; i < getRegisters().length; i++) {
			set(i, 0);
		}
		if (part == Part.TWO) {
			set(0, 1);
		}
	}

	/**
	 * When the instruction pointer is bound to a register:
	 * - its value is written to that register just before each instruction is executed
	 * - the value of that register is written back to the instruction pointer immediately after each instruction
	 * finishes execution.
	 * - Afterward, move to the next instruction by adding one to the instruction pointer, even if the value in the
	 * instruction pointer was just updated by an instruction.
	 */
	public void run() {
		while (getIp() >= 0 && getIp() < getCodes().size()) {
			String code = getCodes().get(getIp());
			set(getIpRegister(), getIp());
			runCode(code.split(" "));
			setIp((int) get(Registers.REGISTER, getIpRegister()) + 1);
		}
	}

	/**
	 * Executes an actual instruction against the registers.
	 */
	public void runCode(String[] code) {
		String opcode = code[0];
		int a = Integer.valueOf(code[1]);
		int b = Integer.valueOf(code[2]);
		int c = Integer.valueOf(code[3]);
		if (opcode.equals("mulr")) {
			set(c, get(REGISTER, a) * get(REGISTER, b));
		}
		else if (opcode.equals("eqri")) {
			set(c, (get(REGISTER, a) == get(VALUE, b) ? 1 : 0));
		}
		else if (opcode.equals("setr")) {
			set(c, get(REGISTER, a));
		}
		else if (opcode.equals("eqrr")) {
			set(c, (get(REGISTER, a) == get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode.equals("gtrr")) {
			set(c, (get(REGISTER, a) > get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode.equals("muli")) {
			set(c, get(REGISTER, a) * get(VALUE, b));
		}
		else if (opcode.equals("borr")) {
			set(c, get(REGISTER, a) | get(REGISTER, b));
		}
		else if (opcode.equals("bani")) {
			set(c, get(REGISTER, a) & get(VALUE, b));
		}
		else if (opcode.equals("addr")) {
			set(c, get(REGISTER, a) + get(REGISTER, b));
		}
		else if (opcode.equals("banr")) {
			set(c, get(REGISTER, a) & get(REGISTER, b));
		}
		else if (opcode.equals("eqir")) {
			set(c, (get(VALUE, a) == get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode.equals("gtir")) {
			set(c, (get(VALUE, a) > get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode.equals("addi")) {
			set(c, get(REGISTER, a) + get(VALUE, b));
		}
		else if (opcode.equals("gtri")) {
			set(c, (get(REGISTER, a) > get(VALUE, b) ? 1 : 0));
		}
		else if (opcode.equals("seti")) {
			set(c, get(VALUE, a));
		}
		else if (opcode.equals("bori")) {
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
	 * Accessor for the instruction pointer
	 */
	private int getIp() {
		return _ip;
	}

	/**
	 * Accessor for the instruction pointer
	 */
	private void setIp(int ip) {
		_ip = ip;
	}

	/**
	 * Accessor for the ipRegister
	 */
	private int getIpRegister() {
		return _ipRegister;
	}

	/**
	 * Accessor for the registers
	 */
	private int[] getRegisters() {
		return _registers;
	}

	/**
	 * Accessor for the codes
	 */
	private List<String> getCodes() {
		return _codes;
	}
}