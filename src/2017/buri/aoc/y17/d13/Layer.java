package buri.aoc.y17.d13;

/**
 * Mutable data class for a firewall layer.
 * 
 * @author Brian Uri!
 */
public class Layer {

	int _depth;
	int _range;

	/**
	 * Constructor
	 * 
	 * 0: 3
	 */
	public Layer(String input) {
		String[] tokens = input.split(": ");
		_depth = Integer.valueOf(tokens[0]);
		_range = Integer.valueOf(tokens[1]);
	}

	/**
	 * Checks if the packet would get caught when entering this layer at the given time.
	 */
	public boolean isCaught(int time) {
		return (time % (getRange() * 2 - 2) == 0);
	}

	/**
	 * Returns the severity of getting caught in this layer.
	 */
	public int getSeverity() {
		return (getDepth() * getRange());
	}

	/**
	 * Accessor for the depth
	 */
	public int getDepth() {
		return _depth;
	}

	/**
	 * Accessor for the range
	 */
	private int getRange() {
		return _range;
	}
}