package buri.aoc.y18.d03;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * The whole piece of fabric they're working on is a very large square - at least 1000 inches on each side.
 * 
 * Each Elf has made a claim about which area of fabric would be ideal for Santa's suit. All claims have an ID and
 * consist of a single rectangle with edges parallel to the edges of the fabric. Each claim's rectangle is defined as
 * follows:
 * 
 * The number of inches between the left edge of the fabric and the left edge of the rectangle.
 * The number of inches between the top edge of the fabric and the top edge of the rectangle.
 * The width of the rectangle in inches.
 * The height of the rectangle in inches.
 * 
 * A claim like #123 @ 3,2: 5x4 means that claim ID 123 specifies a rectangle 3 inches from the left edge, 2 inches from
 * the top edge, 5 inches wide, and 4 inches tall.
 * 
 * @author Brian Uri!
 */
public class Day03 extends Puzzle {

	/**
	 * Input: A claim on each line
	 * Output: A list of claims
	 */
	public static List<Claim> getInput(int fileIndex) {
		List<Claim> claims = new ArrayList<>();
		for (String rawClaim : readFile("2018/03", fileIndex)) {
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