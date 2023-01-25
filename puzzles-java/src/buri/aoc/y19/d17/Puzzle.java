package buri.aoc.y19.d17;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.intcode.Computer;

/**
 * Day 17: Set and Forget
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the sum of the alignment parameters for the scaffold intersections?
	 *
	 * Part 2:
	 * After visiting every part of the scaffold at least once, how much dust does the vacuum robot report it has
	 * collected?
	 */
	public static long getResult(Part part, List<String> input) {
		if (part == Part.ONE) {
			Computer computer = new Computer(input);
			computer.run();
			VacuumGrid grid = new VacuumGrid(computer.getOutputs());
			return (grid.getParameters());
		}

		// Part TWO
		Computer computer = new Computer(input, 2L);

		// Function definitions calculated by hand against the Part One grid.
		String mainRoutine = "A,B,A,B,A,C,A,C,B,C";
		String functionA = "R,6,L,10,R,10,R,10";
		String functionB = "L,10,L,12,R,10";
		String functionC = "R,6,L,12,L,10";
		String view = "n";

		List<Long> inputs = new ArrayList<>();
		inputs.addAll(Computer.toAscii(mainRoutine));
		inputs.addAll(Computer.toAscii(functionA));
		inputs.addAll(Computer.toAscii(functionB));
		inputs.addAll(Computer.toAscii(functionC));
		inputs.addAll(Computer.toAscii(view));
		computer.getInputs().addAll(inputs);
		computer.run();
		return (computer.getLastOutput());
	}
}