package buri.aoc.y18.d22;

import buri.aoc.data.Pair;

/**
 * Encapsulation of the data needed to calculate the best path through the maze.
 * 
 * @author Brian Uri!
 */
public class Node implements Comparable<Node> {
	private Pair _pair;
	private int _costSoFar;
	private char _equipment;

	/**
	 * Constructor
	 */
	public Node(Pair pair, int costSoFar, char equipment) {
		_pair = pair;
		_costSoFar = costSoFar;
		_equipment = equipment;
	}

	/**
	 * Sorts nodes by cost in ascending order
	 */
	@Override
	public int compareTo(Node o) {
		return getCostSoFar() - o.getCostSoFar();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ("[" + getPair() + " " + getEquipment() + "] costSoFar=" + getCostSoFar());
	}

	/**
	 * Accessor for the pair
	 */
	public Pair getPair() {
		return _pair;
	}

	/**
	 * Accessor for the time used to get here
	 */
	public int getCostSoFar() {
		return _costSoFar;
	}

	/**
	 * Accessor for the equipment
	 */
	public char getEquipment() {
		return _equipment;
	}
}
