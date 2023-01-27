package buri.aoc.y16.d22;

import buri.aoc.common.data.tuple.Pair;

/**
 * @author Brian Uri!
 */
public class Node {
	private final Pair<Integer> _id;
	private final int _total;
	private final int _used;
	private final int _free;

	/**
	 * Constructor
	 */
	public Node(String input) {
		String[] tokens = input.split(" ");
		String[] id = tokens[0].split(",");
		_id = new Pair<>(Integer.valueOf(id[0]), Integer.valueOf(id[1]));
		_total = Integer.parseInt(tokens[1]);
		_used = Integer.parseInt(tokens[2]);
		_free = Integer.parseInt(tokens[3]);
	}

	/**
	 * Accessor for the id
	 */
	public Pair<Integer> getId() {
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