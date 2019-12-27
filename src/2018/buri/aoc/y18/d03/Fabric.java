package buri.aoc.y18.d03;

import buri.aoc.data.grid.IntGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Fabric grid
 * 
 * @author Brian Uri!
 */
public class Fabric extends IntGrid {

	/**
	 * Creates a new square grid with the specified width/length.
	 */
	public Fabric(int size) {
		super(new Pair(size, size));
	}

	/**
	 * Mark a spot on the grid as claimed by incrementing its value.
	 */
	public void claimSquare(int x, int y) {
		set(x, y, get(x, y) + 1);
	}

	/**
	 * Counts the squares that overlap between 2 or more claims.
	 */
	public int countOverlaps() {
		int sum = 0;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if (get(x, y) >= 2) {
					sum++;
				}
			}
		}
		return (sum);
	}

	/**
	 * Returns true if a claim has no overlap. When this is true, the sum of all values in the claim add up to width x
	 * height.
	 */
	public boolean hasNoOverlap(Claim claim) {
		int sum = 0;
		for (int w = 0; w < claim.getWidth(); w++) {
			for (int h = 0; h < claim.getHeight(); h++) {
				sum += get(claim.getX() + w, claim.getY() + h);
			}
		}
		return (sum == claim.getSize());
	}
}