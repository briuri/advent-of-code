package buri.aoc.y20.d14;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 14: Docking Data
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(165L, 1, false);
		assertRun(7440382076205L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(208L, 2, false);
		assertRun(4200656704538L, 0, true);
	}

	/**
	 * Part 1:
	 * Execute the initialization program. What is the sum of all values left in memory after it completes?
	 *
	 * Part 2:
	 * Execute the initialization program using an emulator for a version 2 decoder chip. What is the sum of all values
	 * left in memory after it completes?
	 */
	protected long runLong(Part part, List<String> input) {
		Memory memory = new Memory();
		for (String line : input) {
			if (line.startsWith("mask")) {
				memory.setMask(line.split(" = ")[1]);
			}
			else {
				long address = Long.parseLong(line.split("\\[")[1].split("]")[0]);
				long value = Long.parseLong(line.split(" = ")[1]);
				memory.set(part, address, value);
			}
		}
		return (memory.getSum());
	}
}