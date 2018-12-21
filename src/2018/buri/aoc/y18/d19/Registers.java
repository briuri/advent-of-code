package buri.aoc.y18.d19;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.data.registers.IndexedRegisters;

/**
 * Representation of the 5 registers containing integer values.
 * 
 * @author Brian Uri!
 */
public class Registers extends IndexedRegisters {
	private int _ip;
	private int _ipRegister;
	private List<String> _codes;

	/**
	 * String-based Constructor
	 */
	public Registers(Part part, int ipRegister, List<String> codes) {
		super(6);
		_ip = 0;
		_ipRegister = ipRegister;
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
			runStringCode(code.split(" "));
			setIp((int) get(Registers.REGISTER, getIpRegister()) + 1);
		}
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
	 * Accessor for the codes
	 */
	private List<String> getCodes() {
		return _codes;
	}
}