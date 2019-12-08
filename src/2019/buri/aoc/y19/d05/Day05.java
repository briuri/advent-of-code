package buri.aoc.y19.d05;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;
import buri.aoc.data.intcode.Computer;

/**
 * Day 05: Sunny with a Chance of Asteroids
 * 
 * @author Brian Uri!
 */
public class Day05 extends Puzzle {

	/**
	 * Returns the input file as a list of integers
	 */
	public static List<Integer> getInput(int fileIndex) {
		List<Integer> list = new ArrayList<>();
		for (String input : readFile("2019/05", fileIndex).get(0).split(",")) {
			list.add(Integer.valueOf(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * After providing 1 to the only input instruction and passing all the tests, what diagnostic code does the program
	 * produce?
	 * 
	 * Part 2:
	 * What is the diagnostic code for system ID 5?
	 */
	public static int getResult(Part part, List<Integer> program) {
		int input = (part == Part.ONE ? 1 : 5);
		Computer computer = new Computer(program, input);
		return (computer.run(null));
	}
}