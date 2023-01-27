package buri.aoc.y17.d18;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Day 18: Duet
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(4L, 1, false);
		assertRun(2951L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(3L, 2, false);
		assertRun(7366L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the value of the recovered frequency (the value of the most recently played sound) the first time a rcv
	 * instruction is executed with a non-zero value?
	 *
	 * Part 2:
	 * Once both of your programs have terminated (regardless of what caused them to do so), how many times did program
	 * 1 send a value?
	 */
	protected long runLong(Part part, List<String> input) {
		if (part == Part.ONE) {
			Part1Registers registers = new Part1Registers(input);
			registers.process();
			return (registers.getLastFrequency());
		}
		// Part TWO
		Queue<Long> zeroOutgoing = new LinkedList<>();
		Queue<Long> oneOutgoing = new LinkedList<>();
		Part2Registers programZero = new Part2Registers(input, 0, oneOutgoing, zeroOutgoing);
		Part2Registers programOne = new Part2Registers(input, 1, zeroOutgoing, oneOutgoing);
		do {
			programZero.process();
			programOne.process();
		} while (!programZero.isWaitingForQueue() || !programOne.isWaitingForQueue());
		return (programOne.getOutgoingCount());
	}
}