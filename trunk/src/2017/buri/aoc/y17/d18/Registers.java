package buri.aoc.y17.d18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of the string-named registers containing integer values. Initialized at 0.
 * 
 * @author Brian Uri!
 */
public class Registers {

	private Map<String, Long> _registers;

	/**
	 * Constructor
	 */
	public Registers() {
		_registers = new HashMap<String, Long>();
	}

	/**
	 * Executes a sequence of instructions.
	 * 
	 * snd X plays a sound with a frequency equal to the value of X.
	 * set X Y sets register X to the value of Y.
	 * add X Y increases register X by the value of Y.
	 * mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
	 * mod X Y sets register X to the remainder of dividing the value contained in register X by the value of Y (that
	 * 		is, it sets X to the result of X modulo Y).
	 * rcv X recovers the frequency of the last sound played, but only when the value of X is not zero. (If it is zero,
	 * 		the command does nothing.)
	 * jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero. (An offset of 2
	 * 		skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
	 * 
	 * Many of the instructions can take either a register (a single letter) or a number. The value of a register is the
	 * integer it contains; the value of a number is that number.
	 * 
	 * After each jump instruction, the program continues with the instruction to which the jump jumped. After any other
	 * instruction, the program continues with the next instruction. Continuing (or jumping) off either end of the
	 * program terminates it.
	 */
	public void process(List<String> instructions) {
		int current = 0;
		while (true) {
			String[] tokens = instructions.get(current).split(" ");
			if (tokens[0].equals("snd")) {
				getRegisters().put("lastFrequency", getParameter(tokens[1]));
			}
			if (tokens[0].equals("set")) {
				long value = getParameter(tokens[2]);
				getRegisters().put(tokens[1], value);
			}
			if (tokens[0].equals("add")) {
				long originalValue = getValue(tokens[1]);
				long addValue = getParameter(tokens[2]);
				getRegisters().put(tokens[1], originalValue + addValue);
			}
			if (tokens[0].equals("mul")) {
				long originalValue = getValue(tokens[1]);
				long multiplyValue = getParameter(tokens[2]);
				getRegisters().put(tokens[1], originalValue * multiplyValue);
			}
			if (tokens[0].equals("mod")) {
				long originalValue = getValue(tokens[1]);
				long modValue = getParameter(tokens[2]);
				getRegisters().put(tokens[1], originalValue % modValue);
			}
			if (tokens[0].equals("rcv")) {
				long value = getParameter(tokens[1]);
				if (value > 0) {
					long lastFrequency = getValue("lastFrequency");
					getRegisters().put("lastReceivedFrequency", lastFrequency);
					if (getValue("firstReceivedFrequency") == 0) {
						getRegisters().put("firstReceivedFrequency", lastFrequency);
						break;
					}
				}
			}
			if (tokens[0].equals("jgz")) {
				long value = getParameter(tokens[1]);
				if (value > 0) {
					current += getParameter(tokens[2]);
				}
				else {
					current++;
				}
			}
			else {
				current++;
			}
			if (current < 0 || current >= instructions.size()) {
				break;
			}
		}
	}

	public Long getFrequencyAfterFirstReceive() {
		return (getValue("firstReceivedFrequency"));
	}
	
	/**
	 * Returns the integer value or (if a string), the value in that register.
	 */
	private Long getParameter(String parameter) {
		if (parameter.matches("[a-z]")) {
			return (getValue(parameter));
		}
		return (Long.valueOf(parameter));
	}
	
	/**
	 * Lazy-loads a value from a register. Initializes at zero if the register does not yet exist.
	 */
	private Long getValue(String register) {
		if (getRegisters().get(register) == null) {
			getRegisters().put(register, 0L);
		}
		return (getRegisters().get(register));
	}

	/**
	 * Accessor for the registers
	 */
	private Map<String, Long> getRegisters() {
		return _registers;
	}
}