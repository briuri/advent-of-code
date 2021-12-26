package buri.aoc.y21.d23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Data model for a possible state in the amphipod game and the cost accrued so far.
 *
 * #############
 * #...........#
 * ###A#C#B#A###
 * ###D#C#B#A###
 * ###D#B#A#C###
 * ###D#D#B#C###
 * #############
 *
 * @author Brian Uri!
 */
public class State extends CharGrid implements Comparable<State> {
	private Long _cost;

	private static final char WALL = '#';
	private static final char OPEN = '.';
	private static final char A = 'A';
	private static final char B = 'B';
	private static final char C = 'C';
	private static final char D = 'D';

	private static final Map<Character, Integer> COSTS = new HashMap<>();
	static {
		COSTS.put(A, 1);
		COSTS.put(B, 10);
		COSTS.put(C, 100);
		COSTS.put(D, 1000);
	}
	private static final List<Pair<Integer>> HALL = new ArrayList<>();
	static {
		HALL.add(new Pair(1, 1));
		HALL.add(new Pair(2, 1));
		HALL.add(new Pair(4, 1));
		HALL.add(new Pair(6, 1));
		HALL.add(new Pair(8, 1));
		HALL.add(new Pair(10, 1));
		HALL.add(new Pair(11, 1));
	}
	private static final Map<Character, List<Pair<Integer>>> ROOMS = new HashMap<>();
	static {
		ArrayList<Pair<Integer>> roomA = new ArrayList<>();
		roomA.add(new Pair(3, 2));
		roomA.add(new Pair(3, 3));
		roomA.add(new Pair(3, 4));
		roomA.add(new Pair(3, 5));
		ArrayList<Pair<Integer>> roomB = new ArrayList<>();
		roomB.add(new Pair(5, 2));
		roomB.add(new Pair(5, 3));
		roomB.add(new Pair(5, 4));
		roomB.add(new Pair(5, 5));
		ArrayList<Pair<Integer>> roomC = new ArrayList<>();
		roomC.add(new Pair(7, 2));
		roomC.add(new Pair(7, 3));
		roomC.add(new Pair(7, 4));
		roomC.add(new Pair(7, 5));
		ArrayList<Pair<Integer>> roomD = new ArrayList<>();
		roomD.add(new Pair(9, 2));
		roomD.add(new Pair(9, 3));
		roomD.add(new Pair(9, 4));
		roomD.add(new Pair(9, 5));
		ROOMS.put(A, roomA);
		ROOMS.put(B, roomB);
		ROOMS.put(C, roomC);
		ROOMS.put(D, roomD);
	}

	/**
	 * Constructor
	 */
	public State(List<String> input) {
		this(input, 0L, 0L);
	}

