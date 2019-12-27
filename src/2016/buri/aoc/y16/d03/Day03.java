package buri.aoc.y16.d03;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 3: Squares With Three Sides
 * 
 * @author Brian Uri!
 */
public class Day03 extends BasePuzzle {

	/**
	 * Returns input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * How many of the listed triangles are possible?
	 * 
	 * Part 2:
	 * Reading by columns, how many of the listed triangles are possible?
	 */
	public static int getResult(Part part, List<String> input) {
		int possible = 0;
		if (part == Part.ONE) {
			for (String triangle : input) {
				int a = Integer.valueOf(triangle.substring(0, 5).trim());
				int b = Integer.valueOf(triangle.substring(5, 10).trim());
				int c = Integer.valueOf(triangle.substring(10, triangle.length()).trim());
				if (isPossibleTriangle(a, b, c)) {
					possible++;
				}
			}
		}
		else {
			for (int i = 0; i < input.size(); i = i + 3) {
				for (int j = 0; j < 3; j++) {
					int a = Integer.valueOf(input.get(i).substring(5 * j, 5 * (j + 1)).trim());
					int b = Integer.valueOf(input.get(i + 1).substring(5 * j, 5 * (j + 1)).trim());
					int c = Integer.valueOf(input.get(i + 2).substring(5 * j, 5 * (j + 1)).trim());
					if (isPossibleTriangle(a, b, c)) {
						possible++;
					}
				}
			}
		}
		return (possible);
	}

	/**
	 * Compares 3 side lengths for possible triangles. In a valid triangle, the sum of any two sides must be larger than
	 * the remaining side.
	 */
	private static boolean isPossibleTriangle(int a, int b, int c) {
		return ((a + b > c) && (b + c > a) && (c + a > b));
	}
}