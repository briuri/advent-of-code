package buri.aoc.y18.d19;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day19 extends Puzzle {

	/**
	 * Input: First line is the IP register index, rest are codes to run.
	 * Output: List of Strings, unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2018/19", fileIndex));
	}

	/**
	 * Part 1:
	 * What value is left in register 0 when the background process halts?
	 * 
	 * Part 2:
	 * This time, register 0 started with the value 1. What value is left in register 0 when this new background process
	 * halts?
	 */
	public static long getResult(Part part, List<String> input) {
		String ipRegister = input.remove(0);
		Registers registers = new Registers(part, Integer.valueOf(String.valueOf(ipRegister.charAt(4))), input);
		if (part == Part.ONE) {			
			registers.run();
			return (registers.get(Registers.REGISTER, 0));
		}

		// Part TWO
		/**
		 * Solution for part 2 was fairly exploratory. I ran the program until it started looping instruction pointers
		 * then converted the assembly instructions into pseudocode. I set the initial states of the registers to be
		 * near the loop points to observe behavior.
		 * 
		 * ip = 3, 4, 5, 6, 8, 9, 10, 11
		 * 
		 * reg[1] = reg[5] * reg[2]
		 * reg[1] = (reg[1] == reg[4]) ? 1: 0;
		 * reg[3] = reg[1] + reg[3]
		 * reg[3] = reg[3] + 1
		 * reg[2] = reg[2] + 1
		 * reg[1] = (reg[2] > reg[4]) ? 1 : 0;
		 * reg[3] = reg[3] + reg[1]
		 * reg[3] = 2
		 * 
		 * reg[4] was a fixed value of 101551389. The loop was iterating over all numbers under it to find its factors
		 * and summing them. Rather than simulate this, I switched gears and wrote a function to sum factors in
		 * isolation.
		 * 
		 * (This was very similar to y17d23).
		 */
		int sum = 0;
		int fixedValue = 10551389;
		for (int i = 1; i <= (int) Math.sqrt(fixedValue); i += 1) {
			if (fixedValue % i == 0) {
				sum += i;
				if (i != fixedValue / i) {
					sum += (fixedValue / i);
				}
			}
		}
		return (sum);
	}
}