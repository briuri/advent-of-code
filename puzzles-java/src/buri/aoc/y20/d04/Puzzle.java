package buri.aoc.y20.d04;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

/**
 * Day 04: Passport Processing
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * In your batch file, how many passports are valid?
	 *
	 * Part 2:
	 * In your batch file, how many passports are valid?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Passport> list = new ArrayList<>();
		StringBuffer buffer = new StringBuffer();
		for (String line : input) {
			if (line.length() > 0) {
				buffer.append(" ").append(line);
			}
			else {
				list.add(new Passport(buffer.toString()));
				buffer.setLength(0);
			}
		}

		int valid = 0;
		for (Passport data : list) {
			if (data.isValid(part)) {
				valid++;
			}
		}
		return (valid);
	}
}