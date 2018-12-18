package buri.aoc.y18.d10;

import buri.aoc.data.AbstractCharGrid;

/**
 * Simple grid class to print out the moving characters.
 * 
 * @author Brian Uri!
 */
public class Grid extends AbstractCharGrid {

	/**
	 * Constructor
	 */
	public Grid(int size) {
		super(size);
		for (int y = 0; y < getGrid().length; y++) {
			for (int x = 0; x < getGrid().length; x++) {
				set(x, y, ' ');
			}
		}
	}
}
