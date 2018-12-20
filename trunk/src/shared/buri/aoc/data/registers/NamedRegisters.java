package buri.aoc.data.registers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for a set of named registers containing longs.
 * 
 * @author Brian Uri!
 */
public class NamedRegisters {
	private Map<String, Long> _registers;
	private List<String> _instructions;
	private int _current = 0;
	
	/**
	 * Constructor
	 */
	protected NamedRegisters(List<String> instructions) {
		_instructions = instructions;
		_registers = new HashMap<String, Long>();
		setCurrent(0);
	}
		
	/**
	 * Returns the long value or (if a string), the value in that register.
	 */
	protected Long getRegisterOrValue(String parameter) {
		if (parameter.matches("[a-z]")) {
			return (get(parameter));
		}
		return (Long.valueOf(parameter));
	}
	
	/**
	 * Lazy-loads a value from a register. Initializes at zero if the register does not yet exist.
	 */
	protected Long get(String value) {
		if (getRegisters().get(value) == null) {
			set(value, 0L);
		}
		return (getRegisters().get(value));
	}

	/**
	 * Sets a register.
	 */
	protected void set(String register, Long value) {
		getRegisters().put(register, value);
	}
	
	/**
	 * Updates the register's value by some amount.
	 */
	protected void add(String register, Long increment) {
		Long newValue = get(register) + increment;
		getRegisters().put(register, newValue);
	}

	/**
	 * Returns true if the current marker is within the bounds of the input instructions.
	 */
	protected boolean isWithinInstructions() {
		return (getCurrent() >= 0 && getCurrent() < getInstructions().size());
	}
	
	/**
	 * Accessor for the registers
	 */
	protected Map<String, Long> getRegisters() {
		return _registers;
	}
	
	/**
	 * Accessor for the instructions
	 */
	protected List<String> getInstructions() {
		return _instructions;
	}

	/**
	 * Accessor for the current instruction
	 */
	protected int getCurrent() {
		return _current;
	}

	/**
	 * Accessor for the current instruction
	 */
	protected void setCurrent(int current) {
		_current = current;
	}
}
