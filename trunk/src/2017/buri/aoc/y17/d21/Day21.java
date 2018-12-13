package buri.aoc.y17.d21;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day21 extends Puzzle {

	/**
	 * Input: One rule per line.
	 * Output: List of Rules
	 */
	public static List<Rule> getInput(int fileIndex) {
		List<Rule> rules = new ArrayList<>();
		for (String line : readFile("2017/21", fileIndex)) {
			rules.add(new Rule(line));
		}
		return (rules);
	}
	
	/**
	 * Part 1:
	 * How many pixels stay on after 5 iterations? 
	 * 
	 * Part 2:
	 * How many pixels stay on after 18 iterations?
	 */
	public static int getResult(List<Rule> input, int iterations) {
		Image image = new Image(input);
		for (int i = 0; i < iterations; i++) {
			image.fractalize();
		}
		return (image.getLitPixels());
	}
}