package buri.aoc.y19.d25;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.intcode.Computer;

/**
 * Day 25: Cryostasis
 * 
 * @author Brian Uri!
 */
public class Day25 extends BasePuzzle {

	/**
	 * Returns the input file as an Intcode program
	 */
	public static List<Long> getInput(int fileIndex) {
		List<Long> list = new ArrayList<>();
		for (String input : readFile(fileIndex).get(0).split(",")) {
			list.add(Long.valueOf(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * Look around the ship and see if you can find the password for the main airlock.
	 */
	public static String getResult(Part part, List<Long> program) throws IOException {
		Computer computer = new Computer(program);
		// Replaced my input loop with automated commands after I beat the puzzle.
		String[] walkthrough = new String[] { "east", "east", "east", "take ornament", "north", "west",
			"take fixed point", "east", "south", "west", "north", "north", "take asterisk", "south", "west", "west",
			"take astronaut ice cream", "east", "east", "south", "west", "south", "south", "west", "north", "north",
			"north", "east" };
		for (String command : walkthrough) {
			computer.getInputs().addAll(Computer.toAscii(command));
		}
		computer.run();
		StringBuffer buffer = new StringBuffer();
		for (Long value : computer.getOutputs()) {
			buffer.append((char) value.intValue());
		}
		// Only output the code at the end.
		buffer.delete(0, buffer.indexOf("by typing ") + 10);
		buffer.delete(buffer.indexOf(" "), buffer.length());
		return (buffer.toString());
	}
}