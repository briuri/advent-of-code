package buri.aoc.y18.d06;

import buri.aoc.data.AbstractPair;

/**
 * Immutable Integer-based ordered pair class.
 * 
 * @author Brian Uri!
 */
public class Position extends AbstractPair {

	/**
	 * Constructor
	 */
	public Position(String data) {
		super(data);
	}
	
	/**
	 * Constructor
	 */
	public Position(int x, int y) {
		super(x, y);
	}
}
