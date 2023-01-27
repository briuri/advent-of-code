package buri.aoc.y18.d03;

/**
 * Data class for a single claim
 *
 * @author Brian Uri!
 */
public class Claim {

	private final int _id;
	private final int _x;
	private final int _y;
	private final int _width;
	private final int _height;

	/**
	 * Constructor
	 *
	 * #19 @ 679,465: 20x29
	 */
	public Claim(String claim) {
		String[] tokens = claim.split(" ");
		String[] coords = tokens[2].substring(0, tokens[2].length() - 1).split(",");
		String[] size = tokens[3].split("x");

		_id = Integer.parseInt(tokens[0].substring(1));
		_x = Integer.parseInt(coords[0]);
		_y = Integer.parseInt(coords[1]);
		_width = Integer.parseInt(size[0]);
		_height = Integer.parseInt(size[1]);
	}

	/**
	 * Calculates the size based on dimensions.
	 */
	public int getSize() {
		return _width * _height;
	}

	/**
	 * Accessor for the id
	 */
	public int getId() {
		return _id;
	}

	/**
	 * Accessor for the x coordinate
	 */
	public int getX() {
		return _x;
	}

	/**
	 * Accessor for the y coordinate
	 */
	public int getY() {
		return _y;
	}

	/**
	 * Accessor for the width
	 */
	public int getWidth() {
		return _width;
	}

	/**
	 * Accessor for the height
	 */
	public int getHeight() {
		return _height;
	}
}