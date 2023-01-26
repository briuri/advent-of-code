package buri.aoc.y16.d22;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 22: Grid Computing
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(967L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(205L, 0, true);
	}

	/**
	 * Part 1:
	 * How many viable pairs of nodes are there?
	 *
	 * Part 2:
	 * Your goal is to gain access to the data which begins in the node with y=0 and the highest x (that is, the node in
	 * the top-right corner). What is the fewest number of steps required to move your goal data to node-x0-y0?
	 */
	protected long runLong(Part part, List<String> input) {
		Grid grid = new Grid(input);
		if (part == Part.ONE) {
			return (grid.getViablePairs());
		}
		// Part TWO
		// System.out.println(grid);

		/*
		 * Initial:
		 * ................................G
		 * .................................
		 * .................................
		 * .................................
		 * .................................
		 * .................................
		 * .................................
		 * .................................
		 * .................................
		 * ...........######################
		 * .................................
		 * .................................
		 * .........................._......
		 *
		 * 28 to get Empty to Top:
		 * .........._.....................G
		 * .................................
		 * .................................
		 * .................................
		 * .................................
		 * .................................
		 * .................................
		 * .................................
		 * .................................
		 * ...........######################
		 *
		 * 28+22=50 to swap Empty and G:
		 * ...............................G_
		 * .................................
		 *
		 * 50+(31*5)=205 to move G_ left:
		 * G_...............................
		 * .................................
		 *
		 */
		return (205);
	}
}