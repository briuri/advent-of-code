package buri.aoc.y20.d04;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 04: Passport Processing
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(2, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(237, result);
	}

	@Test
	public void testPart2Examples() {
//		assertEquals(0, Day04.getResult(Part.TWO, Day04.getInput(2)));
		assertEquals(4, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(172, result);
	}

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