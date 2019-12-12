package buri.aoc.y19.d11;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;
import buri.aoc.data.Direction;
import buri.aoc.data.Pair;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.intcode.Computer;

/**
 * Day 11: Space Police
 * 
 * @author Brian Uri!
 */
public class Day11 extends Puzzle {

	private static final char BLACK = '#';
	private static final char WHITE = '.';
	private static final char DEFAULT_BLACK = ' ';
	private static final char VISUAL_WHITE = '■';
	
	/**
	 * Returns the input file as a list of longs 
	 */
	public static List<Long> getInput(int fileIndex) {
		List<Long> list = new ArrayList<>();
		for (String input : readFile("2019/11", fileIndex).get(0).split(",")) {
			list.add(Long.valueOf(input));
		}
		return (list);
	}
	
	/**
	 * Part 1:
	 * How many panels does it paint at least once?
	 * 
	 * Part 2:
	 * After starting the robot on a single white panel instead, what registration identifier does it paint on your hull?
	 */
	public static int getResult(Part part, List<Long> program) {
		int size = (part == Part.ONE ? 200 : 90);
		CharGrid hull = new CharGrid(size); 
		for (int y = 0; y < hull.getSize(); y++) {
			for (int x = 0; x < hull.getSize(); x++) {
				hull.set(x, y, DEFAULT_BLACK);
			}
		}
		
		Direction direction = Direction.UP;
		Pair position = new Pair(hull.getSize() / 2, hull.getSize() / 2);
		hull.set(position, part == Part.ONE ? BLACK : WHITE);
		Computer computer = new Computer(program);
		while (true) {
			long input = (hull.get(position) == WHITE) ? 1 : 0;
			computer.run(input);
			List<Long> outputs = computer.getOutputs();
			if (outputs.isEmpty()) {
				break;
			}
			int nextColor = Long.valueOf(outputs.remove(0)).intValue();
			long nextTurn = outputs.remove(0);
			hull.set(position, nextColor == 0 ? BLACK : WHITE);
			direction = (nextTurn == 0 ? direction.turnLeft() : direction.turnRight());
			position.move(direction);
		}
		if (part == Part.ONE) {
			int panels = 0;
			for (int y = 0; y < hull.getSize(); y++) {
				for (int x = 0; x < hull.getSize(); x++) {
					if (hull.get(x, y) != DEFAULT_BLACK) {
						panels++;
					}
				}
			}
			return (panels);
		}
		
		// Part TWO		
		// Make easier to read.
		for (int y = 0; y < hull.getSize(); y++) {
			for (int x = 0; x < hull.getSize(); x++) {
				if (hull.get(x, y) == BLACK) {
					hull.set(x, y, DEFAULT_BLACK);
				}
				if (hull.get(x, y) == WHITE) {
					hull.set(x, y, VISUAL_WHITE);
				}
			}
		}
		System.out.println(hull);
		return (0);
	}
}