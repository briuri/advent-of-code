package buri.aoc.y20.d18;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 18: Operation Order
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(26335L, 1, false);
		assertRun(1451467526514L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(693942L, 2, false);
		assertRun(224973686321527L, 0, true);
	}

	/**
	 * Part 1:
	 * Evaluate the expression on each line of the homework; what is the sum of the resulting values?
	 *
	 * Part 2:
	 * What do you get if you add up the results of evaluating the homework problems using these new rules?
	 */
	protected long runLong(Part part, List<String> input) {
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
		StringBuilder builder = new StringBuilder(equation);

		// Loop over the equation replacing parentheses until none are left.
		while (builder.indexOf(")") != -1) {
			int right = builder.indexOf(")");
			int left = builder.indexOf("(");

			// Find innermost ( to the left of the ).
			int nextLeft = builder.indexOf("(", left + 1);
			while (nextLeft != -1 && nextLeft < right) {
				left = nextLeft;
				nextLeft = builder.indexOf("(", left + 1);
			}

			// Replace the inner equation with its solution
			String innerEquation = builder.substring(left + 1, right);
			builder.replace(left, right + 1, solve(part, innerEquation).toString());
		}

		// Finally, the entire equation can be solved without worrying about parentheses.
		return (solve(part, builder.toString()));
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
		long result = Long.parseLong(tokens[0]);
		for (int i = 1; i < tokens.length; i++) {
			if (tokens[i].equals("*") || tokens[i].equals("+")) {
				long value = Long.parseLong(tokens[i + 1]);
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
		long result = 1L;
		// Use multiplication sign to break up equation into factors.
		for (String innerEquation : equation.split(" \\* ")) {
			long value;
			// Factor is a simple addition equation that can be solved left-to-right.
			if (innerEquation.contains("+")) {
				value = solveLR(innerEquation);
			}
			// Factor already in its simplest form.
			else {
				value = Long.parseLong(innerEquation);
			}
			result = result * value;
		}
		return (result);
	}
}