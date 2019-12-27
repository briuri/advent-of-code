package buri.aoc.y18.d11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 11: Chronal Charge
 * 
 * @author Brian Uri!
 */
public class Day11 extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the X,Y coordinate of the top-left fuel cell of the 3x3 square with the largest total power?
	 * 
	 * Part 2:
	 * What is the X,Y,size identifier of the square with the largest total power?
	 * 
	 * TODO: Optimize Part 2 so it doesn't take so long (8 minutes for the puzzle input).
	 */
	public static String getResult(Part part, int serial) {
		final int gridSize = 300;
		PowerGrid grid = new PowerGrid(gridSize, serial);
		if (part == Part.ONE) {
			PowerGrid reduction = grid.getReduction(3);
			return (reduction.getMaxValuePosition().toString());
		}

		// Part TWO
		List<Long> maxValues = new ArrayList<>();
		Map<Integer, Position> positions = new HashMap<>();
		// Calculate all possible reductions and record the max value from each.
		for (int squareSumSize = 2; squareSumSize <= gridSize; squareSumSize++) {
			Position p = grid.getReduction(squareSumSize).getMaxValuePosition();
			maxValues.add(p.getValue());
			positions.put(squareSumSize, p);
		}

		// Find the max of all square sums and then trace back to the squareSumSize for that max.
		Long maxValue = Collections.max(maxValues);
		for (Integer squareSumSize : positions.keySet()) {
			Position position = positions.get(squareSumSize);
			if (position.getValue() == maxValue) {
				return (position.toString() + "," + squareSumSize);
			}
		}
		throw new RuntimeException("Could not find the max sum.");
	}
}