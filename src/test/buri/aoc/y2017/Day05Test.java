package buri.aoc.y2017;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import buri.aoc.y2017.Day05.Strategy;

/**
 * @author Brian Uri!
 */
public class Day05Test {

	@Test
	public void testGetJumpsFromFile() {
		List<Integer> jumps = Day05.getJumpsFromFile("data/2017/05.txt");
		assertEquals(1033, jumps.size());
		assertEquals(Integer.valueOf(0), jumps.get(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetJumpsFromFileFailure() {
		Day05.getJumpsFromFile("unknown");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetStepsNullJumps() {
		Day05.getSteps(Strategy.ALWAYS_INCREMENT, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetStepsEmptyJumps() {
		Day05.getSteps(Strategy.ALWAYS_INCREMENT, new ArrayList<>());
	}
	
	/**
	 * For example, consider the following list of jump offsets:
	 * 
	 * 0 3 0 1 -3
	 * 
	 * The following steps would be taken before an exit is found:
	 * 
	 * (0) 3  0  1  -3  - before we have taken any steps.
	 * (1) 3  0  1  -3  - jump with offset 0 (that is, don't jump). The instruction is then incremented to 1.
	 *  2 (3) 0  1  -3  - step forward. The first instruction is incremented again, now to 2.
	 *  2  4  0  1 (-3) - jump all the way to the end; leave a 4 behind.
	 *  2 (4) 0  1  -2  - go back to where we just were; increment -3 to -2.
	 *  2  5  0  1  -2  - jump 4 steps forward, escaping the maze.
	 *  
	 *  In this example, the exit is reached in 5 steps.
	 */
	@Test
	public void testPart1Example1() {
		List<Integer> jumps = new ArrayList<>();
		jumps.add(Integer.valueOf(0));
		jumps.add(Integer.valueOf(3));
		jumps.add(Integer.valueOf(0));
		jumps.add(Integer.valueOf(1));
		jumps.add(Integer.valueOf(-3));
		assertEquals(5, Day05.getSteps(Strategy.ALWAYS_INCREMENT, jumps));
	}

	/**
	 * Solves the Day 5 Part 1 puzzle against the real input file.
	 */
	@Test
	public void testPart1RealInput() {
		List<Integer> jumps = Day05.getJumpsFromFile("data/2017/05.txt");
		System.out.println("Day 5 Part 1 steps=" + Day05.getSteps(Strategy.ALWAYS_INCREMENT, jumps));
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
		assertEquals(10, Day05.getSteps(Strategy.CONDITIONAL_INCREMENT, jumps));
	}

	/**
	 * Solves the Day 5 Part 2 puzzle against the real input file.
	 */
	@Test
	public void testPart2RealInput() {
		List<Integer> jumps = Day05.getJumpsFromFile("data/2017/05.txt");
		System.out.println("Day 5 Part 2 steps=" + Day05.getSteps(Strategy.CONDITIONAL_INCREMENT, jumps));
	}
}
