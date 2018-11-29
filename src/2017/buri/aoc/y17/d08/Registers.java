package buri.aoc.y17.d08;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.y17.Part;

/**
 * Day08
 * 
 * Representation of the string-named registers containing integer values. Initialized at 0.
 * 
 * @author Brian Uri!
 */
public class Registers {

	private Map<String, Integer> _registers;
	private Integer _largestValue = 0;
	
	/**
	 * Constructor
	 */
	public Registers() {
		_registers = new HashMap<String, Integer>();
	}

	/**
	 * Executes a sequence of instructions.
	 * 
	 * Each instruction consists of several parts: the register to modify, whether to increase or decrease that
	 * register's value, the amount by which to increase or decrease it, and a condition. If the condition fails, skip
	 * the instruction without modifying the register.
	 */
	public void process(List<RegisterInstruction> instructions) {
		for (RegisterInstruction instruction : instructions) {
			if (isConditionTrue(instruction.getConditionRegister(), instruction.getConditional(),
				instruction.getConditionValue())) {
				incrementRegister(instruction.getTargetRegister(), instruction.getTargetIncrement());
			}
		}
	}

	/**
	 * Returns the largest value in the registers.
	 */
	public Integer getLargestValue(Part part) {
		if (part == Part.ONE) {
			Integer value = 0;
			for (Integer testValue : getRegisters().values()) {
				value = Math.max(value, testValue);
			}
			return (value);
		}
		return (getLargestValue());
	}
	
	/**
	 * Checks if a condition is satisfied.
	 */
	private boolean isConditionTrue(String register, RegisterInstruction.Conditional operator, int testValue) {
		Integer value = getValue(register);
		switch (operator) {
			case LESS:
				return (value < testValue);
			case LESS_OR_EQUAL:
				return (value <= testValue);
			case EQUAL:
				return (value == testValue);
			case NOT_EQUAL:
				return (value != testValue);
			case GREATER_OR_EQUAL:
				return (value >= testValue);
			case GREATER:
				return (value > testValue);
			default:
				throw new IllegalArgumentException("Unknown conditional operator.");
		}
	}

	/**
	 * Updates the register's value by some amount.
	 */
	private void incrementRegister(String register, Integer increment) {
		Integer newValue = getValue(register) + increment;
		getRegisters().put(register, newValue);
		_largestValue = Math.max(getLargestValue(), newValue);
	}

	/**
	 * Lazy-loads a value from a register. Initializes at zero if the register does not yet exist.
	 */
	private Integer getValue(String register) {
		if (getRegisters().get(register) == null) {
			getRegisters().put(register, 0);
		}
		return (getRegisters().get(register));
	}

	/**
	 * Accessor for the registers
	 */
	private Map<String, Integer> getRegisters() {
		return _registers;
	}

	/**
	 * Accessor for the largest value ever held.
	 */
	public Integer getLargestValue() {
		return _largestValue;
	}
}