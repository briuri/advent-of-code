package buri.aoc.y17.d24;

/**
 * Data model for a single piece of the bridge.
 * 
 * @author Brian Uri!
 */
public class Piece implements Comparable<Piece> {

	private int _portA;
	private int _portB;

	/**
	 * Constructor
	 */
	public Piece(String input) {
		String[] tokens = input.split("/");
		_portA = Integer.valueOf(tokens[0]);
		_portB = Integer.valueOf(tokens[1]);
	}

	/**
	 * Returns the port that isn't the one passed in.
	 */
	public int getOtherPort(int port) {
		if (port != getPortA() && port != getPortB()) {
			throw new IllegalArgumentException("This piece doesn't have a port " + port + ".");
		}
		return (port == getPortA() ? getPortB() : getPortA());
	}

	/**
	 * The score is the sum of the ports.
	 */
	public int getScore() {
		return (getPortA() + getPortB());
	}

	@Override
	public String toString() {
		return getPortA() + "/" + getPortB();
	}

	@Override
	public int compareTo(Piece o) {
		return (getScore() - o.getScore());
	}

	/**
	 * Accessor for the portA
	 */
	public int getPortA() {
		return _portA;
	}

	/**
	 * Accessor for the portB
	 */
	public int getPortB() {
		return _portB;
	}
}
