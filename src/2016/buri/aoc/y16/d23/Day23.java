package buri.aoc.y16.d23;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 23: Safe Cracking
 * 
 * @author Brian Uri!
 */
public class Day23 extends Puzzle {

	/**
	 * 0 reg[b] = reg[a]
	 * 1 reg[b] -= 1
	 * 2 reg[d] = reg[a]
	 * 3 reg[a] = 0
	 * 4 reg[c] = reg[b]
	 * 5 reg[a] += 1
	 * 6 reg[c] -= 1
	 * 7 if (reg[c] != 0) then goto (7 + -2)
	 * 8 reg[d] -= 1
	 * 9 if (reg[d] != 0) then goto (9 + -5)
	 * 10 reg[b] -= 1
	 * 11 reg[c] = reg[b]
	 * 12 reg[d] = reg[c]
	 * 13 reg[d] -= 1
	 * 14 reg[c] += 1
	 * 15 if (reg[d] != 0) then goto (15 + -2)
	 * 16 toggle (16 + reg[c])
	 * 17 reg[c] = -16
	 * 18 if (1 != 0) then goto (18 + reg[c])
	 * 19 reg[c] = 75
	 * 20 if (97 != 0) then goto (20 + reg[d])
	 * 21 reg[a] += 1
	 * 22 reg[d] += 1
	 * 23 if (reg[d] != 0) then goto (23 + -2)
	 * 24 reg[c] += 1
	 * 25 if (reg[c] != 0) then goto (25 + -5)
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
	 * QUESTION
	 */
	public static long getResult(Part part, List<String> input) {
		Registers registers = new Registers(input);
		registers.process();
		if (part == Part.ONE) {
			return (registers.get("a"));
		}
		return (0);
	}
}