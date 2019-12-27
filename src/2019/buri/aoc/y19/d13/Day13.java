package buri.aoc.y19.d13;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.grid.IntGrid;
import buri.aoc.data.intcode.Computer;
import buri.aoc.data.tuple.Pair;

/**
 * Day 13: Care Package
 * 
 * @author Brian Uri!
 */
public class Day13 extends BasePuzzle {

	private static final int BLOCK = 2;
	private static final int PADDLE = 3;
	private static final int BALL = 4;

	/**
	 * Returns the input file as a list of longs.
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
	 * How many block tiles are on the screen when the game exits?
	 * 
	 * Part 2:
	 * What is your score after the last block is broken?
	 */
	public static int getResult(Part part, List<Long> program) {
		IntGrid grid = new IntGrid(new Pair(37, 37));
		Computer computer = new Computer(program);
		computer.run();
		List<Long> outputs = computer.getOutputs();
		while (!outputs.isEmpty()) {
			int x = outputs.remove(0).intValue();
			int y = outputs.remove(0).intValue();
			int tile = outputs.remove(0).intValue();
			grid.set(x, y, tile);
		}
		if (part == Part.ONE) {
			return (countBlocks(grid));
		}

		// Part TWO
		int score = 0;
		computer = new Computer(program, 2L);
		Pair ball = null;
		Pair paddle = null;
		while (true) {
			long joystick = 0;
			if (ball == null || ball.getX() == paddle.getX()) {
				joystick = 0;
			}
			else if (ball.getX().intValue() > paddle.getX().intValue()) {
				joystick = 1;
			}
			else {
				joystick = -1;
			}
			computer.getInputs().add(joystick);
			computer.run();
			outputs = computer.getOutputs();
			// Note: Entire grid does not repaint each time. Subsequent output lengths are shorter.
			while (!outputs.isEmpty()) {
				int x = outputs.remove(0).intValue();
				int y = outputs.remove(0).intValue();
				int tile = outputs.remove(0).intValue();
				if (x == -1L && y == 0L) {
					score = tile;
				}
				else {
					if (tile == PADDLE) {
						paddle = new Pair(x, y);
					}
					else if (tile == BALL) {
						ball = new Pair(x, y);
					}
					grid.set(x, y, tile);
				}
			}
			if (countBlocks(grid) == 0) {
				break;
			}
		}
		return (score);
	}

	/**
	 * Counts all blocks on the grid.
	 */
	private static int countBlocks(IntGrid grid) {
		int count = 0;
		for (int y = 0; y < grid.getHeight(); y++) {
			for (int x = 0; x < grid.getWidth(); x++) {
				if (grid.get(x, y) == BLOCK) {
					count++;
				}
			}
		}
		return (count);
	}

}