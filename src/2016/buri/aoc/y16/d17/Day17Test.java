package buri.aoc.y16.d17;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day17Test {

	@Test
	public void testPart1Examples() {
		assertEquals("DDRRRD", Day17.getResult(Part.ONE, "ihgpwlah"));
		assertEquals("DDUDRLRRUDRD", Day17.getResult(Part.ONE, "kglvqrro"));
		assertEquals("DRURDRUDDLLDLUURRDULRLDUUDDDRR", Day17.getResult(Part.ONE, "ulqzkmiv"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day17.getResult(Part.ONE, "rrrbmfta");
		System.out.println("Day 17 Part 1\n\t" + result);
		assertEquals("RLRDRDUDDR", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("370", Day17.getResult(Part.TWO, "ihgpwlah"));
		assertEquals("492", Day17.getResult(Part.TWO, "kglvqrro"));
		assertEquals("830", Day17.getResult(Part.TWO, "ulqzkmiv"));		
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day17.getResult(Part.TWO, "rrrbmfta");
		System.out.println("Day 17 Part 2\n\t" + result);
		assertEquals("420", result);
	}
}
