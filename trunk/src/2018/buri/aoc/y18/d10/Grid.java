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
		super(size, 1);
	}
	
	/**
	 * Marks a spot on the grid.
	 */
	public void drawPoint(Position p) {
		getGrid()[p.getX()][p.getY()] = 1; 
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
				int value = getGrid()[x][y];
				
				buffer.append(value == 0 ? " " : "#");
			}
			buffer.append("\n");
		}
		buffer.append("\n");
		return (buffer.toString());
	}
}
