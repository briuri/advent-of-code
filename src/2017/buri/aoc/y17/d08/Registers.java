package buri.aoc.y17.d08;

import java.util.Collections;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.data.registers.NamedRegisters;

/**
 * Representation of the string-named registers containing integer values. Initialized at 0.
 * 
 * @author Brian Uri!
 */
public class Registers extends NamedRegisters {
	private Long _largestValue = 0L;

	/**
	 * Constructor
	 */
	public Registers() {
		super(Collections.EMPTY_LIST);
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
				add(instruction.getTargetRegister(), instruction.getTargetIncrement());
			}
		}
	}

	/**
	 * Returns the largest value in the registers.
	 */
	public Long getLargestValue(Part part) {
		if (part == Part.ONE) {
			return (Collections.max(getRegisters().values()));
		}
		return (getLargestValue());
	}

	/**
	 * Checks if a condition is satisfied.
	 */
	private boolean isConditionTrue(String register, RegisterInstruction.Conditional operator, int testValue) {
		Long value = get(register);
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
			default: // GREATER
				return (value > testValue);
		}
	}

	/**
	 * Updates the register's value by some amount.
	 */
	protected void add(String register, Long increment) {
		super.add(register, increment);
		setLargestValue(Math.max(getLargestValue(), get(register)));
	}

	/**
	 * Accessor for the largest value ever held.
	 */
	private Long getLargestValue() {
		return _largestValue;
	}

	/**
	 * Accessor for the largestValue
	 */
	private void setLargestValue(Long largestValue) {
		_largestValue = largestValue;
	}
}