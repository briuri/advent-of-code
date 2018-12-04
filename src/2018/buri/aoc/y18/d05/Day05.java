package buri.aoc.y18.d05;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day05 extends Puzzle {

	/**
	 * Input: 
	 * Output: 
	 */
	public static List<Data> getInput(int fileIndex) {
		List<Data> data = new ArrayList<>();
		for (String rawData : readFile("2018/00", fileIndex)) {
			data.add(new Data(rawData));
		}
		return (data);
	}
	
	/**
	 * 
	 */
	public static String getResult(Part part, List<Data> input) {
		for (Data data : input) {
			
		}
		if (part == Part.ONE) {
			return ("");
		}
		return ("");
	}
}