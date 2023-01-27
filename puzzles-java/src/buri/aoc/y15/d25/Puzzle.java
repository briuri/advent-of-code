package buri.aoc.y15.d25;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 25: Let It Snow
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(19980801L, 0, true);
	}

	/**
	 * Part 1:
	 * What code do you give the machine?
	 */
	protected long runLong(Part part, List<String> input) {
		int x = Integer.parseInt(input.get(0).split("row ")[1].split(",")[0]);
		int y = Integer.parseInt(input.get(0).split("column ")[1].split("\\.")[0]);
		Pair<Integer> target = new Pair<>(x, y);
		Pair<Integer> pair = new Pair<>(1, 1);
		long code = 20151125;
		Set<String> visited = new HashSet<>();
		while (!pair.equals(target)) {
			move(pair);
			if (visited.contains(pair.toString())) {
				throw new RuntimeException();
			}
			visited.add(pair.toString());
			code = getNextCode(code);
		}
		return (code);
	}

	/**
	 * Calculates the next code.
	 */
	private static long getNextCode(long code) {
		return (code * 252533) % 33554393;
	}

	/**
	 * Calculates the next position to insert a code. X is row count (vertical) and Y is column count (horizontal).
	 */
	private static void move(Pair<Integer> pair) {
		pair.setX(pair.getX() - 1);
		pair.setY(pair.getY() + 1);
		if (pair.getX() == 0) {
			pair.setX(pair.getY());
			pair.setY(1);
		}
	}
}