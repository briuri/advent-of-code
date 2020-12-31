package buri.aoc.y20.d18;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 18: Operation Order
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * Evaluate the expression on each line of the homework; what is the sum of the resulting values?
	 *
	 * Part 2:
	 * What do you get if you add up the results of evaluating the homework problems using these new rules?
	 */
	public static long getResult(Part part, List<String> input) {
		long sum = 0;
		for (String equation : input) {
			sum += simplify(part, equation);
		}
		return (sum);
	}

	/**
	 * Breaks down an equation by replacing inner parenthetical statements with their computation. Makes no decision
	 * about order of precedence at this stage.
	 */
	protected static long simplify(Part part, String equation) {
		StringBuffer buffer = new StringBuffer(equation);

		// Loop over the equation replacing parentheses until none are left.
		while (buffer.indexOf(")") != -1) {
			int right = buffer.indexOf(")");
			int left = buffer.indexOf("(");

			// Find innermost ( to the left of the ).
			int nextLeft = buffer.indexOf("(", left + 1);
			while (nextLeft != -1 && nextLeft < right) {
				left = nextLeft;
				nextLeft = buffer.indexOf("(", left + 1);
			}

			// Replace the inner equation with its solution
			String innerEquation = buffer.substring(left + 1, right);
			buffer.replace(left, right + 1, solve(part, innerEquation).toString());
		}

		// Finally, the entire equation can be solved without worrying about parentheses.
		return (solve(part, buffer.toString()));
	}

	/**
	 * Solves a simple equation with no parentheses, delegating the decision about order of precedence.
	 */
	protected static Long solve(Part part, String equation) {
		return (part == Part.ONE ? solveLR(equation) : solveAM(equation));
	}

	/**
	 * Solves a simple equation with no parentheses from left-to-right.
	 */
	protected static Long solveLR(String equation) {
		String[] tokens = equation.split(" ");
		Long result = Long.valueOf(tokens[0]);
		for (int i = 1; i < tokens.length; i++) {
			if (tokens[i].equals("*") || tokens[i].equals("+")) {
				long value = Long.valueOf(tokens[i + 1]);
				if (tokens[i].equals("*")) {
					result = result * value;
				}
				else {
					result = result + value;
				}
				i++;
			}
		}
		return (result);
	}

	/**
	 * Solves a simple equation with no parentheses, addition first then multiplication.
	 */
	protected static Long solveAM(String equation) {
		Long result = 1L;
		// Use multiplication sign to break up equation into factors.
		for (String innerEquation : equation.split(" \\* ")) {
			long value;
			// Factor is a simple addition equation that can be solved left-to-right.
			if (innerEquation.indexOf("+") != -1) {
				value = solveLR(innerEquation);
			}
			// Factor already in its simplest form.
			else {
				value = Long.valueOf(innerEquation);
			}
			result = result * value;
		}
		return (result);
	}
}