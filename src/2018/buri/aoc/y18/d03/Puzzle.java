package buri.aoc.y18.d03;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 3: No Matter How You Slice It
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns input file as a list of Claims
	 */
	public static List<Claim> getInput(int fileIndex) {
		List<Claim> claims = new ArrayList<>();
		for (String rawClaim : readFile(fileIndex)) {
			claims.add(new Claim(rawClaim));
		}
		return (claims);
	}

	/**
	 * Part 1:
	 * How many square inches of fabric are within two or more claims?
	 * 
	 * Part 2:
	 * What is the ID of the only claim that doesn't overlap?
	 */
	public static int getResult(Part part, List<Claim> input) {
		Fabric grid = new Fabric(1000);
		for (Claim claim : input) {
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
		for (Claim claim : input) {
			if (grid.hasNoOverlap(claim)) {
				return (claim.getId());
			}
		}
		throw new RuntimeException("Could not find a claim without an overlap.");
	}
}