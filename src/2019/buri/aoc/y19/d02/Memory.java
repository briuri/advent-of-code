package buri.aoc.y19.d02;

import java.util.ArrayList;
import java.util.List;

/**
 * Memory class used for running Intcode programs.
 * 
 * @author Brian Uri!
 */
public class Memory {

	private List<Integer> _memory;

	/**
	 * Constructor without noun/verb setting.
	 * 
	 * @param program the intcodes to run (does not modify)
	 */
	public Memory(List<Integer> program) {
		_memory = new ArrayList<>(program);
	}
	
	/**
	 * Constructor
	 * 
	 * @param program the intcodes to run (does not modify)
	 * @param noun value to store at address 1
	 * @param verb value to store at address 2
	 */
	public Memory(List<Integer> program, int noun, int verb) {
		this(program);
		set(1, noun);
		set(2, verb);
	}

	/**
	 * Runs the intcode program until a HALT opcode is hit, then returns the value in address 0.
	 */
	public int run() {
		int pointer = 0;
		Opcode opcode = Opcode.getOpcodeFor(get(pointer));
		while (opcode != Opcode.HALT) {
			int[] parameters = new int[opcode.getParameters()];
			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = get(pointer + i + 1);
			}
			if (opcode == Opcode.ADD) {
				add(parameters);
			}
			else if (opcode == Opcode.MULTIPLY) {
				multiply(parameters);
			}
			pointer = pointer + 1 + opcode.getParameters();
			opcode = Opcode.getOpcodeFor(get(pointer));
		}
		return (get(0));
	}

	/**
	 * Adds the values at the first x addresses in the parameters, then stores the result at the last address
	 * in the parameters.
	 */
	private void add(int[] parameters) {
		int sum = 0;
		for (int i = 0; i < parameters.length - 1; i++) {
			sum += get(parameters[i]);
		}
		set(parameters[parameters.length - 1], sum);
	}

	/**
	 * Multiplies the values at the first x addresses in the parameters, then stores the result at the last address
	 * in the parameters.
	 */
	private void multiply(int[] parameters) {
		int product = 1;
		for (int i = 0; i < parameters.length - 1; i++) {
			product = product * get(parameters[i]);
		}
		set(parameters[parameters.length - 1], product);
	}

	/**
	 * Returns a value at an address
	 */
	private int get(int address) {
		return (_memory.get(address));
	}
	
	/**
	 * Stores a value at an address
	 */
	private void set(int address, int value) {
		_memory.set(address, value);
	}
}
