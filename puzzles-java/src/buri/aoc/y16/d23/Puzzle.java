package buri.aoc.y16.d23;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 23: Safe Cracking
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(3L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(12315L, result);
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(2));
		toConsole(result);
		assertEquals(479008875L, result);
	}

	/**
	 * Part One (23-0.txt)
	 *
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
	 *
	 * Part Two (23-2.txt)
	 * Replace lines 4 - 9 with a new multiply instruction to optimize.
	 *
	 * 0 reg[b] = reg[a]
	 * 1 reg[b] -= 1
	 * 2 reg[d] = reg[a]
	 * 3 reg[a] = 0
	 * 4 reg[a] = reg[b] * reg[d]
	 * 5 reg[c] = 0
	 * 6 reg[c] = 0
	 * 7 reg[c] = 0
	 * 8 reg[c] = 0
	 * 9 reg[d] = 0
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
	 * Part 1:
	 * What value should be sent to the safe?
	 *
	 * Part 2:
	 * Anyway, what value should actually be sent to the safe?
	 */
	public static long getResult(Part part, List<String> input) {
		long start = (part == Part.ONE ? 7L : 12L);
		Registers registers = new Registers(input, start);
		registers.process();
		return (registers.get("a"));
	}
}