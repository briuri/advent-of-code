package buri.aoc.y17.d10;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day10Test {

	@Test
	public void testGetInput() {
		List<Integer> input = Day10.getInput(Part.ONE, 0);
		assertEquals(16, input.size());
	}

	/**
	 * Suppose we instead only had a circular list containing five elements, 0, 1, 2, 3, 4, and were given input lengths
	 * of 3, 4, 1, 5.
	 * 
	 * In this example, the first two numbers in the list end up being 3 and 4; to check the process, you can multiply
	 * them together to produce 12.
	 */
	@Test
	public void testPart1Examples() {
		assertEquals("12", Day10.getResult(Part.ONE, 5, Day10.getInput(Part.ONE, 1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day10.getResult(Part.ONE, 256, Day10.getInput(Part.ONE, 0));
		System.out.println("Day 10 Part 1\n\t" + result);
		assertEquals("6909", result);
	}

	/**
	 * 
	 */
	@Test
	public void testPart2Examples() {
		assertEquals("33efeb34ea91902bb2f59c9920caa6cd", Day10.getResult(Part.TWO, 256, Day10.getInput(Part.TWO, 2)));
		assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", Day10.getResult(Part.TWO, 256, Day10.getInput(Part.TWO, 3)));
		assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", Day10.getResult(Part.TWO, 256, Day10.getInput(Part.TWO, 4)));

	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day10.getResult(Part.TWO, 256, Day10.getInput(Part.TWO, 0));
		System.out.println("Day 10 Part 2\n\t" + result);
		assertEquals("9d5f4561367d379cfbf04f8c471c0095", result);
	}
}
