package buri.aoc.y18.d22;

/**
 * Encapsulation of the data needed to calculate the best path through the maze.
 *
 * @author Brian Uri!
 */
public class Node implements Comparable<Node> {
	private Position _position;
	private int _costSoFar;

	/**
	 * Constructor
	 */
	public Node(Position position, int costSoFar) {
		_position = position;
		_costSoFar = costSoFar;
	}

	/**
	 * Sorts nodes by lowest cost in ascending order.
	 */
	@Override
	public int compareTo(Node o) {
		return (getCostSoFar() - o.getCostSoFar());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ("[" + getPosition() + "] costSoFar=" + getCostSoFar());
	}

	/**
	 * Accessor for the position / item
	 */
	public Position getPosition() {
		return _position;
	}

	/**
	 * Accessor for the time used to get here
	 */
	public int getCostSoFar() {
		return _costSoFar;
	}
}