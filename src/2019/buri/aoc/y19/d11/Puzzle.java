package buri.aoc.y19.d11;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.Direction;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.intcode.Computer;
import buri.aoc.data.tuple.Pair;

/**
 * Day 11: Space Police
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	private static final char BLACK = '#';
	private static final char WHITE = '.';
	private static final char DEFAULT_BLACK = ' ';
	private static final char VISUAL_WHITE = '■';

	/**
	 * Returns the input file as a list of longs
	 */
	public static List<Long> getInput(int fileIndex) {
		List<Long> list = new ArrayList<>();
		for (String input : readFile(fileIndex).get(0).split(",")) {
			list.add(Long.valueOf(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * How many panels does it paint at least once?
	 * 
	 * Part 2:
	 * After starting the robot on a single white panel instead, what registration identifier does it paint on your
	 * hull?
	 */
	public static String getResult(Part part, List<Long> program) {
		Pair<Integer> size = (part == Part.ONE ? new Pair(140, 128) : new Pair(85, 12));
		CharGrid hull = new CharGrid(size);
		for (int y = 0; y < hull.getHeight(); y++) {
			for (int x = 0; x < hull.getWidth(); x++) {
				hull.set(x, y, DEFAULT_BLACK);
			}
		}
		Direction direction = Direction.UP;
		Pair<Integer> position = hull.getCenterPosition();
		hull.set(position, part == Part.ONE ? BLACK : WHITE);
		Computer computer = new Computer(program);
		while (true) {
			long input = (hull.get(position) == WHITE) ? 1 : 0;
			computer.getInputs().add(input);
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
					firstPaint = new Pair(x, y);
				}
				if (value == BLACK) {
					hull.set(x, y, DEFAULT_BLACK);
				}
				if (value == WHITE) {
					hull.set(x, y, VISUAL_WHITE);
				}
			}
		}
		StringBuffer buffer = new StringBuffer();
		for (int y = firstPaint.getY(); y < hull.getHeight(); y++) {
			for (int x = firstPaint.getX(); x < hull.getWidth(); x++) {
				buffer.append(hull.get(x, y));
			}
			buffer.append("\n");
		}
		return (buffer.toString());
	}
}