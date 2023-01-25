package buri.aoc.y15.d25;

import java.util.HashSet;
import java.util.Set;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 25: Let It Snow
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(new Pair(2947, 3029));
		toConsole(result);
		assertEquals(19980801, result);
	}

	/**
	 * Part 1:
	 * What code do you give the machine?
	 */
	public static long getResult(Pair<Integer> target) {
		Pair pair = new Pair(1, 1);
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