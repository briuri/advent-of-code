package buri.aoc.y17.d21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data class for the Image
 * 
 * @author Brian Uri!
 */
public class Image {

	private int[][] _image;
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
		_image = new int[3][3];
		_image[0][0] = 0;
		_image[0][1] = 0;
		_image[0][2] = 1;
		
		_image[1][0] = 1;
		_image[1][1] = 0;
		_image[1][2] = 1;
		
		_image[2][0] = 0;
		_image[2][1] = 1;
		_image[2][2] = 1;		
		
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
		int patternSize = (getSize() % 2 == 0) ? 2 : 3;
		int subdivisions = (getSize() / patternSize);
		int newImageSize = subdivisions * (patternSize + 1);
		int[][] newImage = new int[newImageSize][newImageSize];
		for (int y = 0; y < subdivisions; y++) {
			for (int x = 0; x < subdivisions; x++) {
				Pattern current = getSubdivision(patternSize, x, y);
				Pattern result = getResultForMatch(current);
				draw(newImage, result, x, y);
			}
		}
		setImage(newImage);
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
				pattern[x][y] = getImage()[x + offsetX][y + offsetY];
			}
		}
		return (new Pattern(pattern));
	}
	
	/**
	 * Checks all rules and returns the result from the matching one.
	 */
	private Pattern getResultForMatch(Pattern pattern) {
		List<Rule> rules = getRules().get(pattern.getSize());
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
	private void draw(int[][] newImage, Pattern pattern, int subX, int subY) {
		int offsetX = pattern.getSize() * subX;
		int offsetY = pattern.getSize() * subY;
		for (int y = 0; y < pattern.getSize(); y++) {
			for (int x = 0; x < pattern.getSize(); x++) {
				newImage[x + offsetX][y + offsetY] = pattern.getPattern()[x][y];
			}
		}
	}
	
	/**
	 * Counts the number of lit pixels (denoted by 1)
	 */
	public int getLitPixels() {
		int sum = 0;
		for (int y = 0; y < getImage().length; y++) {
			for (int x = 0; x < getImage().length; x++) {
				sum += getImage()[x][y];
			}
		}
		return (sum);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int y = 0; y < getImage().length; y++) {
			for (int x = 0; x < getImage().length; x++) {
				buffer.append(getImage()[x][y]);
			}
			buffer.append("\n");
		}
		return (buffer.toString());
	}
	
	/**
	 * Accessor for the current image size
	 */
	private int getSize() {
		return (getImage().length);
	}
	
	/**
	 * Accessor for the image
	 */
	private int[][] getImage() {
		return _image;
	}

	/**
	 * Accessor for the image
	 */
	private void setImage(int[][] image) {
		_image = image;
	}	
	
	/**
	 * Accessor for the rules
	 */
	private Map<Integer, List<Rule>> getRules() {
		return _rules;
	}
}