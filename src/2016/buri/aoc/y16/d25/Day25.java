package buri.aoc.y16.d25;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 25: Clock Signal
 * 
 * @author Brian Uri!
 */
public class Day25 extends Puzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2016/25", fileIndex));
	}

	/**
	 * Reduced pseudocode:
	 * 
	 *   reg[d] = start + (7 * 365)
	 * A reg[a] = reg[d]
	 * B reg[b] = reg[a]
	 *   reg[a] = 0
	 *   
	 * C reg[c] = 2
	 * D if (reg[b] != 0) then goto E else goto F
	 * E reg[b] -= 1
	 *   reg[c] -= 1
	 *   if (reg[c] != 0) then goto D
	 *   reg[a] += 1
	 *   goto C
	 *   
	 * F reg[b] = 2
	 *   while (reg[c] != 0) {
	 *      reg[b] -= 1
	 *      reg[c] -= 1
	 *   }
	 * 	 transmit reg[b]
	 *   if (reg[a] != 0) then goto B else goto A
	 */

	/**
	 * Part 1:
	 * What is the lowest positive integer that can be used to initialize register a and cause the code to output a
	 * clock signal of 0, 1, 0, 1... repeating forever?
	 */
	public static int getResult(Part part, List<String> input) {
		int target = 7 * 365;
		int n = 1;
	    while (n < target) {
	    	if (n % 2 == 0) {
	    		n = n * 2 + 1;
	    	}
	    	else {
	    		n = n * 2;
	    	}
	    }
	    return (n - target);
	}
}