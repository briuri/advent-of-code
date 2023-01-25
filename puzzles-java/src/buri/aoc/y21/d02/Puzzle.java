package buri.aoc.y21.d02;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Triple;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 02: Dive!
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(150L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1524750L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(900L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1592426537L, result);
	}

	/**
	 * Part 1:
	 * forward X increases the horizontal position by X units.
	 * down X increases the depth by X units.
	 * up X decreases the depth by X units.
	 *
	 * What do you get if you multiply your final horizontal position by your final depth?
	 *
	 * Part 2:
	 * down X increases your aim by X units.
	 * up X decreases your aim by X units.
	 * forward X does two things:
	 * 		It increases your horizontal position by X units.
	 * 		It increases your depth by your aim multiplied by X.
	 *
	 * What do you get if you multiply your final horizontal position by your final depth?
	 */
	public static long getResult(Part part, List<String> input) {
		Triple<Integer> position = new Triple(0, 0, 0);
		for (String line : input) {
			String[] tokens = line.split(" ");
			String command = tokens[0];
			int x = Integer.valueOf(tokens[1]);

			if (part == Part.ONE) {
				if (command.equals("forward")) {
					position.setX(position.getX() + x);
				}
				else if (command.equals("up")) {
					position.setY(position.getY() - x);
				}
				else if (command.equals("down")) {
					position.setY(position.getY() + x);
				}
			}
			else {
				if (command.equals("forward")) {
					position.setY(position.getY() + position.getZ() * x);
					position.setX(position.getX() + x);
				}
				else if (command.equals("up")) {
					position.setZ(position.getZ() - x);
				}
				else if (command.equals("down")) {
					position.setZ(position.getZ() + x);
				}
			}
		}
		return (position.getX() * position.getY());
	}
}