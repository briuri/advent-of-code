package buri.aoc.y19.d09;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.data.intcode.Computer;
import buri.aoc.BasePuzzle;

/**
 * Day 09: Sensor Boost
 * 
 * @author Brian Uri!
 */
public class Day09 extends BasePuzzle {

	/**
	 * Returns the input file as a list of longs 
	 */
	public static List<Long> getInput(int fileIndex) {
		List<Long> list = new ArrayList<>();
		for (String input : readFile("2019/09", fileIndex).get(0).split(",")) {
			list.add(Long.valueOf(input));
		}
		return (list);
	}
	
	/**
	 * Part 1:
	 * What BOOST keycode does it produce?
	 * 
	 * Part 2:
	 * What are the coordinates of the distress signal?
	 */
	public static long getResult(Part part, List<Long> program) {
		long input = part == Part.ONE ? 1 : 2;
		Computer computer = new Computer(program);
		computer.getInputs().add(input);
		computer.run();
		return (computer.getLastOutput());
	}
}