package buri.aoc.y15.d02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 2: I Was Told There Would Be No Math
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * How many total square feet of wrapping paper should they order?
	 * 
	 * Part 2:
	 * How many total feet of ribbon should they order?
	 */
	public static int getResult(Part part, List<String> input) {
		int total = 0;
		for (String line : input) {
			String[] tokens = line.split("x");
			List<Integer> dimensions = new ArrayList<>();
			dimensions.add(Integer.valueOf(tokens[0]));
			dimensions.add(Integer.valueOf(tokens[1]));
			dimensions.add(Integer.valueOf(tokens[2]));
			Collections.sort(dimensions);

			if (part == Part.ONE) {
				int smallestSide = dimensions.get(0) * dimensions.get(1);
				int surface = 2 * smallestSide + 2 * dimensions.get(1) * dimensions.get(2) + 2 * dimensions.get(2)
					* dimensions.get(0);
				total += surface + smallestSide;
			}
			else { // Part TWO
				int smallestPerimeter = 2 * dimensions.get(0) + 2 * dimensions.get(1);
				int volume = dimensions.get(0) * dimensions.get(1) * dimensions.get(2);
				total += volume + smallestPerimeter;
			}
		}
		return (total);
	}

}