package buri.aoc.y16.d22;

import buri.aoc.data.Pair;

/**
 * @author Brian Uri!
 */
public class Node {
	private Pair _id;
	private int _total;
	private int _used;
	private int _free;
	
	/**
	 * Constructor
	 */
	public Node(String input) {
		String[] tokens = input.split(" ");
		String[] id = tokens[0].split(",");
		_id = new Pair(Integer.valueOf(id[0]), Integer.valueOf(id[1]));
		_total = Integer.valueOf(tokens[1]);
		_used = Integer.valueOf(tokens[2]);
		_free = Integer.valueOf(tokens[3]);
	}

	/**
	 * Accessor for the id
	 */
	public Pair getId() {
		return _id;
	}
	
	/**
	 * Accessor for the total space
	 */
	public int getTotal() {
		return _total;
	}

	/**
	 * Accessor for the used space
	 */
	public int getUsed() {
		return _used;
	}

	/**
	 * Accessor for the free space
	 */
	public int getFree() {
		return _free;
	}
}
