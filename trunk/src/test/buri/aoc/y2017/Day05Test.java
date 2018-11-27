package buri.aoc.y2017;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import buri.aoc.model.Part;
import buri.aoc.y2017.util.FileUtil;

/**
 * @author Brian Uri!
 */
public class Day05Test {

	@Test(expected = IllegalArgumentException.class)
	public void testGetStepsNullJumps() {
		Day05.getSteps(Part.ONE, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetStepsEmptyJumps() {
		Day05.getSteps(Part.ONE, new ArrayList<>());
	}

	/**
	 * For example, consider the following list of jump offsets:
	 * 
	 * 0 3 0 1 -3
	 * 
	 * The following steps would be taken before an exit is found:
	 * 
	 * (0) 3 0 1 -3 - before we have taken any steps.
	 * (1) 3 0 1 -3 - jump with offset 0 (that is, don't jump). The instruction is then incremented to 1.
	 * 2 (3) 0 1 -3 - step forward. The first instruction is incremented again, now to 2.
	 * 2 4 0 1 (-3) - jump all the way to the end; leave a 4 behind.
	 * 2 (4) 0 1 -2 - go back to where we just were; increment -3 to -2.
	 * 2 5 0 1 -2 - jump 4 steps forward, escaping the maze.
	 * 
	 * In this example, the exit is reached in 5 steps.
	 */
	@Test
	public void testPart1Example1() {
		List<Integer> jumps = new ArrayList<>();
		jumps.add(Integer.valueOf(0));
		jumps.add(Integer.valueOf(3));
		jumps.add(Integer.valueOf(0));
		jumps.add(Integer.valueOf(1));
		jumps.add(Integer.valueOf(-3));
		assertEquals(5, Day05.getSteps(Part.ONE, jumps));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day05.getSteps(Part.ONE, FileUtil.getDay05());
		System.out.println("Day 5 Part 1\n\t" + result);
		assertEquals(336905, result);
	}

	/**
	 * Using this rule with the above example, the process now takes 10 steps, and the offset values after finding the
	 * exit are left as 2 3 2 3 -1.
	 */
	@Test
	public void testPart2Example1() {
		List<Integer> jumps = new ArrayList<>();
		jumps.add(Integer.valueOf(0));
		jumps.add(Integer.valueOf(3));
		jumps.add(Integer.valueOf(0));
		jumps.add(Integer.valueOf(1));
		jumps.add(Integer.valueOf(-3));
		assertEquals(10, Day05.getSteps(Part.TWO, jumps));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day05.getSteps(Part.TWO, FileUtil.getDay05());
		System.out.println("Day 5 Part 2\n\t" + result);
		assertEquals(21985262, result);
	}
}
