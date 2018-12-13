package buri.aoc.y17.d21;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple 2x2 or 3x3 pattern used in a rule.
 * 
 * @author Brian Uri!
 */
public class Pattern {

	private int[][] _pattern;
	
	/**
	 * Input-based Constructor
	 */
	public Pattern(String pattern) {
		List<String> rows = Arrays.asList(pattern.split("/"));
		_pattern = new int[rows.size()][rows.size()];
		for (int y = 0; y < getPattern().length; y++) {
			for (int x = 0; x < getPattern().length; x++) {
				getPattern()[x][y] = toInt(rows.get(y).charAt(x));
			}
		}
	}
	
	/**
	 * Array-based Constructor
	 */
	public Pattern(int[][] pattern) {
		_pattern = pattern;
	}

	/**
	 * Returns all 8 flip/rotate permutations of this pattern.
	 */
	public Set<Pattern> getPermutations() {
		Set<Pattern> set = new HashSet<>();
		set.add(this);
		return (set);
	}
	
	/**
	 * Returns the square size.
	 */
	public int getSize() {
		return (getPattern().length);
	}
	
	/**
	 * Returns this pattern flipped across the vertical axis.
	 */
	public Pattern flip() {
		int[][] pattern = new int[getSize()][getSize()];
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
				pattern[getSize() - x - 1][y] = getPattern()[x][y];
			}
		}
		return (new Pattern(pattern));
	}
	
	/**
	 * Returns this pattern rotated 90 degrees counterclockwise
	 */
	public Pattern rotate() {
		int[][] pattern = new int[getSize()][getSize()];
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
				pattern[x][getSize() - 1 - y] = getPattern()[y][x];
			}
		}
		return new Pattern(pattern);
	}
	
	/**
	 * Converts rule strings into ints for storage.
	 */
	private int toInt(char value) {
		return (value == '#' ? 1 : 0);
	}
	
	@Override
	public boolean equals(Object obj) {
		System.out.println(toString() + " " + obj.toString());
		return (toString().equals(obj.toString()));
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
				buffer.append(getPattern()[x][y]);
			}
			buffer.append("\n");
		}
		return (buffer.toString());
	}
	
	/**
	 * Accessor for the pattern
	 */
	public int[][] getPattern() {
		return _pattern;
	}	
}
