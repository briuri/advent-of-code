package buri.aoc.y18.d19;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.registers.IndexedRegisters;
import org.junit.Test;

import java.util.List;

/**
 * Day 19: Go With the Flow
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(6L, 1, false);
		assertRun(1056L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(10915260L, 0, true);
	}

	/**
	 * Part 1:
	 * What value is left in register 0 when the background process halts?
	 *
	 * Part 2:
	 * This time, register 0 started with the value 1. What value is left in register 0 when this new background process
	 * halts?
	 */
	protected long runLong(Part part, List<String> input) {
		String ipRegister = input.remove(0);
		IndexedRegisters registers = new IndexedRegisters(Character.getNumericValue(ipRegister.charAt(4)), input);
		int initialZeroValue = (part == Part.ONE ? 0 : 1);
		if (part == Part.ONE) {
			registers.run(initialZeroValue);
			return (registers.get(IndexedRegisters.REGISTER, 0));
		}

		// Part TWO
		/*
		  Solution for part 2 was fairly exploratory. I ran the program until it started looping instruction pointers
		  then converted the assembly instructions into pseudocode. I set the initial states of the registers to be
		  near the loop points to observe behavior.

		  ip = 3, 4, 5, 6, 8, 9, 10, 11

		  reg[1] = reg[5] * reg[2]
		  reg[1] = (reg[1] == reg[4]) ? 1: 0;
		  reg[3] = reg[1] + reg[3]
		  reg[3] = reg[3] + 1
		  reg[2] = reg[2] + 1
		  reg[1] = (reg[2] > reg[4]) ? 1 : 0;
		  reg[3] = reg[3] + reg[1]
		  reg[3] = 2

		  reg[4] was a fixed value of 101551389. The loop was iterating over all numbers under it to find its factors
		  and summing them. Rather than simulate this, I switched gears and wrote a function to sum factors in
		  isolation.

		  (This was very similar to y17d23).
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