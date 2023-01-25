package buri.aoc.y21.d17;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 17: Trick Shot
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(45L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1).get(0)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(10878L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(112L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1).get(0)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(4716L, result);
	}

	/**
	 * Part 1:
	 * What is the highest y position it reaches on this trajectory?
	 *
	 * Part 2:
	 * How many distinct initial velocity values cause the probe to be within the target area after any step?
	 */
	public static long getResult(Part part, String input) {
		String[] tokens = input.split(": ")[1].split(", ");
		String[] x = tokens[0].substring(2).split("\\.\\.");
		String[] y = tokens[1].substring(2).split("\\.\\.");
		int targetMinX = Integer.valueOf(x[0]);
		int targetMaxX = Integer.valueOf(x[1]);
		int targetMinY = Integer.valueOf(y[0]);
		int targetMaxY = Integer.valueOf(y[1]);

		long maxY = Long.MIN_VALUE;
		long successes = 0;
		for (int xV = 1; xV <= targetMaxX; xV++) {
			for (int yV = targetMinY; yV < (targetMinY * -1); yV++) {
				Pair<Integer> probe = new Pair<Integer>(0, 0);
				Pair<Integer> velocity = new Pair<Integer>(xV, yV);
				long localMaxY = Long.MIN_VALUE;
				while (true) {
					probe.setX(probe.getX() + velocity.getX());
					probe.setY(probe.getY() + velocity.getY());
					velocity.setX(Math.max(0, velocity.getX() - 1));
					velocity.setY(velocity.getY() - 1);
					localMaxY = Math.max(localMaxY, probe.getY());
					if (probe.getX() >= targetMinX && probe.getX() <= targetMaxX && probe.getY() >= targetMinY && probe.getY() <= targetMaxY) {
						maxY = Math.max(maxY, localMaxY);
						successes++;
						break;
					}
					// Quit when we have passed the target area.
					if (probe.getY() < targetMinY) {
						break;
					}
				}
			}
		}
		return (part == Part.ONE ? maxY : successes);
	}
}