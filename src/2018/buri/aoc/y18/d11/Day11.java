package buri.aoc.y18.d11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day11 extends Puzzle {

	/**
	 * Part 1:
	 * What is the X,Y coordinate of the top-left fuel cell of the 3x3 square with the largest total power?
	 * 
	 * Part 2:
	 * What is the X,Y,size identifier of the square with the largest total power?
	 */
	public static String getResult(Part part, int serial) {
		final int gridSize = 300;
		Grid grid = new Grid(gridSize, serial);
		if (part == Part.ONE) {
			Grid reduction = grid.getReduction(3);
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