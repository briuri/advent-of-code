package buri.aoc.data.intcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class used for running Intcode programs.
 * - y19d2, 5, 7, 9, 11, 13, 15, 17
 * 
 * @author Brian Uri!
 */
public class Computer {
	private List<Long> _memory;
	private List<Long> _inputs;
	private List<Long> _outputs;
	private long _pointer;
	private long _relativeBase;
	private boolean _debug = false;

	private static final String REDUCES_TO = "  -->  ";

	/**
	 * Base constructor
	 */
	public Computer(List<Long> program) {
		_memory = new ArrayList<>(program);
		_inputs = new ArrayList<>();
		_outputs = new ArrayList<>();
		setPointer(0);
		setRelativeBase(0);
	}
	
	/**
	 * Constructor (y19d2)
	 * 
	 * @param program the intcodes to run (does not modify)
	 * @param noun value to store at address 1
	 * @param verb value to store at address 2
	 */
	public Computer(List<Long> program, Long noun, Long verb) {
		this(program);
		_memory.set(1, noun);
		_memory.set(2, verb);
	}
	
	/**
	 * Constructor (y19d13, y10d17)
	 * 
	 * @param program the intcodes to run (does not modify)
	 * @param addressZero the value to set at address zero
	 */
	public Computer(List<Long> program, Long addressZero) {
		this(program);
		_memory.set(0, addressZero);
	}
	
	/**
	 * Runs the intcode program until a HALT opcode is hit.
	 */
	public void run() {
		String fullOpcode = String.valueOf(get(getPointer()));
		Opcode opcode = Opcode.getOpcodeFor(fullOpcode);
		while (opcode != Opcode.HALT) {
			// Handle classic opcodes with no parameter modes prefixed.
			int codeStart = Math.max(0, fullOpcode.length() - 2);

			// Build parameter modes for parsing.
			StringBuffer buffer = new StringBuffer();
			buffer.append(fullOpcode.substring(0, codeStart)).reverse();
			while (buffer.length() < opcode.getNumParameters()) {
				buffer.append("0");
			}
			String rawModes = buffer.toString();

			// Build parameters
			Parameter[] params = new Parameter[opcode.getNumParameters()];
			for (int i = 0; i < params.length; i++) {
				Mode mode = Mode.getModeFor(Character.getNumericValue(rawModes.charAt(i)));
				params[i] = new Parameter(get(getPointer() + i + 1), mode);
			}

			// Show raw instruction and resolution for debugging.
			log(fullOpcode, opcode, params);

			// Execute opcodes.
			boolean jumped = false;
			if (opcode == Opcode.ADD) {
				add(params);
			}
			else if (opcode == Opcode.MULTIPLY) {
				multiply(params);
			}
			else if (opcode == Opcode.SAVE) {
				if (isWaiting()) {
					break;
				}
				save(params);
			}
			else if (opcode == Opcode.OUTPUT) {
				output(params);
			}
			else if (opcode == Opcode.JUMP_IF_TRUE) {
				jumped = jumpIfTrue(params);
			}
			else if (opcode == Opcode.JUMP_IF_FALSE) {
				jumped = jumpIfFalse(params);
			}
			else if (opcode == Opcode.LESS_THAN) {
				lessThan(params);
			}
			else if (opcode == Opcode.EQUALS) {
				equals(params);
			}
			else if (opcode == Opcode.RELATIVE_BASE_OFFSET) {
				relativeBaseOffset(params); 
			}

			// Update instruction pointer.
			if (!jumped) {
				setPointer(getPointer() + 1 + opcode.getNumParameters());
			}
			fullOpcode = String.valueOf(get(getPointer()));
			opcode = Opcode.getOpcodeFor(fullOpcode);
		}
	}

	/**
	 * Returns true if no inputs are available.
	 */
	public boolean isWaiting() {
		return (getInputs().size() == 0);
	}

	/**
	 * Adds the first two values then stores the result at the last address.
	 */
	private void add(Parameter[] params) {
		long value = applyMode(params[0]) + applyMode(params[1]);
		set(params[2], value);
	}

	/**
	 * Multiplies the first two values then stores the result at the last address.
	 */
	private void multiply(Parameter[] params) {
		long value = applyMode(params[0]) * applyMode(params[1]);
		set(params[2], value);
	}

	/**
	 * Saves the input value to an address.
	 */
	private void save(Parameter[] params) {
		set(params[0], getInputs().remove(0));
	}

	/**
	 * Saves an output value.
	 */
	private void output(Parameter[] params) {
		getOutputs().add(applyMode(params[0]));
	}