	/**
	 * Constructor
	 */
	public State(List<String> input, Long cost, Long diffCost) {
		super(new Pair(input.get(0).length(), input.size()));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, input.get(y).charAt(x));
			}
		}
		_cost = cost;
	}

	/**
	 * Copy constructor
	 */
	private State(State state) {
		super(new Pair(state.getWidth(), state.getHeight()));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, state.get(x, y));
			}
		}
		_cost = state.getCost();
	}

	/**
	 * Create a set of all occupied hallway spots and all "front" spots in each room. For each amphipod, find all valid
	 * destinations. Calculate the move cost, create a new state for each possible move, and return all of these states.
	 */
	public List<State> getNextStates() {
		List<State> nextStates = new ArrayList<>();
		for (Pair<Integer> amphipod : getMovableAmphipods()) {
			List<Pair<Integer>> destinations = new ArrayList<>();
			// Always prioritize entering a room and increasing end count.
			Pair<Integer> roomDestination = getRoomDestination(amphipod);
			if (roomDestination != null) {
				destinations.add(roomDestination);
			}
			else {
				destinations.addAll(getHallDestinations(amphipod));
			}
			for (Pair<Integer> destination : destinations) {
				long distance = amphipod.getManhattanDistance(destination);
				char value = get(amphipod);

				State next = new State(this);
				// Replace finished amphipods with a wall.
				if (ROOMS.get(value).contains(destination) && get(destination.getX(), destination.getY() + 1) == WALL) {
					next.set(destination, WALL);
				}
				else {
					next.set(destination, value);
				}
				next.set(amphipod, OPEN);
				next.setCost(next.getCost() + distance * COSTS.get(value));
				nextStates.add(next);
			}
		}
		Collections.sort(nextStates);
		Collections.reverse(nextStates);
		return (nextStates);
	}

	/**
	 * Returns a list of locations of amphipods that can move:
	 * - In the hallway.
	 * - In the 0th position in a room (no one between it and the exit).
	 * - NOT in its own room, UNLESS there are different amphipods behind it.
	 */
	private List<Pair<Integer>> getMovableAmphipods() {
		List<Pair<Integer>> amphipods = new ArrayList<>();
		for (Pair<Integer> hall : HALL) {
			if (get(hall) != OPEN) {
				amphipods.add(hall);
			}
		}
		for (Character label : ROOMS.keySet()) {
			for (Pair<Integer> room : ROOMS.get(label)) {
				Character value = get(room);
				if (value != OPEN && value != WALL) {
					amphipods.add(room);
					break;
				}
			}
		}
		return (amphipods);
	}

	/**
	 * Returns all valid destinations that an amphipod can move to:
	 * - The nearest hallway spots that are not blocked.
	 */
	private List<Pair<Integer>> getHallDestinations(Pair<Integer> amphipod) {
		List<Pair<Integer>> destinations = new ArrayList<>();
		if (isInHall(amphipod)) {
			// See which hallway spots are nearby.
			int index = HALL.indexOf(amphipod);
			if (index - 1 >= 0 && isClear(amphipod, HALL.get(index - 1))) {
				destinations.add(HALL.get(index - 1));
			}
			if (index + 1 < HALL.size() && isClear(amphipod, HALL.get(index + 1))) {
				destinations.add(HALL.get(index + 1));
			}
		}
		else {
			Pair<Integer> left = new Pair(amphipod.getX() - 1, 1);
			Pair<Integer> right = new Pair(amphipod.getX() + 1, 1);
			if (isClear(amphipod, left)) {
				destinations.add(left);
			}
			if (isClear(amphipod, right)) {
				destinations.add(right);
			}
		}
		return (destinations);
	}

	/**
	 * Returns all valid room destinations that an amphipod can move to:
	 * - The furthest back spot in the amphipod's final room (assuming room has no different amphipods in it)
	 */
	private Pair<Integer> getRoomDestination(Pair<Integer> amphipod) {
		Character type = get(amphipod);
		Pair<Integer> destination = null;
		if (isInHall(amphipod)) {
			// See which rooms we can go in.
			for (Pair<Integer> room : ROOMS.get(type)) {
				Character value = get(room);
				if (value == OPEN) {
					destination = room;
				}
				// These amphipods need to leave before entry can occur.
				else if (value != WALL && get(room) != type) {
					destination = null;
				}
			}
		}
		if (destination != null && !isClear(amphipod, destination)) {
			destination = null;
		}
		return (destination);
	}

	/**
	 * Returns true if the amphipod location is in the hallway.
	 */
	private boolean isInHall(Pair<Integer> amphipod) {
		return (HALL.contains(amphipod));
	}

	/**
	 * Returns true if the path is clear one point to another is blocked.
	 */
	private boolean isClear(Pair<Integer> start, Pair<Integer> end) {
		boolean roomClear = true;
		for (int y = start.getY() - 1; y >= 1; y--) {
			roomClear = roomClear && (get(start.getX(), y) == OPEN);
		}
		boolean hallClear = true;
		int minX = Math.min(start.getX(), end.getX());
		int maxX = Math.max(start.getX(), end.getX());
		for (int x = minX + 1; x < maxX; x++) {
			hallClear = hallClear && (get(x, 1) == OPEN);
		}
		boolean endClear = (get(end) == OPEN);
		return (roomClear && hallClear && endClear);
	}

	/**
	 * Returns true if we're in the end state.
	 */
	public boolean isEnd() {
		return (isEnd(A) && isEnd(B) && isEnd(C) && isEnd(D));
	}

	/**
	 * Returns true if a room is in an end state (everything in the room is a wall).
	 */
	private boolean isEnd(Character label) {
		boolean isEnd = true;
		for (Pair<Integer> point : ROOMS.get(label)) {
			Character value = get(point);
			isEnd = isEnd && (value == WALL);
		}
		return (isEnd);
	}

	/**
	 * Returns the number of room squares that are a WALL (all rooms convert to all WALLs when finished).
	 */
	public Integer getEndCount() {
		Integer count = 0;
		for (Character label : ROOMS.keySet()) {
			for (Pair<Integer> room : ROOMS.get(label)) {
				if (get(room) == WALL) {
					count++;
				}
			}
		}
		return (count);
	}

	/**
	 * Comparison includes cost, so equal states with varying costs can be sorted. Prioritize by how many cells are in
	 * an end state, then cost.
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(State other) {
		// Descending order
		int compare = -1 * (getEndCount().compareTo(other.getEndCount()));
		if (compare == 0) {
			compare = getCost().compareTo(other.getCost());
		}
		return (compare);
	}

	/**
	 * Equality is used for key comparison only, so cost is not included.
	 */
	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj.toString());
	}

	/**
	 * For key comparison only, so cost is not included.
	 */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * Accessor for the cost
	 */
	public Long getCost() {
		return _cost;
	}

	/**
	 * Accessor for the cost
	 */
	public void setCost(Long cost) {
		_cost = cost;
	}
}
