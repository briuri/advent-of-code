package buri.aoc.y17.d07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of a progam.
 *
 * @author Brian Uri!
 */
public class Program {
	private String _name;
	private int _weight;
	private int _cachedTotalWeight = 0;
	private List<String> _childNames;
	private List<Program> _children;

	private static final String RELATION = " -> ";
	private static final String WEIGHT_SEPARATOR = " ";
	private static final String CHILD_SEPARATOR = ", ";

	/**
	 * Constructor
	 */
	public Program(String info) {
		String[] relationship = info.split(RELATION);
		String[] nameWeight = relationship[0].split(WEIGHT_SEPARATOR);
		String name = nameWeight[0];
		int weight = Integer.valueOf(nameWeight[1].replace("(", "").replace(")", ""));
		List<String> childNames = new ArrayList<>();
		if (relationship.length > 1) {
			for (String child : relationship[1].split(CHILD_SEPARATOR)) {
				childNames.add(child);
			}
		}

		_name = name;
		_weight = weight;
		_childNames = childNames;
	}

	/**
	 * Loads the full object representations of the child programs.
	 */
	public void loadChildren(Map<String, Program> programs) {
		_children = new ArrayList<>();
		for (String childName : getChildNames()) {
			getChildren().add(programs.get(childName));
		}
	}

	/**
	 * Recursively calculates the weight of this program and all children.
	 */
	public int getTotalWeight() {
		if (_cachedTotalWeight == 0) {
			int totalWeight = getWeight();
			for (Program child : getChildren()) {
				totalWeight += child.getTotalWeight();
			}
			_cachedTotalWeight = totalWeight;
		}
		return (_cachedTotalWeight);
	}

	/**
	 * Searches for the 1 child with the wrong weight.
	 */
	public Program getImbalancedChild() {
		Map<Integer, List<Program>> weightsToChildren = new HashMap<>();
		for (Program child : getChildren()) {
			if (weightsToChildren.get(child.getTotalWeight()) == null) {
				weightsToChildren.put(child.getTotalWeight(), new ArrayList<>());
			}
			weightsToChildren.get(child.getTotalWeight()).add(child);
		}
		// No children, or all children have the same total weight.
		if (getChildren().isEmpty() || weightsToChildren.size() == 1) {
			return (null);
		}
		// Get the loner child. Assumption: The tree will have 3 or more children, so weight with only 1 child is the
		// imbalanced one.
		for (List<Program> children : weightsToChildren.values()) {
			if (children.size() == 1) {
				return (children.get(0));
			}
		}
		throw new RuntimeException("Could not locate imbalanced child of program " + getName());
	}

	/**
	 * Prints the program tower.
	 */
	public String toString(int level) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < level; i++) {
			buffer.append("\t");
		}
		buffer.append(getName()).append(" (").append(getWeight()).append(") (").append(getTotalWeight()).append(")\n");
		for (Program child : getChildren()) {
			buffer.append(child.toString(level + 1));
		}
		return (buffer.toString());
	}

	/**
	 * Accessor for the unique name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the weight
	 */
	public int getWeight() {
		return _weight;
	}

	/**
	 * Accessor for the child names
	 */
	public List<String> getChildNames() {
		return _childNames;
	}

	/**
	 * Accessor for the list of children. Must be initialized.
	 */
	public List<Program> getChildren() {
		if (_children == null) {
			throw new IllegalArgumentException("Children must be initialized before calling.");
		}
		return _children;
	}
}