package buri.aoc.y17.d18;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 18: Duet
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(4, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(2951, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3, Puzzle.getResult(Part.TWO, Puzzle.getInput(2)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(7366, result);
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
	public static long getResult(Part part, List<String> input) {
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
		while (true) {
			programZero.process();
			programOne.process();
			if (programZero.isWaitingForQueue() && programOne.isWaitingForQueue()) {
				break;
			}
		}
		return (programOne.getOutgoingCount());
	}
}