package buri.aoc.y17.d23;

import buri.aoc.common.data.registers.NamedRegisters;

import java.util.List;

/**
 * Abstract base class for registers in each part.
 *
 * @author Brian Uri!
 */
public class Registers extends NamedRegisters {
	private int _multiplyCount;

	/**
	 * Constructor
	 */
	public Registers(List<String> instructions) {
		super(instructions);
		for (char name = 'a'; name <= 'h'; name++) {
			set(String.valueOf(name), 0L);
		}
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
		while (isWithinInstructions()) {
			String[] tokens = getInstructions().get(getCurrent()).split(" ");
			if (tokens[0].equals("set")) {
				long value = getRegisterOrValue(tokens[2]);
				getRegisters().put(tokens[1], value);
			}
			if (tokens[0].equals("sub")) {
				long originalValue = get(tokens[1]);
				long subValue = getRegisterOrValue(tokens[2]);
				getRegisters().put(tokens[1], originalValue - subValue);
			}
			if (tokens[0].equals("mul")) {
				long originalValue = get(tokens[1]);
				long multiplyValue = getRegisterOrValue(tokens[2]);
				getRegisters().put(tokens[1], originalValue * multiplyValue);
				setMultiplyCount(getMultiplyCount() + 1);
			}
			if (tokens[0].equals("jnz")) {
				long value = getRegisterOrValue(tokens[1]);
				if (value != 0) {
					setCurrent(getCurrent() + getRegisterOrValue(tokens[2]).intValue());
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