	/**
	 * Jumps to a new instruction pointer if the first parameter is non-zero. Does nothing otherwise.
	 */
	private boolean jumpIfTrue(Parameter[] params) {
		boolean jumped = false;
		if (applyMode(params[0]) != 0) {
			setPointer(applyMode(params[1]));
			jumped = true;
		}
		return (jumped);
	}

	/**
	 * Jumps to a new instruction pointer if the first parameter is zero. Does nothing otherwise.
	 */
	private boolean jumpIfFalse(Parameter[] params) {
		boolean jumped = false;
		if (applyMode(params[0]) == 0) {
			setPointer(applyMode(params[1]));
			jumped = true;
		}
		return (jumped);
	}

	/**
	 * If first parameter is less than second, store 1 in third parameter's address. Otherwise, store 0.
	 */
	private void lessThan(Parameter[] params) {
		int value = (applyMode(params[0]) < applyMode(params[1])) ? 1 : 0;
		set(params[2], value);
	}

	/**
	 * If first parameter is equal to second, store 1 in third parameter's address. Otherwise, store 0.
	 */
	private void equals(Parameter[] params) {
		int value = (applyMode(params[0]) == applyMode(params[1])) ? 1 : 0;
		set(params[2], value);
	}

	/**
	 * Adjusts relative base by some amount.
	 */
	private void relativeBaseOffset(Parameter[] params) {
		setRelativeBase(getRelativeBase() + applyMode(params[0]));
	}
	
	/**
	 * Displays the raw instructions and their resolutions for debugging.
	 */
	private void log(String fullOpcode, Opcode opcode, Parameter[] params) {
		if (!isDebugEnabled()) {
			return;
		}
		// Generate pointer, relative base, and raw instruction.
		StringBuffer log = new StringBuffer();
		log.append(String.format("ip=%d, rB=%d", getPointer(), getRelativeBase()));
		while (log.length() < 20) {
			log.append(" ");
		}
		log.append(fullOpcode);
		for (int i = 0; i < params.length; i++) {
			log.append(",").append(params[i].getValue());
		}
		while (log.length() < 50) {
			log.append(" ");
		}

		// Generate human-readable instructions.
		if (opcode == Opcode.ADD) {
			String showWork = "%s = %s + %s";
			String reduction = "%s = %s";
			log.append(String.format(showWork, params[2], params[0], params[1]));
			log.append(REDUCES_TO);
			log.append(String.format(showWork, params[2], applyMode(params[0]), applyMode(params[1])));
			log.append(REDUCES_TO);
			log.append(String.format(reduction, params[2], applyMode(params[0]) + applyMode(params[1])));
		}
		else if (opcode == Opcode.MULTIPLY) {
			String showWork = "%s = %s x %s";
			String reduction = "%s = %s";
			log.append(String.format(showWork, params[2], params[0], params[1]));
			log.append(REDUCES_TO);
			log.append(String.format(showWork, params[2], applyMode(params[0]), applyMode(params[1])));
			log.append(REDUCES_TO);
			log.append(String.format(reduction, params[2], applyMode(params[0]) * applyMode(params[1])));
		}
		else if (opcode == Opcode.SAVE) {
			String reduction = "%s = %s";
			String value = isWaiting() ? "?" : String.valueOf(getInputs().get(0));
			log.append(String.format(reduction, params[0], value));
		}
		else if (opcode == Opcode.OUTPUT) {
			String showWork = "out = %s";
			String reduction = "out = %s";
			log.append(String.format(showWork, params[0]));
			log.append(REDUCES_TO);
			log.append(String.format(reduction, applyMode(params[0])));
		}
		else if (opcode == Opcode.JUMP_IF_TRUE) {
			String showWork = "if (%s != 0) jump to %s";
			String reduction = "%s";
			log.append(String.format(showWork, params[0], params[1]));
			log.append(REDUCES_TO);
			log.append(String.format(showWork, applyMode(params[0]), applyMode(params[1])));
			log.append(REDUCES_TO);
			log.append(String.format(reduction, (applyMode(params[0]) != 0 ? "jump" : "no jump")));
		}
		else if (opcode == Opcode.JUMP_IF_FALSE) {
			String showWork = "if (%s == 0) jump to %s";
			String reduction = "%s";
			log.append(String.format(showWork, params[0], params[1]));
			log.append(REDUCES_TO);
			log.append(String.format(showWork, applyMode(params[0]), applyMode(params[1])));
			log.append(REDUCES_TO);
			log.append(String.format(reduction, (applyMode(params[0]) == 0 ? "jump" : "no jump")));
		}
		else if (opcode == Opcode.LESS_THAN) {
			String showWork = "%s = (%s < %s ? 1 :0)";
			String reduction = "%s";
			log.append(String.format(showWork, params[2], params[0], params[1]));
			log.append(REDUCES_TO);
			log.append(String.format(showWork, params[2], applyMode(params[0]), applyMode(params[1])));
			log.append(REDUCES_TO);
			log.append(String.format(reduction, ((applyMode(params[0]) < applyMode(params[1])) ? 1 : 0)));
		}
		else if (opcode == Opcode.EQUALS) {
			String showWork = "%s = (%s == %s ? 1 :0)";
			String reduction = "%s";
			log.append(String.format(showWork, params[2], params[0], params[1]));
			log.append(REDUCES_TO);
			log.append(String.format(showWork, params[2], applyMode(params[0]), applyMode(params[1])));
			log.append(REDUCES_TO);
			log.append(String.format(reduction, ((applyMode(params[0]) == applyMode(params[1])) ? 1 : 0)));
		}
		else if (opcode == Opcode.RELATIVE_BASE_OFFSET) {
			String showWork = "rB = %s + %s";
			String reduction = "%s";
			log.append(String.format(showWork, "rB", params[0]));
			log.append(REDUCES_TO);
			log.append(String.format(showWork, getRelativeBase(), applyMode(params[0])));
			log.append(REDUCES_TO);
			log.append(String.format(reduction, getRelativeBase() + applyMode(params[0])));
		}
		else {
			log.append("No human-readable form assigned yet.");
		}
		System.out.println(log.toString());
	}

