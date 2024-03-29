package buri.aoc.y19.d11;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.Direction;
import buri.aoc.common.data.grid.CharGrid;
import buri.aoc.common.data.intcode.Computer;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.List;

/**
 * Day 11: Space Police
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("2319", 0, true);
	}
	@Test
	public void testPart2() {
		// UERPRFGJ
		assertRun(" ■  ■ ■■■■ ■■■  ■■■  ■■■  ■■■■  ■■    ■■", 0, true);
	}

	private static final char BLACK = '#';
	private static final char WHITE = '.';
	private static final char DEFAULT_BLACK = ' ';
	private static final char VISUAL_WHITE = '■';

	/**
	 * Part 1:
	 * How many panels does it paint at least once?
	 *
	 * Part 2:
	 * After starting the robot on a single white panel instead, what registration identifier does it paint on your
	 * hull?
	 */
	protected String runString(Part part, List<String> input) {
		Pair<Integer> size = (part == Part.ONE ? new Pair<>(140, 128) : new Pair<>(85, 12));
		CharGrid hull = new CharGrid(size);
		for (int y = 0; y < hull.getHeight(); y++) {
			for (int x = 0; x < hull.getWidth(); x++) {
				hull.set(x, y, DEFAULT_BLACK);
			}
		}
		Direction direction = Direction.UP;
		Pair<Integer> position = hull.getCenterPosition();
		hull.set(position, part == Part.ONE ? BLACK : WHITE);
		Computer computer = new Computer(input);
		while (true) {
			long inputValue = (hull.get(position) == WHITE) ? 1 : 0;
			computer.getInputs().add(inputValue);
			computer.run();
			List<Long> outputs = computer.getOutputs();
			if (outputs.isEmpty()) {
				break;
			}
			long nextColor = outputs.remove(0);
			long nextTurn = outputs.remove(0);
			hull.set(position, nextColor == 0L ? BLACK : WHITE);
			direction = (nextTurn == 0L ? direction.turnLeft() : direction.turnRight());
			position.move(direction);
		}

		if (part == Part.ONE) {
			int panels = 0;
			for (int y = 0; y < hull.getHeight(); y++) {
				for (int x = 0; x < hull.getWidth(); x++) {
					if (hull.get(x, y) != DEFAULT_BLACK) {
						panels++;
					}
				}
			}
			return (String.valueOf(panels));
		}

		// Part TWO
		// Make easier to read and shift output to origin.
		Pair<Integer> firstPaint = null;
		for (int y = 0; y < hull.getHeight(); y++) {
			for (int x = 0; x < hull.getWidth(); x++) {
				char value = hull.get(x, y);
				if (value != DEFAULT_BLACK && firstPaint == null) {
					firstPaint = new Pair<>(x, y);
				}
				if (value == BLACK) {
					hull.set(x, y, DEFAULT_BLACK);
				}
				if (value == WHITE) {
					hull.set(x, y, VISUAL_WHITE);
				}
			}
		}
		StringBuilder builder = new StringBuilder();
		for (int y = firstPaint.getY(); y < hull.getHeight(); y++) {
			for (int x = firstPaint.getX(); x < hull.getWidth(); x++) {
				builder.append(hull.get(x, y));
			}
			builder.append("\n");
		}
		return (builder.toString());
	}
}