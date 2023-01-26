package buri.aoc.y21.d02;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Triple;
import org.junit.Test;

import java.util.List;

/**
 * Day 02: Dive!
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(150L, 1, false);
		assertRun(1524750L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(900L, 1, false);
		assertRun(1592426537L, 0, true);
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
	protected long runLong(Part part, List<String> input) {
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