	/**
	 * Parses a parameter based on its mode. When mode = POSITIONAL, return the value at the parameter as an address.
	 * When mode = IMMEDIATE, return the parameter value as is. When mode = RELATIVE, return the value at the parameter
	 * as an address plus the relative base.
	 */
	private long applyMode(Parameter param) {
		if (param.getMode() == Mode.POSITIONAL) {
			return (get(param.getValue()));
		}
		if (param.getMode() == Mode.IMMEDIATE) {
			return (param.getValue());
		}
		// RELATIVE
		return (get(param.getValue() + getRelativeBase()));
	}

	/**
	 * Returns a value at an address. Assumes mode has already been handled by caller.
	 */
	public long get(long address) {
		expandMemory(address);
		return (_memory.get((int) address));
	}

	/**
	 * Stores a value at an address.
	 */
	private void set(Parameter parameter, long value) {
		if (parameter.getMode() == Mode.IMMEDIATE) {
			throw new IllegalArgumentException("Cannot set with an IMMEDIATE parameter value.");
		}
		long address = parameter.getValue();
		if (parameter.getMode() == Mode.RELATIVE) {
			address += getRelativeBase();
		}
		expandMemory(address);
		_memory.set((int) address, value);
	}

	/**
	 * Increases memory to accommodate a large address.
	 */
	private void expandMemory(long address) {
		if (address > Integer.MAX_VALUE) {
			throw new RuntimeException("Huge address on set " + address);
		}
		while (_memory.size() <= address) {
			_memory.add(0L);
		}
	}
	
	/**
	 * Accessor for the inputs
	 */
	public List<Long> getInputs() {
		return _inputs;
	}

	/**
	 * Accessor for the output (not destructive)
	 */
	public long getLastOutput() {
		if (_outputs.isEmpty()) {
			return (0);
		}
		return _outputs.get(_outputs.size() - 1);
	}

	/**
	 * Accessor for the outputs
	 */
	public List<Long> getOutputs() {
		return (_outputs);
	}
	
	/**
	 * Accessor for the instruction pointer
	 */
	private long getPointer() {
		return _pointer;
	}

	/**
	 * Accessor for the instruction pointer
	 */
	private void setPointer(long pointer) {
		_pointer = pointer;
	}

	/**
	 * Accessor for the relativeBase
	 */
	private long getRelativeBase() {
		return _relativeBase;
	}

	/**
	 * Accessor for the relativeBase
	 */
	private void setRelativeBase(long relativeBase) {
		_relativeBase = relativeBase;
	}

	/**
	 * Accessor for the debug flag
	 */
	private boolean isDebugEnabled() {
		return _debug;
	}

	/**
	 * Accessor for the debug flag
	 */
	public void setDebugEnabled(boolean debug) {
		_debug = debug;
	}
}
