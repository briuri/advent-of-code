package buri.aoc.y18.d04;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * 
 * @author Brian Uri!
 */
public class Day04Test {

	@Test
	public void testGetInput() {
		List<LogEntry> input = Day04.getInput(0);
		assertEquals(905, input.size());
	}

	/**
	 * In the example above, Guard #10 spent the most minutes asleep, a total of 50 minutes (20+25+5), while Guard #99
	 * only slept for a total of 30 minutes (10+10+10). Guard #10 was asleep most during minute 24 (on two days, whereas
	 * any other minute the guard was asleep was only seen on one day).
	 */
	@Test
	public void testPart1Examples() {
		assertEquals(240, Day04.getResult(Part.ONE, Day04.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day04.getResult(Part.ONE, Day04.getInput(0));
		System.out.println("Day 4 Part 1\n\t" + result);
		assertEquals(76357, result);
	}

	/**
	 * In the example above, Guard #99 spent minute 45 asleep more than any other guard or minute - three times in
	 * total. (In all other cases, any guard spent any minute asleep at most twice.)
	 * 
	 * In the above example, the answer would be 99 * 45 = 4455.)
	 */
	@Test
	public void testPart2Examples() {
		assertEquals(4455, Day04.getResult(Part.TWO, Day04.getInput(1)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day04.getResult(Part.TWO, Day04.getInput(0));
		System.out.println("Day 4 Part 2\n\t" + result);
		assertEquals(41668, result);
	}
}
