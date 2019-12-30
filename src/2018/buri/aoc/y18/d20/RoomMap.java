package buri.aoc.y18.d20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.path.Path;
import buri.aoc.data.path.Pathfinder;
import buri.aoc.data.path.StepStrategy;
import buri.aoc.data.tuple.Pair;

/**
 * @author Brian Uri!
 */
public class RoomMap extends CharGrid {

	private Pair<Integer> _start;

	private static final char WALL = '#';
	private static final char ROOM = '.';
	private static final char DOOR = 'O';
	private static final char START = 'X';

	private static final char REGEXP_END = '$';
	private static final char GROUP_START = '(';
	private static final char GROUP_CHOICE = '|';
	private static final char GROUP_END = ')';
	private static final char NORTH = 'N';
	private static final char EAST = 'E';
	private static final char SOUTH = 'S';
	private static final char WEST = 'W';

	private final StepStrategy<Pair<Integer>> STEP_STRATEGY = new StepStrategy<Pair<Integer>>() {
		@Override
		public List<Pair<Integer>> getNextSteps(Pair<Integer> current) {
			List<Pair<Integer>> nextSteps = current.getAdjacent();
			for (Iterator<Pair<Integer>> iterator = nextSteps.iterator(); iterator.hasNext();) {
				Pair<Integer> position = iterator.next();
				// Remove any that are walls.
				if (get(position) == WALL) {
					iterator.remove();
				}
			}
			return (nextSteps);
		}
	};

	/**
	 * Constructor
	 */
	public RoomMap() {
		super(new Pair(225, 225));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, WALL);
			}
		}

		// Start in the middle of the grid.
		_start = new Pair(getWidth() / 2, getHeight() / 2);
		set(getStart(), START);
	}

	/**
	 * Entry point for exploring the rooms from the starting location.
	 */
	public void explore(String input) {
		explore(getStart().copy(), input, new HashSet<>());
	}

	/**
	 * Recursive depth-first exploration of the rooms. Maintains string representations of the start/input to avoid
	 * revisiting.
	 */
	public void explore(Pair start, String input, Set<String> snapshots) {
		String snapshot = start + input;
		if (snapshots.contains(snapshot)) {
			return;
		}
		snapshots.add(snapshot);

		for (int i = 0; i < input.length(); i++) {
			char value = input.charAt(i);
			if (value == REGEXP_END || value == GROUP_END) {
				break;
			}
			if (value == GROUP_START) {
				// When a group is found, follow it all the way to the end of the input.
				int groupEndIndex = getGroupEndIndex(input, i) + 1;
				for (String option : subdivideGroup(input, i)) {
					option = option + input.substring(groupEndIndex);
					explore(start.copy(), option, snapshots);
				}
				break;
			}
			else {
				move(start, value);
			}
		}
	}

	/**
	 * Explores from a particular position in some direction.
	 */
	private void move(Pair<Integer> position, char direction) {
		if (direction == NORTH) {
			set(position.getX(), position.getY() - 1, DOOR);
			position.setY(position.getY() - 2);
		}
		else if (direction == EAST) {
			set(position.getX() + 1, position.getY(), DOOR);
			position.setX(position.getX() + 2);
		}
		else if (direction == SOUTH) {
			set(position.getX(), position.getY() + 1, DOOR);
			position.setY(position.getY() + 2);
		}
		else if (direction == WEST) {
			set(position.getX() - 1, position.getY(), DOOR);
			position.setX(position.getX() - 2);
		}
		if (get(position) != START) {
			set(position, ROOM);
		}
	}

	/**
	 * Splits up a group into potential options for pathing, ignoring inner groups.
	 */
	private List<String> subdivideGroup(String input, int groupStart) {
		List<String> choices = new ArrayList<>();

		int currentChoiceIndex = groupStart + 1;
		Stack<Integer> nestedGroups = new Stack<>();
		for (int i = groupStart; i < input.length(); i++) {
			char value = input.charAt(i);
			if (value == GROUP_START) {
				nestedGroups.push(i);
			}
			// Only parse choices that are at the same depth as the outer group.
			if (value == GROUP_CHOICE && nestedGroups.peek() == groupStart) {
				choices.add(input.substring(currentChoiceIndex, i));
				currentChoiceIndex = i + 1;
			}
			if (value == GROUP_END) {
				nestedGroups.pop();
				if (nestedGroups.isEmpty()) {
					choices.add(input.substring(currentChoiceIndex, i));
					break;
				}
			}
		}
		return (choices);
	}

	/**
	 * Locates the index of the end of a group, ignoring subgroups.
	 */
	private int getGroupEndIndex(String input, int groupStart) {
		Stack<Integer> nestedGroups = new Stack<>();
		for (int i = groupStart; i < input.length(); i++) {
			char value = input.charAt(i);
			if (value == GROUP_START) {
				nestedGroups.push(i);
			}
			if (value == GROUP_END) {
				nestedGroups.pop();
				if (nestedGroups.isEmpty()) {
					return (i);
				}
			}
		}
		throw new RuntimeException("Imbalanced parentheses.");
	}

	/**
	 * Find the room for which the shortest path from your starting location to that room would require passing
	 * through the most doors; what is the fewest doors you can pass through to reach it?
	 */
	public int getMostDoors() {
		// Every other cell on the path is a door.
		return ((getPaths().get(0).getLength() + 1) / 2);
	}

	/**
	 * Returns the number of rooms that have shortest paths and pass through (at minimum) a certain number of doors.
	 */
	public int getRoomsWithDoors(int minDoors) {
		int count = 0;
		for (Path path : getPaths()) {
			// Every other cell on the path is a door.
			if ((path.getLength() + 1) / 2 >= minDoors) {
				count++;
			}
		}
		return (count);
	}

	/**
	 * Does a BFS to find nearest rooms, then generates paths to those rooms in descending length order.
	 */
	private List<Path> getPaths() {
		Map<Pair<Integer>, Pair<Integer>> cameFrom = Pathfinder.breadthFirstSearch(getStart(), STEP_STRATEGY);
		// Get all known rooms.
		List<Pair<Integer>> destinations = new ArrayList<>();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if (get(x, y) == ROOM) {
					destinations.add(new Pair(x, y));
				}
			}
		}
		// Return all shortest paths in descending order of length.
		List<Path> paths = Pathfinder.toPaths(getStart(), destinations, cameFrom);
		Collections.reverse(paths);
		return (paths);
	}

	/**
	 * Accessor for the start
	 */
	private Pair<Integer> getStart() {
		return _start;
	}
}
