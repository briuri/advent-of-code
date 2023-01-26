package buri.aoc.y15.d10;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 10: Elves Look, Elves Say
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(252594L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(3579328L, 0, true);
	}

	/**
	 * Part 1:
	 * Starting with the digits in your puzzle input, apply this process 40 times. What is the length of the result?
	 *
	 * Part 2:
	 * Now, starting again with the digits in your puzzle input, apply this process 50 times. What is the length of the
	 * new result?
	 */
	protected long runLong(Part part, List<String> input) {
		String string = input.get(0);
		int times = (part == Part.ONE ? 40 : 50);
		for (int i = 0; i < times; i++) {
			StringBuilder builder = new StringBuilder();
			int lookCount = 0;
			char currentChar = ' ';
			for (int index = 0; index < string.length(); index++) {
				char value = string.charAt(index);
				if (value == currentChar) {
					lookCount++;
				}
				else {
					if (lookCount != 0) {
						builder.append(lookCount).append(currentChar);
					}
					currentChar = value;
					lookCount = 1;
				}
			}
			builder.append(lookCount).append(currentChar);
			string = builder.toString();
		}
		return (string.length());
	}
}