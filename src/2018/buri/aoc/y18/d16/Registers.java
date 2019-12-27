package buri.aoc.y18.d16;

import buri.aoc.data.registers.IndexedRegisters;

/**
 * Representation of the 4 registers containing integer values.
 * 
 * @author Brian Uri!
 */
public class Registers extends IndexedRegisters {

	/**
	 * Constructor for Part 2, with registers initialized to zero.
	 */
	public Registers() {
		this("0, 0, 0, 0");
	}

	/**
	 * String-based Constructor
	 */
	public Registers(String values) {
		super(4);
		String[] tokens = values.split(",");
		for (int i = 0; i < tokens.length; i++) {
			set(i, Integer.valueOf(tokens[i].trim()));
		}
	}

	/**
	 * Copy constructor
	 */
	private Registers(int[] registers) {
		super(registers.length);
		System.arraycopy(registers, 0, getRegisters(), 0, registers.length);
	}

	/**
	 * Creates a copy of a register that can be modified without affecting the original.
	 */
	private Registers copy() {
		return (new Registers(getRegisters()));
	}

	/**
	 * Tries all possible opcodes using the a,b,c values to determine how many possible matches there are. Compares
	 * result of operation against the expected Registers to identify a match.
	 */
	public int tryCode(String[] code, Registers expected) {
		int numCodes = 16;
		int matches = 0;

		// Test each of the 15 opcodes.
		for (int i = 0; i < numCodes; i++) {
			Registers copy = copy();
			code[0] = String.valueOf(i);
			copy.runIntCode(code);
			if (expected.equals(copy)) {
				matches++;
			}
		}
		if (matches == 1) {
			/**
			 * To map numbers to the actual text codes, I ran this tryCode() method and printed out the codes with just
			 * 1 match. I then removed that match from the pool of possible codes to test (causing instructions with 2
			 * matches to now have 1, and so forth) and iteratively reran until I had all the codes.
			 * 
			 * I was then able to write runCode() and finally refactored tryCode() to use runCode().
			 */
		}
		return (matches);
	}

}