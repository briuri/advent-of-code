package buri.aoc.y18.d22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import buri.aoc.data.Pair;
import buri.aoc.data.grid.CharGrid;

/**
 * Data class for the maze.
 * 
 * @author Brian Uri!
 */
public class Maze extends CharGrid {
	private int _depth;
	private Position _target;
	private int[][] _erosionLevels;

	private static final char ROCKY = '.';
	private static final char WET = '=';
	private static final char NARROW = '|';

	// Use the same character as the terrain where the item is forbidden to simplify swap checks.
	private static final char NONE = ROCKY;
	private static final char TORCH = WET;
	private static final char GEAR = NARROW;
	private static final char[] ALL_ITEMS = new char[] { NONE, TORCH, GEAR };

	private static final Position MOUTH_POSITION = new Position(0, 0, TORCH);

	/**
	 * Constructor
	 */
	public Maze(int depth, Pair target) {
		super(new Pair(Math.max(target.getX(), target.getY() + 10), Math.max(target.getX(), target.getY() + 10)));
		_depth = depth;
		_target = new Position(target.getX(), target.getY(), TORCH);
		_erosionLevels = new int[getWidth()][getHeight()];
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, getRegionType(x, y));
			}
		}
	}

	/**
	 * Returns total risk level for the smallest rectangle that includes 0,0 and the target.
	 */
	public int getRiskLevel() {
		int riskLevel = 0;
		for (int y = 0; y < getTarget().getY() + 1; y++) {
			for (int x = 0; x < getTarget().getX() + 1; x++) {
				// ROCKY areas have 0 risk.
				if (get(x, y) == WET) {
					riskLevel += 1;
				}
				else if (get(x, y) == NARROW) {
					riskLevel += 2;
				}
			}
		}
		return (riskLevel);
	}

	/**
	 * Method to determine any region's type based on its erosion level.
	 * 
	 * The geologic index can be determined using the first rule that applies from the list below:
	 * 
	 * The region at 0,0 (the mouth of the cave) has a geologic index of 0.
	 * The region at the coordinates of the target has a geologic index of 0.
	 * If the region's Y coordinate is 0, the geologic index is its X coordinate times 16807.
	 * If the region's X coordinate is 0, the geologic index is its Y coordinate times 48271.
	 * Otherwise, the region's geologic index is the result of multiplying the erosion levels of the regions at X-1,Y
	 * and X,Y-1.
	 * 
	 * A region's erosion level is its geologic index plus the cave system's depth, all modulo 20183. Then:
	 * 
	 * If the erosion level modulo 3 is 0, the region's type is rocky.
	 * If the erosion level modulo 3 is 1, the region's type is wet.
	 * If the erosion level modulo 3 is 2, the region's type is narrow.
	 * 
	 */
	private char getRegionType(int x, int y) {
		int geologicIndex;
		if ((x == 0 && y == 0) || (x == getTarget().getX() && y == getTarget().getY())) {
			geologicIndex = 0;
		}
		else if (y == 0) {
			geologicIndex = x * 16807;
		}
		else if (x == 0) {
			geologicIndex = y * 48271;
		}
		else {
			geologicIndex = (getErosionLevels()[x - 1][y] * getErosionLevels()[x][y - 1]);
		}
		int erosionLevel = (geologicIndex + getDepth()) % 20183;
		getErosionLevels()[x][y] = erosionLevel;
		if (erosionLevel % 3 == 0) {
			return (ROCKY);
		}
		else if (erosionLevel % 3 == 1) {
			return (WET);
		}
		// (erosionLevel % 3 == 2)
		return (NARROW);
	}

	/**
	 * You start at MOUTH_POSITION with TORCH. The fastest route might involve entering regions beyond the X or Y
	 * coordinate of the target. You can move (COST=1) to an adjacent region (up, down, left, or right; never
	 * diagonally) if your currently equipped tool allows you to enter that region. (COST=1) You can change your
	 * currently equipped tool (COST=7) if your new item would be valid for your current region.
	 * 
	 * You need TORCH to see target. Target is always in ROCK so if you arrive without TORCH, add (COST=7) to switch.
	 */
	public int getMinutes() {
		// Do a Djikstra search for the path. Sort nodes based on cost/time to reach them so far.
		// Only revisit unique positions (triple of x, y, and items) when it's a lower cost than the last visit.
		Queue<Node> frontier = new PriorityQueue<>();
		frontier.add(new Node(MOUTH_POSITION, 0));
		Map<String, Integer> visitedNodes = new HashMap<>();
		while (true) {
			Node node = frontier.poll();

			// Successfully found the target while wielding TORCH
			if (node.getPosition().equals(getTarget())) {
				return (node.getCostSoFar());
			}

			// Evaluate moving to any allowable cells while keeping our current item.
			for (Position next : getTraversableNeighbors(node.getPosition())) {
				evaluateNext(frontier, visitedNodes, next, node.getCostSoFar() + 1);
			}

			// Evaluate staying here and swapping items. (Always exactly 1 potential swap)
			for (char item : ALL_ITEMS) {
				if (item != node.getPosition().getItem() && isItemAllowed(item, node.getPosition())) {
					Position next = new Position(node.getPosition().getX(), node.getPosition().getY(), item);
					evaluateNext(frontier, visitedNodes, next, node.getCostSoFar() + 7);
				}
			}
		}
	}

	/**
	 * Helper method for Djikstra search that compares the time we reach the next node to the fastest time on record.
	 */
	private void evaluateNext(Queue<Node> frontier, Map<String, Integer> visitedNodes, Position next, int nextTime) {
		Integer fastestTime = visitedNodes.get(next.toString());
		if (fastestTime == null || nextTime < fastestTime) {
			frontier.add(new Node(next, nextTime));
			visitedNodes.put(next.toString(), nextTime);
		}
	}

	/**
	 * Returns traversable cells adjacent to some position, taking equipped item into account.
	 */
	private List<Position> getTraversableNeighbors(Position center) {
		List<Position> neighbors = new ArrayList<>();
		if (center.getY() >= 1) {
			neighbors.add(new Position(center.getX(), center.getY() - 1, center.getItem()));
		}
		if (center.getX() >= 1) {
			neighbors.add(new Position(center.getX() - 1, center.getY(), center.getItem()));
		}
		if (center.getX() < getWidth() - 1) {
			neighbors.add(new Position(center.getX() + 1, center.getY(), center.getItem()));
		}
		if (center.getY() < getHeight() - 1) {
			neighbors.add(new Position(center.getX(), center.getY() + 1, center.getItem()));
		}

		// Remove any with incompatible item / terrain matches.
		for (Iterator<Position> iterator = neighbors.iterator(); iterator.hasNext();) {
			Position position = iterator.next();
			if (!isItemAllowed(position.getItem(), position)) {
				iterator.remove();
			}
		}
		return (neighbors);
	}

	/**
	 * Returns true if the item is allowed on the terrain of the given position. The character representation of each
	 * item matches the character of the terrain it cannot enter.
	 * 
	 * Item allowed in terrain:
	 * NONE (.) usable in WET (=) and NARROW (|)
	 * TORCH (=) usable in ROCKY (.) and NARROW (|)
	 * GEAR (|) usable in ROCKY (.) and WET (=)
	 * 
	 * Terrains and forbidden items:
	 * ROCKY (.) forbids NONE (.)
	 * WET (=) forbids TORCH (=)
	 * NARROW (|) forbids GEAR (|)
	 */
	private boolean isItemAllowed(char item, Position position) {
		return (item != get(position));
	}

	/**
	 * Accessor for the depth
	 */
	private int getDepth() {
		return _depth;
	}

	/**
	 * Accessor for the target
	 */
	private Pair getTarget() {
		return _target;
	}

	/**
	 * Accessor for the erosionLevels
	 */
	private int[][] getErosionLevels() {
		return _erosionLevels;
	}
}
