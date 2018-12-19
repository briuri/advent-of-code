package buri.aoc.y17.d18;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 18: Duet
 * 
 * @author Brian Uri!
 */
public class Day18 extends Puzzle {

	/**
	 * Returns input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2017/18", fileIndex));
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