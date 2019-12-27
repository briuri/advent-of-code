package buri.aoc.y16.d13;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import buri.aoc.data.Pair;
import buri.aoc.data.Path;
import buri.aoc.data.grid.CharGrid;

/**
 * Map of the cubicles.
 * 
 * @author Brian Uri!
 */
public class Grid extends CharGrid {

	private static final char WALL = '#';
	private static final char OPEN = '.';
	private static final char START = 'O';

	private static final Pair START_POSITION = new Pair(1, 1);

	/**
	 * Constructor
	 */
	public Grid(int magicNumber) {
		super(new Pair(50, 50));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				int interim = (x * x) + (3 * x) + (2 * x * y) + y + (y * y) + magicNumber;
				int oneCount = Integer.toBinaryString(interim).replaceAll("0", "").length();
				char value = (oneCount % 2 == 0 ? OPEN : WALL);
				set(x, y, value);
			}
		}
		set(START_POSITION, START);
	}

	/**
	 * Returns the minimum number of steps to a single destination.
	 */
	public int getStepsTo(Pair destination) {
		List<Pair> destinations = new ArrayList<>();
		destinations.add(destination);

		// There will be 1 path returned, since only 1 destination was provided.
		List<Path> shortestPaths = getShortestPaths(destinations);
		return (shortestPaths.get(0).getLength());
	}

	/**
	 * Returns the number of locations reachable within X steps.
	 */
	public int getClosePositions(int steps) {
		// Add all potential destinations.
		List<Pair> destinations = new ArrayList<>();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				char value = get(x, y);
				if (value == OPEN) {
					destinations.add(new Pair(x, y));
				}
			}
		}

		int count = 0;
		for (Path path : getShortestPaths(destinations)) {
			if (path.getLength() <= steps) {
				count++;
			}
		}
		
		// Add one for the path of staying on the start location without moving.
		return (count + 1);
	}

	/**
	 * Does a BFS to find shortest paths.
	 */
	private List<Path> getShortestPaths(List<Pair> destinations) {
		// Do a BFS to find destination.
		Queue<Pair> frontier = new ArrayDeque<>();
		frontier.add(START_POSITION);
		Map<Pair, Pair> cameFrom = new HashMap<>();
		cameFrom.put(START_POSITION, null);
		Pair current = null;
		while (!frontier.isEmpty()) {
			current = frontier.remove();
			for (Pair next : getTraversableNeighbors(current)) {
				if (cameFrom.get(next) == null) {
					frontier.add(next);
					cameFrom.put(next, current);
				}
			}
		}
		return (Path.buildPaths(START_POSITION, destinations, cameFrom));
	}

	/**
	 * Returns traversable cells adjacent to some position.
	 */
	private List<Pair> getTraversableNeighbors(Pair center) {
		List<Pair> neighbors = new ArrayList<>();
		if (center.getY() > 0) {
			neighbors.add(new Pair(center.getX(), center.getY() - 1));
		}
		if (center.getX() > 0) {
			neighbors.add(new Pair(center.getX() - 1, center.getY()));
		}
		if (center.getX() < getWidth() - 1) {
			neighbors.add(new Pair(center.getX() + 1, center.getY()));
		}
		if (center.getY() < getHeight() - 1) {
			neighbors.add(new Pair(center.getX(), center.getY() + 1));
		}
		// Remove any that are not traversable.
		for (Iterator<Pair> iterator = neighbors.iterator(); iterator.hasNext();) {
			Pair position = iterator.next();
			if (get(position) == WALL || get(position) == START) {
				iterator.remove();
			}
		}
		return (neighbors);
	}
}
