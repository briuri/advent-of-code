package buri.aoc.y20.d24;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 24: Lobby Layout
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
	 * After all of the instructions have been followed, how many tiles are left with the black side up?
	 *
	 * Part 2:
	 * How many tiles will be black after 100 days?
	 */
	public static long getResult(Part part, List<String> input) {
		Floor floor = new Floor(input);
		if (part == Part.TWO) {
			floor.cycleArt();
		}
		return (floor.getBlackCount());
	}
}