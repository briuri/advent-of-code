package buri.aoc.y16.d22;

import buri.aoc.common.data.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Uri!
 */
public class Grid {

	private int _maxX;
	private int _maxY;
	private final Map<Pair, Node> _nodes;

	/**
	 * Constructor
	 */
	public Grid(List<String> input) {
		_nodes = new HashMap<>();
		for (String line : input) {
			Node node = new Node(line);
			getNodes().put(node.getId(), node);
			_maxX = Math.max(_maxX, node.getId().getX());
			_maxY = Math.max(_maxY, node.getId().getY());
		}
	}

	/**
	 * A viable pair is any two nodes (A,B), regardless of whether they are directly connected, such that:
	 * Node A is not empty (its Used is not zero).
	 * Nodes A and B are not the same node.
	 * The data on node A (its Used) would fit on node B (its Avail).
	 */
	public int getViablePairs() {
		int count = 0;
		for (Node nodeA : getNodes().values()) {
			if (nodeA.getUsed() > 0) {
				for (Node nodeB : getNodes().values()) {
					if (!nodeA.getId().equals(nodeB.getId()) && nodeB.getFree() > nodeA.getUsed()) {
						count++;
					}
				}
			}
		}
		return (count);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y <= getMaxY(); y++) {
			for (int x = 0; x <= getMaxX(); x++) {
				Pair<Integer> id = new Pair<>(x, y);
				Node node = getNodes().get(id);
				if (node.getId().getX() == getMaxX() && node.getId().getY() == 0) {
					builder.append('G');
				}
				else if (node.getUsed() == 0) {
					builder.append('_');
				}
				else if (node.getTotal() > 100) {
					builder.append("#");
				}
				else {
					builder.append(".");
				}
			}
			builder.append("\n");
		}
		return (builder.toString());
	}

	/**
	 * Accessor for the nodes
	 */
	private Map<Pair, Node> getNodes() {
		return _nodes;
	}

	/**
	 * Accessor for the maxX
	 */
	private int getMaxX() {
		return _maxX;
	}

	/**
	 * Accessor for the maxY
	 */
	private int getMaxY() {
		return _maxY;
	}
}