package buri.aoc.y18.d13;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 13: Mine Cart Madness
 * 
 * @author Brian Uri!
 */
public class Day13 extends BasePuzzle {

	/**
	 * Returns input file as a Tracks object.
	 */
	public static Tracks getInput(int fileIndex) {
		return (new Tracks(readFile(fileIndex)));
	}

	/**
	 * Part 1:
	 * You'd like to know the location of the first crash.
	 * 
	 * Part 2:
	 * What is the location of the last cart at the end of the first tick where it is the only cart left?
	 */
	public static String getResult(Part part, Tracks tracks) {
		while (true) {
			tracks.move();
			if (part == Part.ONE && tracks.getFirstCollision() != null) {
				return (tracks.getFirstCollision());
			}
			if (part == Part.TWO && tracks.getCarts().size() == 1) {
				return (tracks.getLastCartPosition().toString());
			}
		}
	}
}