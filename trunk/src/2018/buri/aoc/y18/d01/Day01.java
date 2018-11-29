package buri.aoc.y18.d01;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day01 extends Puzzle {

	/**
	 * Input: 
	 * Output: 
	 */
	public static List<List<String>> getInput(int fileIndex) {
		List<List<String>> rows = new ArrayList<>();
		try {
			for (String rawRow : Files.readAllLines(getInputPath("2018/01", fileIndex))) {
				rows.add(Arrays.asList(rawRow.split(" ")));
			}
			return (rows);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file", e);
		}
	}
	
	/**
	 * 
	 */
	public static int getResult(Part part, List<List<String>> input) {
		return (0);
	}
}