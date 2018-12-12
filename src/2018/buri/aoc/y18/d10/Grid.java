package buri.aoc.y18.d10;

import buri.aoc.data.AbstractGrid;

/**
 * Simple grid class to print out the moving characters.
 * 
 * @author Brian Uri!
 */
public class Grid extends AbstractGrid {

	/**
	 * Constructor
	 */
	public Grid(int size) {
		super(size);
	}
	
	@Override
	protected String toOutput(long value) {
		return (value == 0L ? " " : "#");
	}
}
