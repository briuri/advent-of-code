package buri.aoc.y20.d04;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 04: Passport Processing
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(2L, 1, false);
		assertRun(237L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(4L, 3, false);
		assertRun(172L, 0, true);
	}

	/**
	 * Part 1:
	 * In your batch file, how many passports are valid?
	 *
	 * Part 2:
	 * In your batch file, how many passports are valid?
	 */
	protected long runLong(Part part, List<String> input) {
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