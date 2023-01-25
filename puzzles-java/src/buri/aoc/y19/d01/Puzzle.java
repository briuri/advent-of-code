package buri.aoc.y19.d01;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 01: The Tyranny of the Rocket Equation
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(2, Puzzle.getFuel(Part.ONE, 12));
		assertEquals(2, Puzzle.getFuel(Part.ONE, 14));
		assertEquals(654, Puzzle.getFuel(Part.ONE, 1969));
		assertEquals(33583, Puzzle.getFuel(Part.ONE, 100756));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3087896, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Puzzle.getFuel(Part.TWO, 14));
		assertEquals(966, Puzzle.getFuel(Part.TWO, 1969));
		assertEquals(50346, Puzzle.getFuel(Part.TWO, 100756));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4628989, result);
	}

	/**
	 * Part 1:
	 * What is the sum of the fuel requirements for all of the modules on your spacecraft?
	 *
	 * Part 2:
	 * What is the sum of the fuel requirements for all of the modules on your spacecraft when also taking into account
	 * the mass of the added fuel?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Integer> values = convertStringsToInts(input);
		int sum = 0;
		for (Integer mass : values) {
			sum += getFuel(part, mass);
		}
		return (sum);
	}

	/**
	 * Calculates the amount of fuel needed for some mass. Fuel required to launch a given module is based on its mass.
	 * Specifically, to find the fuel required for a module, take its mass, divide by three, round down, and subtract 2.
	 *
	 * In Part Two, the fuel itself also requires fuel.
	 */
	public static int getFuel(Part part, int mass) {
		int fuel = Math.max(mass / 3 - 2, 0);
		if (part == Part.TWO && fuel > 0) {
			fuel += getFuel(part, fuel);
		}
		return (fuel);
	}
}