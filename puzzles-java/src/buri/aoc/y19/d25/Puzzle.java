package buri.aoc.y19.d25;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.intcode.Computer;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Day 25: Cryostasis
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() throws IOException {
		assertRun("134227456", 0, true);
	}

	/**
	 * Part 1:
	 * Look around the ship and see if you can find the password for the main airlock.
	 */
	protected String runString(Part part, List<String> input) {
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
		StringBuilder builder = new StringBuilder();
		for (Long value : computer.getOutputs()) {
			builder.append((char) value.intValue());
		}
		// Only output the code at the end.
		builder.delete(0, builder.indexOf("by typing ") + 10);
		builder.delete(builder.indexOf(" "), builder.length());
		return (builder.toString());
	}
}