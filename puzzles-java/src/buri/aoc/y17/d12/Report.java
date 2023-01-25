package buri.aoc.y17.d12;

import java.util.HashSet;
import java.util.Set;

/**
 * Mutable data model for the input.
 *
 * @author Brian Uri!
 */
public class Report implements Comparable {

	private Integer _id;
	private Set<Integer> _connections;

	/**
	 * Constructor
	 *
	 * 4 <-> 2, 3, 6
	 */
	public Report(String input) {
		String[] tokens = input.split(" <-> ");
		_id = Integer.valueOf(tokens[0]);
		_connections = new HashSet<>();
		for (String connection : tokens[1].split(", ")) {
			getConnections().add(Integer.valueOf(connection));
		}
	}

	@Override
	public int compareTo(Object o) {
		return (getId().compareTo(((Report) o).getId()));
	}

	/**
	 * Accessor for the id
	 */
	public Integer getId() {
		return _id;
	}

	/**
	 * Accessor for the list of connections
	 */
	public Set<Integer> getConnections() {
		return _connections;
	}
}