package buri.aoc.y15.d07;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;

/**
 * Day 7: Some Assembly Required
 * 
 * @author Brian Uri!
 */
public class Day07 extends BasePuzzle {

	private static final Pattern VARIABLE_NAMES = Pattern.compile("[a-z]");

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2015/07", fileIndex));
	}

	/**
	 * Part 1:
	 * In little Bobby's kit's instructions booklet (provided as your puzzle input), what signal is ultimately provided
	 * to wire a?
	 * 
	 * Part 2:
	 * Now, take the signal you got on wire a, override wire b to that signal, and reset the other wires (including wire
	 * a). What new signal is ultimately provided to wire a?
	 */
	public static String getResult(Part part, List<String> input) {
		// Build a map of inputs and outputs, using output as key.
		Map<String, String> io = new HashMap<>();
		for (String line : input) {
			String[] tokens = line.split(" -> ");
			io.put(tokens[1], tokens[0]);
		}
		if (part == Part.TWO) {
			io.put("b", "46065");
		}

		// Iterate over the io mapping, replacing variables with numbers until no more can be replaced.
		while (true) {
			for (String out : io.keySet()) {
				String[] in = io.get(out).split(" ");
				if (in.length == 1) {
					replace(io, out, in[0]);
				}
			}

			// See if more variables need replacing.
			int variablesRemaining = 0;
			for (String out : io.keySet()) {
				if (VARIABLE_NAMES.matcher(io.get(out)).find()) {
					variablesRemaining++;
				}
			}
			if (variablesRemaining == 0) {
				break;
			}
		}

		return (io.get("a"));
	}

	/**
	 * Replaces a variable name with some number. If the resultant input has no more variables,
	 * compute the value.
	 */
	private static void replace(Map<String, String> io, String variable, String number) {
		for (String out : io.keySet()) {
			String[] in = io.get(out).split(" ");
			for (int i = 0; i < in.length; i++) {
				if (in[i].equals(variable)) {
					in[i] = number;
				}
			}
			String update = String.join(" ", in);
			if (!VARIABLE_NAMES.matcher(update).find()) {
				update = String.valueOf((int) calculate(update));
			}
			io.put(out, update);
		}
	}

	/**
	 * Performs a calculation and returns the result.
	 */
	private static char calculate(String input) {
		String[] tokens = input.split(" ");
		if (tokens.length == 1) { // assignment
			return ((char) Integer.parseInt(tokens[0]));
		}
		if (tokens.length == 2) { // NOT
			char x = (char) Integer.parseInt(tokens[1]);
			return ((char) ~x);
		}
		else {
			char x = (char) Integer.parseInt(tokens[0]);
			char y = (char) Integer.parseInt(tokens[2]);
			if (tokens[1].equals("AND")) {
				return ((char) (x & y));
			}
			if (tokens[1].equals("OR")) {
				return ((char) (x | y));
			}
			if (tokens[1].equals("LSHIFT")) {
				return ((char) (x << y));
			}
			// RSHIFT
			return ((char) (x >>> y));
		}
	}
}