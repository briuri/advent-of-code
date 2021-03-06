package buri.aoc.y19.d19;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.intcode.Computer;
import buri.aoc.data.tuple.Pair;

/**
 * Day 19: Tractor Beam
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

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
	 * How many points are affected by the tractor beam in the 50x50 area closest to the emitter?
	 * 
	 * Part 2:
	 * What value do you get if you take that point's X coordinate, multiply it by 10000, then add the point's Y
	 * coordinate?
	 */
	public static long getResult(Part part, List<Long> program) {
		if (part == Part.ONE) {
			long sum = 0;
			for (long y = 0; y < 50; y++) {
				for (long x = 0; x < 50; x++) {
					long out = run(program, new Pair(x, y));
					sum += out;
				}
			}
			return (sum);
		}

		// Part TWO
		final int SIZE = 100;
		long y = SIZE + 1;
		long x = 0;
		// Search row by row from lower left corner of beam.
		while (true) {
			// Skip ahead in current row to first tractor beam.
			while (true) {
				Pair<Long> lowerLeft = new Pair(x, y);
				if (run(program, lowerLeft) == 1) {
					break;
				}
				x += 1;
			}
			Pair<Long> lowerRight = new Pair(x + SIZE - 1, y);
			Pair<Long> upperLeft = new Pair(x, y - SIZE + 1);
			Pair<Long> upperRight = new Pair(x + SIZE - 1, y - SIZE + 1);
			// Boolean short-circuit avoids unnecessary corner checks.
			if (run(program, lowerRight) == 1 && run(program, upperLeft) == 1 && run(program, upperRight) == 1) {
				return (upperLeft.getX() * 10000 + upperLeft.getY());
			}
			y += 1;
		}
	}

	/**
	 * Runs the Intcode computer against the given point.
	 */
	private static long run(List<Long> program, Pair<Long> position) {
		Computer computer = new Computer(program);
		computer.getInputs().add(position.getX());
		computer.getInputs().add(position.getY());
		computer.run();
		return (computer.getOutputs().remove(0));
	}
}