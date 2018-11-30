package buri.aoc.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day00 extends Puzzle {

	/**
	 * Input: 
	 * Output: 
	 */
	public static List<List<String>> getInput(int fileIndex) {
		List<List<String>> rows = new ArrayList<>();
		for (String rawRow : readFile("2018/00", fileIndex)) {
			rows.add(Arrays.asList(rawRow.split(" ")));
		}
		return (rows);
	}
	
	/**
	 * 
	 */
	public static int getResult(Part part, List<List<String>> input) {
		return (0);
	}
}