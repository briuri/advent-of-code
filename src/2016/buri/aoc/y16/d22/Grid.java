package buri.aoc.y16.d22;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.data.Pair;

/**
 * @author Brian Uri!
 */
public class Grid {

	private Map<Pair, Node> _nodes;
	
	/**
	 * Constructor
	 */
	public Grid(List<String> input) {
		_nodes = new HashMap<>();
		for (String line : input) {
			Node node = new Node(line);
			getNodes().put(node.getId(), node);
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

	/**
	 * Accessor for the nodes
	 */
	private Map<Pair, Node> getNodes() {
		return _nodes;
	}
}
