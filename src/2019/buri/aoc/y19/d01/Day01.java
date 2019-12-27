package buri.aoc.y19.d01;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 01: The Tyranny of the Rocket Equation
 * 
 * @author Brian Uri!
 */
public class Day01 extends BasePuzzle {

	/**
	 * Returns the input file as a list of integers
	 */
	public static List<Integer> getInput(int fileIndex) {
		return (convertStringsToInts(readFile(fileIndex)));
	}

	/**
	 * Part 1:
	 * What is the sum of the fuel requirements for all of the modules on your spacecraft?
	 * 
	 * Part 2:
	 * What is the sum of the fuel requirements for all of the modules on your spacecraft when also taking into account
	 * the mass of the added fuel?
	 */
	public static int getResult(Part part, List<Integer> input) {
		int sum = 0;
		for (Integer mass : input) {
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