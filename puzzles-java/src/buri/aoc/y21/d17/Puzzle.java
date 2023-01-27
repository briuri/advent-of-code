package buri.aoc.y21.d17;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.List;

/**
 * Day 17: Trick Shot
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(45L, 1, false);
		assertRun(10878L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(112L, 1, false);
		assertRun(4716L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the highest y position it reaches on this trajectory?
	 *
	 * Part 2:
	 * How many distinct initial velocity values cause the probe to be within the target area after any step?
	 */
	protected long runLong(Part part, List<String> input) {
		String[] tokens = input.get(0).split(": ")[1].split(", ");
		String[] x = tokens[0].substring(2).split("\\.\\.");
		String[] y = tokens[1].substring(2).split("\\.\\.");
		int targetMinX = Integer.parseInt(x[0]);
		int targetMaxX = Integer.parseInt(x[1]);
		int targetMinY = Integer.parseInt(y[0]);
		int targetMaxY = Integer.parseInt(y[1]);

		long maxY = Long.MIN_VALUE;
		long successes = 0;
		for (int xV = 1; xV <= targetMaxX; xV++) {
			for (int yV = targetMinY; yV < (targetMinY * -1); yV++) {
				Pair<Integer> probe = new Pair<>(0, 0);
				Pair<Integer> velocity = new Pair<>(xV, yV);
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