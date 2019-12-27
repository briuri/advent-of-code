package buri.aoc.y19.d20;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import buri.aoc.Part;
import buri.aoc.data.Path;
import buri.aoc.data.Triple;
import buri.aoc.data.grid.CharGrid;

/**
 * Grid for the recursive maze.
 * 
 * @author Brian Uri!
 */
public class Maze extends CharGrid {
	private Triple _start;
	private Triple _end;
	private Map<Character, Triple> _warpPoints;

	private static final char WALL = '#';
	private static final char START = '@';
	private static final char END = '!';

	/**
	 * Constructor
	 */
	public Maze(List<String> input) {
		super(input.get(0).length());
		_warpPoints = new HashMap<>();

		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < line.length(); x++) {
				Character tile = line.charAt(x);
				Triple position = new Triple(x, y, 0);
				if (tile == START) {
					_start = position;
				}
				else if (tile == END) {
					_end = position;
				}
				// I hand-edited inputs to show letters/numbers on warp points, then removed AA labels and walled in.
				else if (Character.isAlphabetic(tile) || Character.isDigit(tile)) {
					getWarpPoints().put(tile, position);
				}
				set((int) position.getX(), (int) position.getY(), tile);
			}
		}
	}

	public int getSteps(Part part) {
		List<Triple> ends = new ArrayList<>();
		ends.add(getEnd());

		// BFS
		Queue<Triple> frontier = new ArrayDeque<>();
		frontier.add(getStart());
		Map<Triple, Triple> cameFrom = new HashMap<>();
		cameFrom.put(getStart(), null);
		Triple current = null;
		while (!frontier.isEmpty()) {
			current = frontier.remove();
			for (Triple next : getTraversableNeighbors(part, getStart(), current)) {
				if (cameFrom.get(next) == null) {
					frontier.add(next);
					cameFrom.put(next, current);
					if (next.equals(getEnd())) {
						break;
					}
				}
			}
		}

		List<Path> paths = Path.buildPaths(getStart(), ends, cameFrom);
		return (paths.get(0).getLength());
	}

	/**
	 * Returns traversable cells adjacent to some position, ignoring doors.
	 */
	private List<Triple> getTraversableNeighbors(Part part, Triple start, Triple current) {
		List<Triple> neighbors = new ArrayList<>();
		neighbors.add(new Triple(current.getX(), current.getY() - 1, current.getZ()));
		neighbors.add(new Triple(current.getX() - 1, current.getY(), current.getZ()));
		neighbors.add(new Triple(current.getX() + 1, current.getY(), current.getZ()));
		neighbors.add(new Triple(current.getX(), current.getY() + 1, current.getZ()));
		for (Iterator<Triple> iterator = neighbors.iterator(); iterator.hasNext();) {
			Triple position = iterator.next();
			Character tile = get((int) position.getX(), (int) position.getY());
			// Remove any that are not traversable.
			if (position.equals(start) || tile == WALL) {
				iterator.remove();
			}
		}
		// Add a teleport. Don't go too deep, in hopes that the answer is a closer path.
		if (isTeleport(part, current) && current.getZ() < getWarpPoints().size()) {
			Character tile = get((int) current.getX(), (int) current.getY());
			Triple target = getWarpPoints().get(getTarget(tile)).copy();
			if (part == Part.TWO) {
				if (Character.isLowerCase(tile) || tile == '0') {
					target.setZ(current.getZ() + 1);
				}
				else {
					target.setZ(current.getZ() - 1);
				}
			}
			neighbors.add(target);
		}
		return (neighbors);
	}

	/**
	 * Returns true if on a teleport tile.
	 */
	private boolean isTeleport(Part part, Triple current) {
		Character tile = get((int) current.getX(), (int) current.getY());
		boolean isPossibleTP = (Character.isAlphabetic(tile) || Character.isDigit(tile));
		if (part == Part.ONE || current.getZ() != 0) {
			return (isPossibleTP);
		}
		// Only inner TPs work on outermost level.
		return (isPossibleTP && Character.isLowerCase(tile) || tile == '0');
	}

	/**
	 * Returns the target of the teleport tile.
	 */
	private Character getTarget(Character teleport) {
		Character target;
		if (Character.isDigit(teleport)) {
			target = (teleport == '0' ? '1' : '0');
		}
		else {
			target = (Character.isUpperCase(teleport) ? Character.toLowerCase(teleport) : Character.toUpperCase(teleport));
		}
		return (target);
	}

	/**
	 * Accessor for the start
	 */
	private Triple getStart() {
		return _start;
	}

	/**
	 * Accessor for the end
	 */
	private Triple getEnd() {
		return _end;
	}

	/**
	 * Accessor for the warp points
	 */
	private Map<Character, Triple> getWarpPoints() {
		return _warpPoints;
	}
}
