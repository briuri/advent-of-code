package buri.aoc.y18.d08;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutable data class for a node. Instantiated with metadata, and children are loaded later.
 * 
 * @author Brian Uri!
 */
public class Node {

	private List<Node> _children;
	private List<Integer> _metadata;

	/**
	 * Constructor (object is not complete until children are added)
	 */
	public Node(List<Integer> metadata) {
		_children = new ArrayList<>();
		_metadata = metadata;
	}

	/**
	 * Adds a node as a child
	 */
	public void addChild(Node node) {
		getChildren().add(node);
	}

	/**
	 * The value of a node depends on whether it has child nodes.
	 * 
	 * If a node has no child nodes, its value is the sum of its metadata entries. So, the value of node B is
	 * 10+11+12=33, and the value of node D is 99.
	 * 
	 * However, if a node does have child nodes, the metadata entries become indexes which refer to those child nodes. A
	 * metadata entry of 1 refers to the first child node, 2 to the second, 3 to the third, and so on. The value of this
	 * node is the sum of the values of the child nodes referenced by the metadata entries. If a referenced child node
	 * does not exist, that reference is skipped. A child node can be referenced multiple time and counts each time it
	 * is referenced. A metadata entry of 0 does not refer to any child node.
	 */
	public int getValue() {
		if (getChildren().size() == 0) {
			return (getMetadataSum());
		}
		int value = 0;
		for (Integer md : getMetadata()) {
			int index = md - 1;
			if (index < getChildren().size()) {
				value += getChildren().get(index).getValue();
			}
		}
		return (value);
	}

	/**
	 * Returns the sum of all metadata entries.
	 */
	public int getMetadataSum() {
		int sum = 0;
		for (Integer i : getMetadata()) {
			sum += i;
		}
		return (sum);
	}

	/**
	 * Accessor for the children
	 */
	private List<Node> getChildren() {
		return _children;
	}

	/**
	 * Accessor for the metadata
	 */
	private List<Integer> getMetadata() {
		return _metadata;
	}
}
