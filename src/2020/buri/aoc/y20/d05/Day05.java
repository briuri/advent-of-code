package buri.aoc.y20.d05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 05: Binary Boarding
 *
 * @author Brian Uri!
 */
public class Day05 extends BasePuzzle {

	/**
	 * Returns the input file with one boarding pass per line.
	 */
	public static List<Pass> getInput(int fileIndex) {
		List<Pass> list = new ArrayList<>();
		for (String input : readFile(fileIndex)) {
			list.add(new Pass(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * What is the highest seat ID on a boarding pass?
	 *
	 * Part 2:
	 * What is the ID of your seat?
	 */
	public static int getResult(Part part, List<Pass> input) {
		List<Integer> seats = new ArrayList<>();
		for (Pass pass : input) {
			seats.add(pass.getId());
		}
		Collections.sort(seats);
		if (part == Part.ONE) {
			return (seats.get(seats.size() - 1));
		}

		for (int i = 1; i < seats.size(); i++) {
			if (seats.get(i) != seats.get(i - 1) + 1) {
				return (seats.get(i) - 1);
			}
		}
		throw new RuntimeException("Plane is full.");
	}
}