package buri.aoc.y19.d05;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.intcode.Computer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 05: Sunny with a Chance of Asteroids
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(15259545L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(999L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(7616021L, result);
	}

	/**
	 * Part 1:
	 * After providing 1 to the only input instruction and passing all the tests, what diagnostic code does the program
	 * produce?
	 *
	 * Part 2:
	 * What is the diagnostic code for system ID 5?
	 */
	public static long getResult(Part part, List<String> input) {
		long inputValue = (part == Part.ONE ? 1 : 5);
		Computer computer = new Computer(input);
		computer.getInputs().add(inputValue);
		computer.run();
		return (computer.getLastOutput());
	}
}