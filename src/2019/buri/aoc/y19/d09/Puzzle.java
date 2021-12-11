package buri.aoc.y19.d09;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.intcode.Computer;

/**
 * Day 09: Sensor Boost
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What BOOST keycode does it produce?
	 *
	 * Part 2:
	 * What are the coordinates of the distress signal?
	 */
	public static long getResult(Part part, List<String> input) {
		long inputValue = (part == Part.ONE ? 1 : 2);
		Computer computer = new Computer(input);
		computer.getInputs().add(inputValue);
		computer.run();
		return (computer.getLastOutput());
	}
}