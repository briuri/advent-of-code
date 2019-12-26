package buri.aoc.y18.d10;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;
import buri.aoc.data.grid.CharGrid;

/**
 * Day 10: The Stars Align
 * 
 * @author Brian Uri!
 */
public class Day10 extends BasePuzzle {

	/**
	 * Returns input file as a list of Positions.
	 */
	public static List<Position> getInput(int fileIndex) {
		List<Position> positions = new ArrayList<>();
		for (String data : readFile("2018/10", fileIndex)) {
			positions.add(new Position(data));
		}
		return (positions);
	}
	
	/**
	 * Part 1:
	 * What message will eventually appear in the sky?
	 * 
	 * Part 2:
	 * How many seconds would they have needed to wait for that message to appear?
	 */
	public static String getResult(Part part, List<Position> input) {
		// Iterate over the position changes and find the time when the positions are closest together.
		int minTime = 0;
		BigInteger minArea = null;
		for (int i = 0; i < 20000; i++) {
			BigInteger area = getRectangleBounds(input);
			// Points are converging.
			if (minArea == null || area.compareTo(minArea) < 0) {
				minArea = area;
				minTime = i;
			}
			// Points are breaking apart again. Stop evaluating.
			else if (area.compareTo(minArea) >= 0) {
				break;
			}				
			for (Position position : input) {
				position.move(1);
			}
		}
		if (part == Part.ONE) {
			// Paint the grid at the correct time.
			for (Position position : input) {
				position.reset();
				position.move(minTime);
			}
			// Shift output to origin in console.
			Position offset = getOffset(input);
			int maxX = Integer.MIN_VALUE;
			for (Position position : input) {
				position.offset(offset);
				maxX = Math.max(position.getX(), maxX);
			}
			CharGrid grid = new CharGrid(maxX + 1);
			for (Position position : input) {
				grid.set(position, '#');
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
		BigInteger xLength = new BigInteger(String.valueOf(maxX - minX));
		BigInteger yLength = new BigInteger(String.valueOf(maxY - minY));
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