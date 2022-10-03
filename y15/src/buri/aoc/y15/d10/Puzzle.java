package buri.aoc.y15.d10;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 10: Elves Look, Elves Say
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * Starting with the digits in your puzzle input, apply this process 40 times. What is the length of the result?
	 * 
	 * Part 2:
	 * Now, starting again with the digits in your puzzle input, apply this process 50 times. What is the length of the
	 * new result?
	 */
	public static int getResult(Part part, String input) {
		int times = (part == Part.ONE ? 40 : 50);
		for (int i = 0; i < times; i++) {
			StringBuilder builder = new StringBuilder();
			int lookCount = 0;
			char currentChar = ' ';
			for (int index = 0; index < input.length(); index++) {
				char value = input.charAt(index);
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
			input = builder.toString();
		}
		return (input.length());
	}
}