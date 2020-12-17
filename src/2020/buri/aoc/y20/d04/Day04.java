package buri.aoc.y20.d04;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 04: Passport Processing
 *
 * @author Brian Uri!
 */
public class Day04 extends BasePuzzle {

	/**
	 * Returns the input file as a list of passports.
	 */
	public static List<Passport> getInput(int fileIndex) {
		List<Passport> list = new ArrayList<>();
		StringBuffer buffer = new StringBuffer();
		for (String input : readFile(fileIndex)) {
			if (input.length() > 0) {
				buffer.append(" ").append(input);
			}
			else {
				list.add(new Passport(buffer.toString()));
				buffer.setLength(0);
			}
		}
		return (list);
	}

	/**
	 * Part 1:
	 * In your batch file, how many passports are valid?
	 *
	 * Part 2:
	 * In your batch file, how many passports are valid?
	 */
	public static int getResult(Part part, List<Passport> input) {
		int valid = 0;
		for (Passport data : input) {
			if (data.isValid(part)) {
				valid++;
			}
		}
		return (valid);
	}
}