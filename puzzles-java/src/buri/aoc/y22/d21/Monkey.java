package buri.aoc.y22.d21;

import buri.aoc.common.Part;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Data model for WHAT.
 *
 * @author Brian Uri!
 */
public class Monkey {
	private final String _name;
	private String _operation;

	public static final Pattern ALPHABET = Pattern.compile("\\p{Alpha}");

	/**
	 * Constructor
	 */
	public Monkey(Part part, String line) {
		String[] tokens = line.split(": ");
		_name = tokens[0];
		_operation = tokens[1];
		if (part == Part.TWO && getName().equals("root")) {
			String operand = getOperation().split(" ")[1];
			_operation = getOperation().replace(operand, "=");
		}
	}

	/**
	 * Replaces any instance of a monkey's name with the value.
	 */
	public void reduceOperation(Monkey monkey) {
		_operation = getOperation().replace(monkey.getName(), monkey.getOperation());
		Matcher matcher = ALPHABET.matcher(getOperation());
		if (!matcher.find() && !isSimpleOperation()) {
			String[] tokens = getOperation().split(" ");
			long left = Long.parseLong(tokens[0]);
			long right = Long.parseLong(tokens[2]);
			switch (tokens[1]) {
				case "+":
					_operation = String.valueOf(left + right);
					break;
				case "-":
					_operation = String.valueOf(left - right);
					break;
				case "*":
					_operation = String.valueOf(left * right);
					break;
				case "/":
					_operation = String.valueOf(left / right);
					break;
			}
		}
	}

	@Override
	public String toString() {
		return (getName() + ": " + getOperation());
	}

	/**
	 * Accesor for name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Returns true if the operation is a simple value.
	 */
	public boolean isSimpleOperation() {
		try {
			Long.parseLong(getOperation());
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * Accesor for operation
	 */
	public String getOperation() {
		return _operation;
	}
}