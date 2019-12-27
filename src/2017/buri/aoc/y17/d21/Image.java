package buri.aoc.y17.d21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.data.Pair;
import buri.aoc.data.grid.IntGrid;

/**
 * Data class for the Image
 * 
 * @author Brian Uri!
 */
public class Image extends IntGrid {
	private Map<Integer, List<Rule>> _rules;

	/**
	 * Constructor
	 * 
	 * Starting image:
	 * .#.
	 * ..#
	 * ###
	 * 
	 */
	public Image(List<Rule> rules) {
		super(new Pair(3, 3));
		getGrid()[0] = new Integer[] { 0, 0, 1 };
		getGrid()[1] = new Integer[] { 1, 0, 1 };
		getGrid()[2] = new Integer[] { 0, 1, 1 };
		
		_rules = new HashMap<>();
		getRules().put(2, new ArrayList<>());
		getRules().put(3, new ArrayList<>());
		for (Rule rule : rules) {
			getRules().get(rule.getPatternSize()).add(rule);
		}
	}

	/**
	 * If the size is evenly divisible by 2, break the pixels up into 2x2 squares, and convert each 2x2 square into a
	 * 3x3 square by following the corresponding enhancement rule.
	 * 
	 * Otherwise, the size is evenly divisible by 3; break the pixels up into 3x3 squares, and convert each 3x3 square
	 * into a 4x4 square by following the corresponding enhancement rule.
	 */
	public void fractalize() {
		int patternSize = (getWidth() % 2 == 0) ? 2 : 3;
		int subdivisions = (getWidth() / patternSize);
		int newImageSize = subdivisions * (patternSize + 1);
		Integer[][] newImage = new Integer[newImageSize][newImageSize];
		for (int y = 0; y < subdivisions; y++) {
			for (int x = 0; x < subdivisions; x++) {
				Pattern current = getSubdivision(patternSize, x, y);
				Pattern result = getResultForMatch(current);
				draw(newImage, result, x, y);
			}
		}
		setGrid(newImage);
	}
	
	/**
	 * Extracts a subgrid from the image and converts to a pattern.
	 */
	private Pattern getSubdivision(int patternSize, int subX, int subY) {
		int[][] pattern = new int[patternSize][patternSize];
		int offsetX = patternSize * subX;
		int offsetY = patternSize * subY;
		for (int y = 0; y < patternSize; y++) {
			for (int x = 0; x < patternSize; x++) {
				pattern[x][y] = get(x + offsetX, y + offsetY);
			}
		}
		return (new Pattern(pattern));
	}
	
	/**
	 * Checks all rules and returns the result from the matching one.
	 */
	private Pattern getResultForMatch(Pattern pattern) {
		List<Rule> rules = getRules().get(pattern.getWidth());
		for (Rule rule : rules) {
			if (rule.matches(pattern)) {
				return (rule.getResult());
			}
		}
		throw new RuntimeException("Pattern has no matching rule.");
	}
	
	/**
	 * Draws a pattern onto a grid.
	 */
	private void draw(Integer[][] newImage, Pattern pattern, int subX, int subY) {
		int offsetX = pattern.getWidth() * subX;
		int offsetY = pattern.getHeight() * subY;
		for (int y = 0; y < pattern.getHeight(); y++) {
			for (int x = 0; x < pattern.getWidth(); x++) {
				newImage[x + offsetX][y + offsetY] = pattern.get(x, y);
			}
		}
	}
	
	/**
	 * Counts the number of lit pixels (denoted by 1)
	 */
	public int getLitPixels() {
		int sum = 0;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				sum += get(x, y);
			}
		}
		return (sum);
	}

	/**
	 * Accessor for the rules
	 */
	private Map<Integer, List<Rule>> getRules() {
		return _rules;
	}
}