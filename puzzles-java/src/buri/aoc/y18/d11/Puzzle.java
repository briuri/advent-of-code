package buri.aoc.y18.d11;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Quad;
import org.junit.Test;

import java.util.List;

/**
 * Day 11: Chronal Charge
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("33,45", 1, false);
		assertRun("20,62", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("90,269,16", 1, false);
		assertRun("229,61,16", 0, true);
	}

	/**
	 * Part 1:
	 * What is the X,Y coordinate of the top-left fuel cell of the 3x3 square with the largest total power?
	 *
	 * Part 2:
	 * What is the X,Y,size identifier of the square with the largest total power?
	 */
	protected String runString(Part part, List<String> input) {
	    final int size = 300;
		final int serial = Integer.parseInt(input.get(0));
	    PowerGrid grid = new PowerGrid(size, serial);
		if (part == Part.ONE) {
			return (grid.getMaxValuePosition(3).toString());
		}

		// Partial Sums for Part TWO
	    for (int y = 1; y < size; y++) {
	    	for (int x = 1; x < size; x++) {
	    		// x,y is lower right corner of the square.
	    		int value = grid.get(x, y) + grid.get(x - 1, y) + grid.get(x, y - 1) - grid.get(x - 1, y - 1);
	    		grid.set(x, y, value);
			}
		}
		Quad<Integer> max = new Quad(0, 0, 0, 0);	// x, y, blockSize, power
		for (int blockSize = 1; blockSize < size; blockSize++) {
			for (int y = 0; y < size - blockSize; y++) {
				for (int x = 0; x < size - blockSize; x++) {
					// x,y is upper left corner of the square
					int value = grid.get(x, y) + grid.get(x + blockSize, y + blockSize)
						- grid.get(x, y + blockSize) - grid.get(x + blockSize, y);
					if (value > max.getT()) {
						max = new Quad(x + 1, y + 1, blockSize, value);
					}
				}
		    }
		}
		return (max.getX() + "," + max.getY() + "," + max.getZ());
	}
}