package buri.aoc.y16.d23;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 23: Safe Cracking
 * 
 * @author Brian Uri!
 */
public class Day23 extends BasePuzzle {

	/**
	 * reg[a] = 7
	 * 0 reg[b] = reg[a]
	 * 1 reg[b] -= 1
	 * 2 reg[d] = reg[a]
	 * 3 reg[a] = 0
	 * 4 reg[c] = reg[b]
	 * 5 reg[a] += 1
	 * 6 reg[c] -= 1
	 * 7 if (reg[c] != 0) then goto (5)
	 * 8 reg[d] -= 1
	 * 9 if (reg[d] != 0) then goto (4)
	 * 10 reg[b] -= 1
	 * 11 reg[c] = reg[b]
	 * 12 reg[d] = reg[c]
	 * 13 reg[d] -= 1
	 * 14 reg[c] += 1
	 * 15 if (reg[d] != 0) then goto (13)
	 * 16 toggle (16 + reg[c])
	 * 17 reg[c] = -16
	 * 18 if (1 != 0) then goto (18 + reg[c])
	 * 19 reg[c] = 75
	 * 20 if (97 != 0) then goto (20 + reg[d])
	 * 21 reg[a] += 1
	 * 22 reg[d] += 1
	 * 23 if (reg[d] != 0) then goto (21)
	 * 24 reg[c] += 1
	 * 25 if (reg[c] != 0) then goto (20)
	 */

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2016/23", fileIndex));
	}

	/**
	 * Part 1:
	 * What value should be sent to the safe?
	 * 
	 * Part 2:
	 * Anyway, what value should actually be sent to the safe?
	 * 
	 * TODO: Refactor algorithm to reduce run time for part 2 (16 minutes).
	 */
	public static long getResult(Part part, List<String> input) {
		long start = (part == Part.ONE ? 7L : 12L);
		Registers registers = new Registers(input, start);
		registers.process();
		return (registers.get("a"));
	}
}