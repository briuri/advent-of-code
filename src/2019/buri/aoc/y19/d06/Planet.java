package buri.aoc.y19.d06;

/**
 * Model representing a planet and the planet it orbits.
 * 
 * @author Brian Uri!
 */
public class Planet {
	private String _name;
	private String _parentName;
	private Planet _parent;

	/**
	 * Constructor
	 */
	public Planet(String name, String parentName) {
		_name = name;
		_parentName = parentName;
	}

	@Override
	public String toString() {
		Planet parent = getParent();
		return (parent == null ? getName() : getParent() + ")" + getName());
	}

	/**
	 * Accessor for the name of this planet
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the name of the parent planet
	 */
	public String getParentName() {
		return _parentName;
	}

	/**
	 * Accessor for the planet this planet orbits (its parent).
	 */
	public Planet getParent() {
		return _parent;
	}

	/**
	 * Accessor for the planet this planet orbits (its parent).
	 */
	public void setParent(Planet parent) {
		_parent = parent;
	}
}
