package buri.aoc.y15.d06;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.grid.IntGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Day 6: Probably a Fire Hazard
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
	 * After following the instructions, how many lights are lit?
	 * 
	 * Part 2:
	 * What is the total brightness of all lights combined after following Santa's instructions?
	 */
	public static int getResult(Part part, List<String> input) {
		IntGrid grid = new IntGrid(new Pair(1000, 1000));
		for (String command : input) {
			String[] tokens = command.split(" ");
			Pair<Integer> lowerBound = new Pair(tokens[tokens.length - 3], Integer.class);
			Pair<Integer> upperBound = new Pair(tokens[tokens.length - 1], Integer.class);
			for (int y = lowerBound.getY(); y <= upperBound.getY(); y++) {
				for (int x = lowerBound.getX(); x <= upperBound.getX(); x++) {
					Integer value;
					if (part == Part.ONE) {
						if (tokens[1].equals("on")) {
							value = 1;
						}
						else if (tokens[1].equals("off")) {
							value = 0;
						}
						else {
							value = (grid.get(x, y) == 1 ? 0 : 1);
						}
					}
					else { // Part TWO
						if (tokens[1].equals("on")) {
							value = grid.get(x, y) + 1;
						}
						else if (tokens[1].equals("off")) {
							value = Math.max(0, grid.get(x, y) - 1);
						}
						else {
							value = grid.get(x, y) + 2;
						}
					}
					grid.set(x, y, value);
				}
			}
		}

		int lit = 0;
		for (int y = 0; y < grid.getHeight(); y++) {
			for (int x = 0; x < grid.getWidth(); x++) {
				lit += grid.get(x, y);
			}
		}
		return (lit);
	}
}