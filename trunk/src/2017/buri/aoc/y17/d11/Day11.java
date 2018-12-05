package buri.aoc.y17.d11;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day11 extends Puzzle {

	/**
	 * Input: One line with comma-separated directions.
	 * Output: List of string directions.
	 */
	public static List<String> getInput(int fileIndex) {
		List<String> data = new ArrayList<>();
		for (String rawData : readFile("2017/11", fileIndex).get(0).split(",")) {
			data.add(rawData);
		}
		return (data);
	}

	/**
	 * 
	 */
	public static String getResult(Part part, List<String> input) {
		// Three hex directions are orthogonal. Reduce to directions that don't cancel each other out.
		int nwCount = 0;
		int nCount = 0;
		int neCount = 0;
		for (String direction : input) {
			if (direction.equals("nw")) {
				nwCount++;
			}
			if (direction.equals("se")) {
				nwCount--;
			}
			if (direction.equals("n")) {
				nCount++;
			}
			if (direction.equals("s")) {
				nCount--;
			}
			if (direction.equals("ne")) {
				neCount++;
			}
			if (direction.equals("sw")) {
				neCount--;
			}
		}
		System.out.print("Reduced directions: ");
		System.out.print(nCount + " N, ");
		System.out.print(nwCount + " NW, ");
		System.out.print(neCount + " NE");

		// TODO: Map hex grid to x,y coordinate system then find steps for reduced directions.
		if (part == Part.ONE) {
			return ("");
		}
		return ("");
	}
}