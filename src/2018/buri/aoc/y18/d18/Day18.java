package buri.aoc.y18.d18;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 18: Settlers of the North Pole
 * 
 * @author Brian Uri!
 */
public class Day18 extends BasePuzzle {

	/**
	 * Returns input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * What will the total resource value of the lumber collection area be after 10 minutes?
	 * 
	 * Part 2:
	 * What will the total resource value of the lumber collection area be after 1000000000 minutes?
	 */
	public static int getResult(Part part, List<String> input) {
		int minutes = (part == Part.ONE ? 10 : 1000000000);
		Acreage acreage = new Acreage(input);
		return (acreage.evolve(minutes));
	}
}