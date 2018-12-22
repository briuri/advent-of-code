package buri.aoc.y18.d22;

import java.util.ArrayList;
import java.util.HashMap;
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
	private Pair _target;
	private int[][] _erosionLevels;

	private static final char ROCKY = '.';
	private static final char WET = '=';
	private static final char NARROW = '|';
	
	private static final Pair MOUTH_POSITION = new Pair(0, 0);
	
	// Use the same character as the terrain where the equipment is forbidden to simplify swap checks.
	private static final char NONE = ROCKY;
	private static final char TORCH = WET;
	private static final char GEAR = NARROW;
	private static final char[] ALL_EQUIPMENT = new char[] { NONE, TORCH, GEAR };
	
	/**
	 * Constructor
	 */
	public Maze(int depth, Pair target) {
		super(1000);
		_depth = depth;
		_target = target;
		_erosionLevels = new int[getSize()][getSize()];
		for (int y = 0; y < getSize(); y++) {
			for (int x = 0; x < getSize(); x++) {
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
		if ((x == 0 && y == 0) ||  (x == getTarget().getX() && y == getTarget().getY())) {
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
		else if (erosionLevel% 3 == 1) {
			return (WET);
		}
		// (erosionLevel % 3 == 2)
		return (NARROW);
	}

	/**
	 * You start at MOUTH_POSITION with TORCH. The fastest route might involve entering regions beyond the X or Y
	 * coordinate of the target. You can move (COST=1) to an adjacent region (up, down, left, or right; never
	 * diagonally) if your currently equipped tool allows you to enter that region. (COST = 1 min) You can change your
	 * currently equipped tool (COST=7) if your new equipment would be valid for your current region.
	 * 
	 * You need TORCH to see target. Target is always in ROCK so if you arrive without TORCH, add (COST=7) to switch.
	 */
	public int getMinutes() {
		// Do a Djikstra search for the path. Sort nodes based on time to reach them so far.
		Queue<Node> frontier = new PriorityQueue<>();
		frontier.add(new Node(MOUTH_POSITION, 0, TORCH));
		Map<String, Integer> fastestTimes = new HashMap<>();
		while (true) {
			Node node = frontier.poll();
			// Successfully found the target while wielding TORCH
			if (node.getPair().equals(getTarget()) && node.getEquipment() == TORCH) {
				return (node.getCostSoFar());
			}
			
			// Evaluate moving to any allowable cells while keeping our current equipment.
			for (Pair neighbor : getTraversableNeighbors(node.getPair())) {
				if (!requiresSwap(node.getEquipment(), neighbor)) {
					String timeKey = createKey(neighbor, node.getEquipment());
					Integer fastestTime = fastestTimes.get(timeKey);
					if (fastestTime == null) {
						fastestTime = Integer.MAX_VALUE;
					}
					
					int newTime = node.getCostSoFar() + 1;
					// We found a faster way to go than the previous time we visited neighbor with this equipment.
					if (newTime < fastestTime) {
						frontier.add(new Node(neighbor, newTime, node.getEquipment()));
						fastestTimes.put(timeKey, newTime);
					}
				}
			}
						
			// Evaluate staying here and swapping equipment.
			for (char equipment : ALL_EQUIPMENT) {
				if (requiresSwap(equipment, node.getPair()) && equipment != node.getEquipment()) {
					String timeKey = createKey(node.getPair(), equipment);
					Integer fastestTime = fastestTimes.get(timeKey);
					if (fastestTime == null) {
						fastestTime = Integer.MAX_VALUE;
					}
					
					int newTime = node.getCostSoFar() + 7;
					// We found a faster way to go than the previous time we visited neighbor with this equipment.
					if (newTime < fastestTime) {
						int time = node.getCostSoFar() + 7;
						frontier.add(new Node(node.getPair(), time, equipment));						
						fastestTimes.put(timeKey, time);
					}
				}
			}
		}
	}
	
	/**
	 * Creates a unique key based on a pair and equipment.
	 */
	private String createKey(Pair pair, int equipment) {
		return (pair.toString() + "-" + equipment);
	}
	
	/**
	 * Returns traversable cells adjacent to some position.
	 */
	private List<Pair> getTraversableNeighbors(Pair center) {
		List<Pair> neighbors = new ArrayList<>();
		if (center.getY() >= 1) {
			neighbors.add(new Pair(center.getX(), center.getY() - 1));
		}
		if (center.getX() >= 1) {
			neighbors.add(new Pair(center.getX() - 1, center.getY()));
		}
		if (center.getX() < getSize() - 1) {
			neighbors.add(new Pair(center.getX() + 1, center.getY()));
		}
		if (center.getY() < getSize() - 1) {
			neighbors.add(new Pair(center.getX(), center.getY() + 1));
		}
		return (neighbors);
	}
	
	/**
	 * Returns true if the equipment must be swapped before entering next square. The character representation of each
	 * piece of equipment matches the character of the terrain it cannot enter.
	 * 
	 * - TORCH (usable in ROCK, NARROW) (start with this equipped)
	 * - GEAR (usable in ROCK, WET)
	 * - NONE (usable in WET, NARROW)
	 */
	private boolean requiresSwap(char equipment, Pair next) {
		return (equipment == get(next));
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
	public int[][] getErosionLevels() {
		return _erosionLevels;
	}
}
