package buri.aoc.y20.d14;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 14: Docking Data
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(165, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(7440382076205L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(208, Puzzle.getResult(Part.TWO, Puzzle.getInput(2)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4200656704538L, result);
	}

	/**
	 * Part 1:
	 * Execute the initialization program. What is the sum of all values left in memory after it completes?
	 *
	 * Part 2:
	 * Execute the initialization program using an emulator for a version 2 decoder chip. What is the sum of all values
	 * left in memory after it completes?
	 */
	public static long getResult(Part part, List<String> input) {
		Memory memory = new Memory();
		for (String line : input) {
			if (line.startsWith("mask")) {
				memory.setMask(line.split(" = ")[1]);
			}
			else {
				Long address = Long.valueOf(line.split("\\[")[1].split("\\]")[0]);
				Long value = Long.valueOf(line.split(" = ")[1]);
				memory.set(part, address, value);
			}
		}
		return (memory.getSum());
	}
}