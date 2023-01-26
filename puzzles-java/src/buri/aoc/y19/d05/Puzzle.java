package buri.aoc.y19.d05;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.intcode.Computer;
import org.junit.Test;

import java.util.List;

/**
 * Day 05: Sunny with a Chance of Asteroids
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(999L, 1, false);
		assertRun(15259545L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(7616021L, 0, true);
	}

	/**
	 * Part 1:
	 * After providing 1 to the only input instruction and passing all the tests, what diagnostic code does the program
	 * produce?
	 *
	 * Part 2:
	 * What is the diagnostic code for system ID 5?
	 */
	protected long runLong(Part part, List<String> input) {
		long inputValue = (part == Part.ONE ? 1 : 5);
		Computer computer = new Computer(input);
		computer.getInputs().add(inputValue);
		computer.run();
		return (computer.getLastOutput());
	}
}