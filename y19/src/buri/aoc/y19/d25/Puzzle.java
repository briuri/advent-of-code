package buri.aoc.y19.d25;

import java.io.IOException;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.data.intcode.Computer;

/**
 * Day 25: Cryostasis
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * Look around the ship and see if you can find the password for the main airlock.
	 */
	public static String getResult(List<String> input) throws IOException {
		Computer computer = new Computer(input);
		// Replaced my input loop with automated commands after I beat the puzzle.
		String[] walkthrough = new String[] {
			"east", "east", "east", "take ornament", "north", "west", "take fixed point",
			"east", "south", "west", "north", "north", "take asterisk",
			"south", "west", "west", "take astronaut ice cream", "east", "east", "south", "west",
			"south", "south", "west", "north", "north",	"north", "east" };
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