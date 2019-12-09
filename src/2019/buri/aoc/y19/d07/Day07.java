package buri.aoc.y19.d07;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;
import buri.aoc.data.Permutations;
import buri.aoc.data.intcode.Computer;

/**
 * Day 07: Amplification Circuit
 * 
 * @author Brian Uri!
 */
public class Day07 extends Puzzle {

	/**
	 * Returns the input file as a list of longs
	 */
	public static List<Long> getInput(int fileIndex) {
		List<Long> list = new ArrayList<>();
		for (String input : readFile("2019/07", fileIndex).get(0).split(",")) {
			list.add(Long.valueOf(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * What is the highest signal that can be sent to the thrusters?
	 * 
	 * Part 2:
	 * What is the highest signal that can be sent to the thrusters?
	 */
	public static long getResult(Part part, List<Long> program) {
		long[] phases = (part == Part.ONE ? new long[] { 0, 1, 2, 3, 4 } : new long[] { 5, 6, 7, 8, 9 });
		List<long[]> permutations = Permutations.getPermutations(phases);
		
		long maxOutput = 0;
		for (long[] perm : permutations) {
			// Initialize all amplifiers with phases.
			List<Computer> amps = new ArrayList<>();
			for (int i = 0; i < perm.length; i++) {
				amps.add(new Computer(program, perm[i]));
			}

			// Execute each amplifier until it halts (or waits for input in Part TWO).
			// In Part ONE, this while loop only runs one time then all amps halt.
			long output = 0;
			while (true) {
				for (Computer amp : amps) {
					amp.run(output);
					output = amp.getLastOutput();
				}
				if (!amps.get(amps.size() - 1).isWaiting()) {
					break;
				}
			}
			maxOutput = Math.max(output, maxOutput);
		}
		return (maxOutput);
	}
}