package buri.aoc.y19.d07;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.Permutations;
import buri.aoc.common.data.intcode.Computer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 07: Amplification Circuit
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(43210L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(54321L, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(65210L, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(422858L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(139629729L, Puzzle.getResult(Part.TWO, Puzzle.getInput(4)));
		assertEquals(18216L, Puzzle.getResult(Part.TWO, Puzzle.getInput(5)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(14897241L, result);
	}

	/**
	 * Part 1:
	 * What is the highest signal that can be sent to the thrusters?
	 *
	 * Part 2:
	 * What is the highest signal that can be sent to the thrusters?
	 */
	public static long getResult(Part part, List<String> input) {
		Long[] phases = (part == Part.ONE ? new Long[] { 0L, 1L, 2L, 3L, 4L } : new Long[] { 5L, 6L, 7L, 8L, 9L });
		List<Long[]> permutations = Permutations.getPermutations(phases);

		long maxOutput = 0;
		for (Long[] perm : permutations) {
			// Initialize all amplifiers with phases.
			List<Computer> amps = new ArrayList<>();
			for (int i = 0; i < perm.length; i++) {
				Computer amp = new Computer(input);
				amp.getInputs().add(perm[i]);
				amps.add(amp);
			}

			// Execute each amplifier until it halts (or waits for input in Part TWO).
			// In Part ONE, this while loop only runs one time then all amps halt.
			long output = 0;
			while (true) {
				for (Computer amp : amps) {
					amp.getInputs().add(output);
					amp.run();
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