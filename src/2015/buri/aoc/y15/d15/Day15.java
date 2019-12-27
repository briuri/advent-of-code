package buri.aoc.y15.d15;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 15: Science for Hungry People
 * 
 * @author Brian Uri!
 */
public class Day15 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie
	 * you can make?
	 * 
	 * Part 2:
	 * What is the total score of the highest-scoring cookie you can make with a calorie total of 500?
	 */
	public static int getResult(Part part, List<String> input) {
		int[][] ing = new int[4][5];
		ing[0] = new int[] { 4, -2, 0, 0, 5 };
		ing[1] = new int[] { 0, 5, -1, 0, 8 };
		ing[2] = new int[] { -1, 0, 5, 0, 6 };
		ing[3] = new int[] { 0, 0, -2, 2, 1 };

		int maxScore = Integer.MIN_VALUE;
		for (int a = 0; a < 101; a++) {
			for (int b = 0; b < 101; b++) {
				for (int c = 0; c < 101; c++) {
					for (int d = 0; d < 101; d++) {
						if (a + b + c + d != 100) {
							continue;
						}
						int cap = Math.max(0, ing[0][0] * a + ing[1][0] * b + ing[2][0] * c + ing[3][0] * d);
						int dur = Math.max(0, ing[0][1] * a + ing[1][1] * b + ing[2][1] * c + ing[3][1] * d);
						int fla = Math.max(0, ing[0][2] * a + ing[1][2] * b + ing[2][2] * c + ing[3][2] * d);
						int tex = Math.max(0, ing[0][3] * a + ing[1][3] * b + ing[2][3] * c + ing[3][3] * d);
						int cal = Math.max(0, ing[0][4] * a + ing[1][4] * b + ing[2][4] * c + ing[3][4] * d);
						int score = cap * dur * fla * tex;
						if (part == Part.ONE || (part == Part.TWO && cal == 500)) {
							maxScore = Math.max(maxScore, score);
						}
					}
				}
			}
		}
		return (maxScore);
	}
}
