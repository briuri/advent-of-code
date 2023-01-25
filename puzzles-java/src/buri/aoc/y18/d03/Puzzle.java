package buri.aoc.y18.d03;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 3: No Matter How You Slice It
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(4, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(111266, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(266, result);
	}

	/**
	 * Part 1:
	 * How many square inches of fabric are within two or more claims?
	 *
	 * Part 2:
	 * What is the ID of the only claim that doesn't overlap?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Claim> claims = new ArrayList<>();
		for (String line : input) {
			claims.add(new Claim(line));
		}

		Fabric grid = new Fabric(1000);
		for (Claim claim : claims) {
			for (int w = 0; w < claim.getWidth(); w++) {
				for (int h = 0; h < claim.getHeight(); h++) {
					grid.claimSquare(claim.getX() + w, claim.getY() + h);
				}
			}
		}
		if (part == Part.ONE) {
			return (grid.countOverlaps());
		}
		// Part TWO
		for (Claim claim : claims) {
			if (grid.hasNoOverlap(claim)) {
				return (claim.getId());
			}
		}
		throw new RuntimeException("Could not find a claim without an overlap.");
	}
}