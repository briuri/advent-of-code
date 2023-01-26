package buri.aoc.y18.d22;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.List;

/**
 * Day 22: Mode Maze
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(6323L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(982L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the total risk level for the smallest rectangle that includes 0,0 and the target's coordinates?
	 *
	 * Part 2:
	 * What is the fewest number of minutes you can take to reach the target?
	 */
	protected long runLong(Part part, List<String> input) {
		int depth = Integer.parseInt(input.get(0).split(": ")[1]);
		Pair target = new Pair(input.get(1).split(": ")[1], Integer.class);
		Maze maze = new Maze(depth, target);
		return (part == Part.ONE ? maze.getRiskLevel() : maze.getMinutes());
	}
}