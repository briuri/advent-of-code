package buri.aoc.y19.d17;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;
import buri.aoc.data.intcode.Computer;

/**
 * Day 17: Set and Forget
 * 
 * @author Brian Uri!
 */
public class Day17 extends Puzzle {

	/**
	 * Returns the input file as an Intcode program
	 */
	public static List<Long> getInput(int fileIndex) {
		List<Long> list = new ArrayList<>();
		for (String input : readFile("2019/17", fileIndex).get(0).split(",")) {
			list.add(Long.valueOf(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * What is the sum of the alignment parameters for the scaffold intersections?
	 * 
	 * Part 2:
	 * After visiting every part of the scaffold at least once, how much dust does the vacuum robot report it has
	 * collected?
	 */
	public static long getResult(Part part, List<Long> program) {
		if (part == Part.ONE) {
			Computer computer = new Computer(program);
			computer.run();
			VacuumGrid grid = new VacuumGrid(computer.getOutputs());
			return (grid.getParameters());
		}

		// Part TWO
		Computer computer = new Computer(program, 2L);
		
		// Function definitions calculated by hand against the Part One grid.
		String mainRoutine = "A,B,A,B,A,C,A,C,B,C\n";
		String functionA = "R,6,L,10,R,10,R,10\n";
		String functionB = "L,10,L,12,R,10\n";
		String functionC = "R,6,L,12,L,10\n";
		String view = "y\n";
		
		List<Long> inputs = new ArrayList<>();
		inputs.addAll(toAscii(mainRoutine));
		inputs.addAll(toAscii(functionA));
		inputs.addAll(toAscii(functionB));
		inputs.addAll(toAscii(functionC));
		inputs.addAll(toAscii(view));
		computer.getInputs().addAll(inputs);
		computer.run();
		return (computer.getLastOutput());
	}

	/**
	 * Converts the program into Intcode inputs.
	 */
	private static List<Long> toAscii(String routine) {
		List<Long> list = new ArrayList<>();
		for (char value : routine.toCharArray()) {
			list.add(Long.valueOf((int) value));
		}		
		return (list);
	}
}