package buri.aoc.y21.d24;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data model for a naive ALU. Not actually used in the puzzle solution -- just used to decompile the puzzle input.
 *
 * @author Brian Uri!
 */
public class ALU {
	private List<String> _input;
	private Map<String, String> _memory;

	/**
	 * Constructor
	 */
	public ALU(List<String> input) {
		_input = input;
	}

	/**
	 * Runs the ALU on a 14-digit number.
	 */
	public void run(String allInputs) {
		_memory = new HashMap<>();
		getMemory().put("w", "0");
		getMemory().put("x", "0");
		getMemory().put("y", "0");
		getMemory().put("z", "0");

		List<String> input = new ArrayList<>();
		for (Character value : allInputs.toCharArray()) {
			input.add(String.valueOf(value));
		}

		for (String line : getInput()) {
			String[] tokens = line.split(" ");
			String command = tokens[0];
			String saveAt = tokens[1];
			BigInteger old = new BigInteger(getMemory().get(saveAt));
			BigInteger value = null;
			boolean isLiteral = false;
			String valueAt = null;
			if (tokens.length == 3) {
				valueAt = tokens[2];
				isLiteral = (valueAt.matches(".*\\d.*"));
				value = (isLiteral ? new BigInteger(valueAt) : new BigInteger(getMemory().get(valueAt)));
			}
			boolean debug = false;

			if (command.equals("inp")) {
//				System.out.println(getMemory());
				getMemory().put(saveAt, input.remove(0));
				if (debug) {
					System.out.println("\n" + saveAt + " = " + getMemory().get(saveAt));
				}
			}
			if (command.equals("add")) {
				getMemory().put(saveAt, old.add(value).toString());
				if (debug) {
					System.out.println("" + saveAt + " = " + saveAt + " + " + (isLiteral ? value : valueAt) + " = " + old + " + " + value + " = "
						+ getMemory().get(saveAt));
				}
			}
			if (command.equals("mul")) {
				getMemory().put(saveAt, old.multiply(value).toString());
				if (debug) {
					System.out.println("" + saveAt + " = " + saveAt + " * " + (isLiteral ? value : valueAt) + " = " + old + " * " + value + " = "
						+ getMemory().get(saveAt));
				}
			}
			if (command.equals("div")) {
				getMemory().put(saveAt, old.divide(value).toString());
				if (debug) {
					System.out.println("" + saveAt + " = " + saveAt + " / " + (isLiteral ? value : valueAt) + " = " + old + " / " + value + " = "
						+ getMemory().get(saveAt));
				}
			}
			if (command.equals("mod")) {
				getMemory().put(saveAt, old.mod(value).toString());
				if (debug) {
					System.out.println("" + saveAt + " = " + saveAt + " % " + (isLiteral ? value : valueAt) + " = " + old + " % " + value + " = "
						+ getMemory().get(saveAt));
				}
			}
			if (command.equals("eql")) {
				String newValue = (old.equals(value) ? "1" : "0");
				getMemory().put(saveAt, newValue);
				if (debug) {
					System.out.println("" + saveAt + " = (" + old + " == " + value + " ? 1 : 0) = " + newValue);
				}
			}
		}
//		System.out.println(getMemory());
//		System.out.println(allInputs + " z = " + getMemory().get("z"));
	}

	/**
	 * Accessor for the input
	 */
	public List<String> getInput() {
		return _input;
	}

	/**
	 * Accessor for the memory
	 */
	public Map<String, String> getMemory() {
		return _memory;
	}
}