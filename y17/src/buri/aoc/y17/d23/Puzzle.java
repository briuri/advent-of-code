package buri.aoc.y17.d23;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 23: Coprocessor Conflagration
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * a=0, If you run the program (your puzzle input), how many times is the mul instruction invoked?
	 *
	 * Part 2:
	 * a=1, If the program were to run to completion, what value would be left in register h?
	 */
	public static int getResult(Part part, List<String> input) {
		if (part == Part.ONE) {
			Registers registers = new Registers(input);
			registers.process();
			return (registers.getMultiplyCount());
		}

		// Part TWO
		/**
		 * Assembly reduction:
		 * 		int h = 0;
		 * 		for (long b = 105700; b <= 122700; b += 17) {
		 * 			boolean f = false;
		 * 			for (long d = 2; d != b; d++) {
		 * 				for (long e = 2; e != b; e++) {
		 * 					if ((d * e) == b) {
		 * 						f = true;
		 * 					}
		 * 				}
		 * 			}
		 * 			if (f) {
		 * 				h += 1;
		 * 			}
		 * 		}
		 * 		return (h);
		 *
		 * So it's testing all values in the outer for loop to see if that value can be made by multiply two other numbers.
		 * A faster way to do this would be to check if the number is a prime number. If it is not, increment h.
		 */
		int h = 0;
		for (long b = 105700; b <= 122700; b += 17) {
			if (!isPrime(b)) {
				h++;
			}
		}
		return (h);
	}

	/**
	 * Returns true if the number is prime.
	 */
	private static boolean isPrime(long b) {
		if (b % 2 == 0) {
			return (b == 2);
		}
		for (int i = 3; i <= (int) (0.1 + Math.sqrt(b)); i += 2) {
			if (b % i == 0) {
				return (false);
			}
		}
		return (true);
	}
}