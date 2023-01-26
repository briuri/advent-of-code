package buri.aoc.y18.d16;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 16: Chronal Classification
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(1L, 1, false);
		assertRun(592L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(557L, 2, true);
	}

	/**
	 * For ease of processing, I split the raw input file into the data for Part 1 (16-0, 16-1) and Part 2 (16-2).
	 *
	 * Part 1:
	 * Ignoring the opcode numbers, how many samples in your puzzle input behave like three or more opcodes?
	 *
	 * Part 2:
	 * What value is contained in register 0 after executing the test program?
	 */
	protected long runLong(Part part, List<String> input) {
		if (part == Part.ONE) {
			int count = 0;
			for (int i = 0; i < input.size(); i = i + 4) {
				String before = input.get(i);
				String[] code = input.get(i + 1).split(" ");
				String after = input.get(i + 2);
				Registers initial = new Registers(before.substring(before.indexOf('[') + 1, before.indexOf(']')));
				Registers expected = new Registers(after.substring(after.indexOf('[') + 1, after.indexOf(']')));
				int possibleMatches = initial.tryCode(code, expected);
				if (possibleMatches >= 3) {
					count++;
				}
			}
			return (count);
		}

		// Part TWO
		Registers registers = new Registers();
		for (int i = 0; i < input.size(); i++) {
			registers.runIntCode(input.get(i).split(" "));
		}
		return (registers.get(Registers.REGISTER, 0));
	}
}