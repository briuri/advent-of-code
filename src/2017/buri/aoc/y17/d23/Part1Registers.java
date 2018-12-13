package buri.aoc.y17.d23;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class for registers in each part.
 * 
 * @author Brian Uri!
 */
public class Part1Registers {
	private int _current = 0;
	private Map<String, Long> _registers;
	private List<String> _instructions;
	private int _multiplyCount;

	/**
	 * Constructor
	 */
	public Part1Registers(List<String> instructions) {
		_registers = new HashMap<String, Long>();
		_instructions = instructions;
		for (char name = 'a'; name <= 'h'; name++) {
			_registers.put(String.valueOf(name), 0L);
		}
		setCurrent(0);
	}

	/**
	 * Executes a sequence of instructions.
	 * 
	 * set X Y sets register X to the value of Y.
	 * sub X Y decreases register X by the value of Y.
	 * mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
	 * jnz X Y jumps with an offset of the value of Y, but only if the value of X is not zero. (An offset of 2 skips the
	 * next instruction, an offset of -1 jumps to the previous instruction, and so on.)
	 */
	public void process() {
		while (true) {
			if (!isWithinInstructions()) {
				break;
			}
			String[] tokens = getInstructions().get(getCurrent()).split(" ");
			if (tokens[0].equals("set")) {
				long value = getParameter(tokens[2]);
				getRegisters().put(tokens[1], value);
			}
			if (tokens[0].equals("sub")) {
				long originalValue = getValue(tokens[1]);
				long subValue = getParameter(tokens[2]);
				getRegisters().put(tokens[1], originalValue - subValue);
			}
			if (tokens[0].equals("mul")) {
				long originalValue = getValue(tokens[1]);
				long multiplyValue = getParameter(tokens[2]);
				getRegisters().put(tokens[1], originalValue * multiplyValue);
				setMultiplyCount(getMultiplyCount() + 1);
			}
			if (tokens[0].equals("jnz")) {
				long value = getParameter(tokens[1]);
				if (value != 0) {
					setCurrent(getCurrent() + getParameter(tokens[2]).intValue());
				}
				else {
					setCurrent(getCurrent() + 1);
				}
			}
			else {
				setCurrent(getCurrent() + 1);
			}
		}
	}

	/**
	 * Returns true if the current marker is within the bounds of the input instructions.
	 */
	private boolean isWithinInstructions() {
		return (getCurrent() >= 0 && getCurrent() < getInstructions().size());
	}

	/**
	 * Returns the integer value or (if a string), the value in that register.
	 */
	private Long getParameter(String parameter) {
		if (parameter.matches("[a-h]")) {
			return (getValue(parameter));
		}
		return (Long.valueOf(parameter));
	}

	/**
	 * Loads a value from a register.
	 */
	public Long getValue(String register) {
		return (getRegisters().get(register));
	}

	/**
	 * Accessor for the instructions
	 */
	private List<String> getInstructions() {
		return _instructions;
	}

	/**
	 * Accessor for the registers
	 */
	protected Map<String, Long> getRegisters() {
		return _registers;
	}

	/**
	 * Accessor for the current instruction
	 */
	private int getCurrent() {
		return _current;
	}

	/**
	 * Accessor for the current instruction
	 */
	private void setCurrent(int current) {
		_current = current;
	}

	/**
	 * Accessor for the multiplyCount
	 */
	public int getMultiplyCount() {
		return _multiplyCount;
	}

	/**
	 * Accessor for the multiplyCount
	 */
	private void setMultiplyCount(int multiplyCount) {
		_multiplyCount = multiplyCount;
	}
}