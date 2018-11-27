package buri.aoc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Day07
 * 
 * Representation of a progam.
 * 
 * @author Brian Uri!
 */
public class Program {
	private String _name;
	private int _weight;
	private List<String> _childNames;
	private List<Program> _children;
	
	/**
	 * Constructor
	 */
	public Program(String name, int weight, List<String> childNames) {
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
		int totalWeight = getWeight();
		for (Program child : getChildren()) {
			totalWeight += child.getTotalWeight();
		}
		return (totalWeight); 
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