package buri.aoc.data.intcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class used for running Intcode programs.
 * - y19d2
 * - y19d5
 * - y19d7
 * 
 * @author Brian Uri!
 */
public class Computer {
	private List<Integer> _memory;
	private List<Integer> _inputs;
	private int _output;
	private int _pointer;

	private static final boolean DEBUG = false;
	private static final int DEBUG_WIDTH = 20;
	private static final String DEBUG_ARROW = " ---> ";

	/**
	 * Constructor (y19d2)
	 * 
	 * @param program the intcodes to run (does not modify)
	 * @param noun value to store at address 1
	 * @param verb value to store at address 2
	 */
	public Computer(List<Integer> program, Integer noun, Integer verb) {
		_memory = new ArrayList<>(program);
		if (noun != null) {
			_memory.set(1, noun);
		}
		if (verb != null) {
			_memory.set(2, verb);
		}
		setPointer(0);
	}
	
	/**
	 * Constructor (y19d5, y19d7)
	 * 
	 * @param program the intcodes to run (does not modify)
	 * @param input the value to start the program with
	 */
	public Computer(List<Integer> program, int input) {
		_memory = new ArrayList<>(program);
		_inputs = new ArrayList<>();
		getInputs().add(input);
		setPointer(0);
	}

	/**
	 * Runs the intcode program until a HALT opcode is hit, then returns the last output value.
	 */
	public int run(Integer nextInput) {
		if (nextInput != null) {
			getInputs().add(nextInput);
		}
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
				params[i] = new Parameter(get(getPointer() + i + 1), Character.getNumericValue(rawModes.charAt(i)));
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

			// Update instruction pointer.
			if (!jumped) {
				setPointer(getPointer() + 1 + opcode.getNumParameters());
			}
			fullOpcode = String.valueOf(get(getPointer()));
			opcode = Opcode.getOpcodeFor(fullOpcode);
		}
		return (getOutput());
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
		int value = applyMode(params[0]) + applyMode(params[1]);
		set(params[2], value);
	}

	/**
	 * Multiplies the first two values then stores the result at the last address.
	 */
	private void multiply(Parameter[] params) {
		int value = applyMode(params[0]) * applyMode(params[1]);
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
		setOutput(applyMode(params[0]));
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
	 * Displays the raw instructions and their resolutions for debugging.
	 */
	private void log(String fullOpcode, Opcode opcode, Parameter[] params) {
		if (!DEBUG) {
			return;
		}
		// Generate raw instruction.
		StringBuffer log = new StringBuffer();
		log.append(fullOpcode);
		for (int i = 0; i < params.length; i++) {
			log.append(",").append(params[i].getValue());
		}
		while (log.length() < DEBUG_WIDTH) {
			log.append(" ");
		}

		// Generate human-readable instructions.
		if (opcode == Opcode.ADD) {
			int value = applyMode(params[0]) + applyMode(params[1]);
			log.append(params[2]).append(" = ").append(params[0]).append(" + ").append(params[1]);
			if (params[0].isPositional() || params[1].isPositional()) {
				log.append(DEBUG_ARROW).append(applyMode(params[0])).append(" + ").append(applyMode(params[1]));
			}
			log.append(DEBUG_ARROW).append(value);
		}
		else if (opcode == Opcode.MULTIPLY) {
			int value = applyMode(params[0]) * applyMode(params[1]);
			log.append(params[2]).append(" = ").append(params[0]).append(" x ").append(params[1]);
			if (params[0].isPositional() || params[1].isPositional()) {
				log.append(DEBUG_ARROW).append(applyMode(params[0])).append(" x ").append(applyMode(params[1]));
			}
			log.append(DEBUG_ARROW).append(value);
		}
		else if (opcode == Opcode.SAVE) {
			log.append(params[0]).append(" = ");
			log.append(isWaiting() ? "?" : getInputs().get(0));
		}
		else if (opcode == Opcode.OUTPUT) {
			log.append("output = ").append(params[0]);
			if (params[0].isPositional()) {
				log.append(DEBUG_ARROW).append(applyMode(params[0]));
			}
		}
		else if (opcode == Opcode.JUMP_IF_TRUE) {
			log.append("if (").append(params[0]).append(" != 0) then jump to ").append(params[1]);
			if (params[0].isPositional() || params[1].isPositional()) {
				log.append(DEBUG_ARROW).append("if (").append(applyMode(params[0])).append(" != 0) ");
				log.append("then jump to ").append(applyMode(params[1]));
			}
			log.append(DEBUG_ARROW).append(applyMode(params[0]) != 0 ? "jump" : "no jump");
		}
		else if (opcode == Opcode.JUMP_IF_FALSE) {
			log.append("if (").append(params[0]).append(" == 0) then jump to ").append(params[1]);
			if (params[0].isPositional() || params[1].isPositional()) {
				log.append(DEBUG_ARROW).append("if (").append(applyMode(params[0])).append(" != 0) ");
				log.append("then jump to ").append(applyMode(params[1]));
			}
			log.append(DEBUG_ARROW).append(applyMode(params[0]) == 0 ? "jump" : "no jump");
		}
		else if (opcode == Opcode.LESS_THAN) {
			int value = (applyMode(params[0]) < applyMode(params[1])) ? 1 : 0;
			log.append(params[2]).append(" = (").append(params[0]).append(" < ").append(params[1]);
			log.append(" ? 1 : 0)");
			if (params[0].isPositional() || params[1].isPositional()) {
				log.append(DEBUG_ARROW);
				log.append("(").append(applyMode(params[0])).append(" < ").append(applyMode(params[1]));
				log.append(" ? 1 : 0)");
			}
			log.append(DEBUG_ARROW).append(value);
		}
		else if (opcode == Opcode.EQUALS) {
			int value = (applyMode(params[0]) == applyMode(params[1])) ? 1 : 0;
			log.append(params[2]).append(" = (").append(params[0]).append(" == ").append(params[1]);
			log.append(" ? 1 : 0)");
			if (params[0].isPositional() || params[1].isPositional()) {
				log.append(DEBUG_ARROW);
				log.append("(").append(applyMode(params[0])).append(" == ").append(applyMode(params[1]));
				log.append(" ? 1 : 0)");
			}
			log.append(DEBUG_ARROW).append(value);
		}
		else {
			log.append("No human-readable form assigned yet.");
		}
		System.out.println(log.toString());
	}

	/**
	 * Parses a parameter based on its mode. When mode = 0 (position), return the value at the parameter as an address.
	 * When mode = 1 (immediate), return the parameter value as is.
	 */
	private int applyMode(Parameter param) {
		return (param.isPositional() ? get(param.getValue()) : param.getValue());
	}

	/**
	 * Returns a value at an address
	 */
	public int get(int address) {
		return (_memory.get(address));
	}

	/**
	 * Stores a value at an address. The parameter is always handled in position mode.
	 */
	private void set(Parameter parameter, int value) {
		_memory.set(parameter.getValue(), value);
	}

	/**
	 * Accessor for the inputs
	 */
	private List<Integer> getInputs() {
		return _inputs;
	}

	/**
	 * Accessor for the output
	 */
	public int getOutput() {
		return _output;
	}

	/**
	 * Accessor for the output
	 */
	private void setOutput(int output) {
		_output = output;
	}

	/**
	 * Accessor for the instruction pointer
	 */
	private int getPointer() {
		return _pointer;
	}

	/**
	 * Accessor for the instruction pointer
	 */
	private void setPointer(int pointer) {
		_pointer = pointer;
	}
}
