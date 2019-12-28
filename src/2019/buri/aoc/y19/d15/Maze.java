package buri.aoc.y19.d15;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import buri.aoc.data.Direction;
import buri.aoc.data.Path;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.intcode.Computer;
import buri.aoc.data.tuple.Pair;

/**
 * Data model for the oxygen maze.
 * 
 * @author Brian Uri!
 */
public class Maze extends CharGrid {

	private Pair<Integer> _start;
	private Pair<Integer> _end;

	private static final char WALL = '■';
	private static final char UNEXPLORED = ' ';
	private static final char EXPLORED = '.';
	private static final char START = 'X';
	private static final char END = 'O';

	private static final long NORTH = 1L;
	private static final long SOUTH = 2L;
	private static final long WEST = 3L;
	private static final long EAST = 4L;

	/**
	 * Constructor
	 */
	public Maze() {
		super(new Pair(42, 42));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, UNEXPLORED);
			}
		}

		// Start in the middle of the grid.
		_start = getCenterPosition();
		set(getStart(), START);
	}

	/**
	 * After the maze is explored, find the shortest path to oxygen.
	 */
	public int getFewestCommands() {
		Set<Character> destinationTiles = new HashSet<>();
		destinationTiles.add(END);
		List<Path> paths = getPaths(getStart(), destinationTiles);
		return (paths.get(0).getLength());
	}

	/**
	 * After the maze is explored, find the longest path from oxygen to farthest square.
	 */
	public int getSpreadTime() {
		Set<Character> destinationTiles = new HashSet<>();
		destinationTiles.add(EXPLORED);
		destinationTiles.add(START);
		List<Path> paths = getPaths(getEnd(), destinationTiles);
		return (paths.get(0).getLength());
	}

	/**
	 * Entry point for exploring the maze from the starting location.
	 */
	public void explore(Computer droid) {
		explore(getStart().copy(), droid);
	}

	/**
	 * Recursive depth-first exploration of the maze
	 */
	public void explore(Pair<Integer> start, Computer droid) {
		// Droid's location in Intcode for debugging. Offset of 16 from my grid coordinates.
		// long x = droid.get(1039) + 16;
		// long y = droid.get(1040) + 16;

		if (get(start) != START && get(start) != END) {
			set(start, EXPLORED);
		}
		for (Direction direction : Direction.values()) {
			Pair<Integer> next = start.copy();
			next.move(direction);
			if (get(next) == UNEXPLORED) {
				droid.getInputs().add(toCommand(direction));
				droid.run();
				long status = droid.getOutputs().remove(0);
				if (status == 0) {
					set(next, WALL);
					continue;
				}
				if (status == 1) {
					explore(next, droid);
				}
				if (status == 2) {
					set(next, END);
					setEnd(next.copy());
				}
				// Move droid back on status 1 or 2
				droid.getInputs().add(toReverse(direction));
				droid.run();
				droid.getOutputs().remove(0);
			}
		}
	}

	/**
	 * Does a BFS determine path lengths.
	 */
	private List<Path> getPaths(Pair<Integer> origin, Set<Character> destinationTiles) {
		// Get all locations.
		List<Pair> destinations = new ArrayList<>();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if (destinationTiles.contains(get(x, y))) {
					destinations.add(new Pair(x, y));
				}
			}
		}

		// Do a BFS to find nearest location.
		Queue<Pair> frontier = new ArrayDeque<>();
		frontier.add(origin);
		Map<Pair, Pair> cameFrom = new HashMap<>();
		cameFrom.put(origin, null);
		Pair<Integer> current = null;
		while (!frontier.isEmpty()) {
			current = frontier.remove();
			for (Pair next : getTraversableNeighbors(current, origin)) {
				if (cameFrom.get(next) == null) {
					frontier.add(next);
					cameFrom.put(next, current);
				}
			}
		}

		// Return all paths in descending order of length.
		List<Path> paths = Path.buildPaths(origin, destinations, cameFrom);
		Collections.reverse(paths);
		return (paths);
	}

	/**
	 * Returns traversable cells adjacent to some position.
	 */
	private List<Pair> getTraversableNeighbors(Pair<Integer> center, Pair<Integer> origin) {
		List<Pair> neighbors = new ArrayList<>();
		neighbors.add(new Pair(center.getX(), center.getY() - 1));
		neighbors.add(new Pair(center.getX() - 1, center.getY()));
		neighbors.add(new Pair(center.getX() + 1, center.getY()));
		neighbors.add(new Pair(center.getX(), center.getY() + 1));
		// Remove any that are not traversable.
		for (Iterator<Pair> iterator = neighbors.iterator(); iterator.hasNext();) {
			Pair position = iterator.next();
			if (get(position) == WALL || position.equals(origin)) {
				iterator.remove();
			}
		}
		return (neighbors);
	}

	/**
	 * Converts a direction into an Intcode command.
	 */
	private static long toCommand(Direction direction) {
		if (direction == Direction.UP) {
			return (NORTH);
		}
		if (direction == Direction.DOWN) {
			return (SOUTH);
		}
		if (direction == Direction.LEFT) {
			return (WEST);
		}
		if (direction == Direction.RIGHT) {
			return (EAST);
		}
		throw new IllegalArgumentException("Unknown direction:" + direction);
	}

	/**
	 * Returns a reverse movement command.
	 */
	private static long toReverse(Direction direction) {
		if (direction == Direction.UP) {
			return (SOUTH);
		}
		if (direction == Direction.DOWN) {
			return (NORTH);
		}
		if (direction == Direction.LEFT) {
			return (EAST);
		}
		if (direction == Direction.RIGHT) {
			return (WEST);
		}
		throw new IllegalArgumentException("Unknown direction:" + direction);
	}

	/**
	 * Accessor for the start
	 */
	private Pair<Integer> getStart() {
		return _start;
	}

	/**
	 * Accessor for the end
	 */
	private Pair<Integer> getEnd() {
		return _end;
	}

	/**
	 * Accessor for the end
	 */
	private void setEnd(Pair end) {
		_end = end;
	}
}
