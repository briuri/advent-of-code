package buri.aoc.y18.d10;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.grid.CharGrid;
import buri.aoc.common.data.tuple.Pair;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Day 10: The Stars Align
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		// HI
		assertRun("■   ■  ■■■\n", 1, false);
		// PLBPGFRR
		assertRun("■■■■■   ■       ■■■■■   ■■■■■    ■■■■   ■■■■■■  ■■■■■   ■■■■■ \n", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("10519", 0, true);
	}

	/**
	 * Part 1:
	 * What message will eventually appear in the sky?
	 *
	 * Part 2:
	 * How many seconds would they have needed to wait for that message to appear?
	 */
	protected String runString(Part part, List<String> input) {
		List<Position> positions = new ArrayList<>();
		for (String line : input) {
			positions.add(new Position(line));
		}

		// Iterate over the position changes and find the time when the positions are closest together.
		int minTime = 0;
		BigInteger minArea = null;
		for (int i = 0; i < 20000; i++) {
			BigInteger area = getRectangleBounds(positions);
			// Points are converging.
			if (minArea == null || area.compareTo(minArea) < 0) {
				minArea = area;
				minTime = i;
			}
			// Points are breaking apart again. Stop evaluating.
			else if (area.compareTo(minArea) >= 0) {
				break;
			}
			for (Position position : positions) {
				position.move(1);
			}
		}
		if (part == Part.ONE) {
			// Paint the grid at the correct time.
			for (Position position : positions) {
				position.reset();
				position.move(minTime);
			}
			// Shift output to origin in console.
			Position offset = getOffset(positions);
			int maxX = Integer.MIN_VALUE;
			for (Position position : positions) {
				position.offset(offset);
				maxX = Math.max(maxX, position.getX());
			}
			CharGrid grid = new CharGrid(new Pair(maxX + 1, 10));
			for (Position position : positions) {
				grid.set(position, '■');
			}
			return (grid.toString());
		}
		// Part TWO
		return (String.valueOf(minTime));
	}

	/**
	 * Find the area of the rectangular bounds of all positions. The rectangle with the smallest area will be the grid
	 * with the visible answer.
	 */
	public static BigInteger getRectangleBounds(List<Position> positions) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (Position p : positions) {
			minX = Math.min(minX, p.getX());
			maxX = Math.max(maxX, p.getX());
			minY = Math.min(minY, p.getY());
			maxY = Math.max(maxY, p.getY());
		}
		BigInteger xLength = PuzzleMath.toBigInt(maxX - minX);
		BigInteger yLength = PuzzleMath.toBigInt(maxY - minY);
		return xLength.multiply(yLength);
	}

	/**
	 * Find the offset (dead whitespace) so we can shift the output to the origin.
	 */
	public static Position getOffset(List<Position> positions) {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		for (Position p : positions) {
			minX = Math.min(minX, p.getX());
			minY = Math.min(minY, p.getY());
		}
		return (new Position(minX, minY));
	}
}