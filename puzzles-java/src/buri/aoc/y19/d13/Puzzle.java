package buri.aoc.y19.d13;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.grid.IntGrid;
import buri.aoc.common.data.intcode.Computer;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.List;

/**
 * Day 13: Care Package
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(286L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(14538L, 0, true);
	}

	private static final int BLOCK = 2;
	private static final int PADDLE = 3;
	private static final int BALL = 4;

	/**
	 * Part 1:
	 * How many block tiles are on the screen when the game exits?
	 *
	 * Part 2:
	 * What is your score after the last block is broken?
	 */
	protected long runLong(Part part, List<String> input) {
		IntGrid grid = new IntGrid(new Pair<>(37, 37));
		Computer computer = new Computer(input);
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
		computer = new Computer(input, 2L);
		Pair<Integer> ball = null;
		Pair<Integer> paddle = null;
		do {
			long joystick;
			if (ball == null || ball.getX() == paddle.getX()) {
				joystick = 0;
			}
			else if (ball.getX() > paddle.getX()) {
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
						paddle = new Pair<>(x, y);
					}
					else if (tile == BALL) {
						ball = new Pair<>(x, y);
					}
					grid.set(x, y, tile);
				}
			}
		} while (countBlocks(grid) != 0);
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