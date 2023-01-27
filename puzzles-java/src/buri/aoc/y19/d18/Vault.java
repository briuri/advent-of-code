package buri.aoc.y19.d18;

import buri.aoc.common.data.grid.CharGrid;
import buri.aoc.common.data.path.Path;
import buri.aoc.common.data.path.Pathfinder;
import buri.aoc.common.data.path.StepStrategy;
import buri.aoc.common.data.tuple.Pair;
import buri.aoc.common.PuzzleMath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Grid for the vault.
 *
 * @author Brian Uri!
 */
public class Vault extends CharGrid {
	private Map<Character, Pair> _starts;
	private Map<Character, Pair> _keys;
	private Map<Character, Pair> _doors;
	private Set<Route> _routes;

	private static final char WALL = '#';
	private static final char START = '@';

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
	public Vault(List<String> input) {
		super(new Pair(input.get(0).length(), input.size()));
		_starts = new HashMap<>();
		_doors = new HashMap<>();
		_keys = new HashMap<>();

		// Build vault map
		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < line.length(); x++) {
				Character tile = line.charAt(x);
				Pair position = new Pair(x, y);
				// Use numbers 0-3 instead of @ for starting points.
				if (tile == START) {
					tile = Character.forDigit(getStarts().size(), 10);
					getStarts().put(tile, position);
				}
				else if (isKey(tile)) {
					_keys.put(tile, position);
				}
				else if (isDoor(tile)) {
					_doors.put(tile, position);
				}
				set(position, tile);
			}
		}

		// Build routes
		Map<String, Route> routes = new HashMap<>();
		Set<Character> routePoints = new HashSet<>();
		routePoints.addAll(getStarts().keySet());
		routePoints.addAll(getKeys().keySet());
		for (Character startChar : routePoints) {
			for (Character endChar : routePoints) {
				// Calculate route just once, representing both directions.
				String key = "" + startChar + endChar;
				String reverseKey = "" + endChar + startChar;
				if (startChar.equals(endChar) || routes.containsKey(reverseKey)) {
					continue;
				}
				Route route = mapRoute(startChar, endChar);
				if (route != null) {
					routes.put(key, route);
				}
			}
		}
		_routes = new HashSet<>(routes.values());
	}

	/**
	 * Walks a route to find all the doors and the number of steps (assuming all keys acquired).
	 */
	private Route mapRoute(Character startChar, Character endChar) {
		Pair<Integer> start = getRoutePositionFor(startChar);
		Pair<Integer> end = getRoutePositionFor(endChar);

		// BFS with all doors unlocked
		Map<Pair<Integer>, Pair<Integer>> cameFrom = Pathfinder.breadthFirstSearch(start, STEP_STRATEGY);
		List<Pair<Integer>> possibleKeys = new ArrayList<>();
		possibleKeys.add(end);

		// Exactly 1 path between keys in Part 1. Possibly 0 paths in Part 2.
		List<Path<Pair<Integer>>> paths = Pathfinder.toPaths(possibleKeys, cameFrom);
		if (paths.isEmpty()) {
			return (null);
		}
		Path path = paths.get(0);
		Route route = new Route(startChar, endChar, path.getLength());
		for (Pair position : (List<Pair>) path.getSteps()) {
			Character tile = get(position);
			if (isDoor(tile)) {
				route.getDoors().add(tile);
			}
		}
		return (route);
	}

	/**
	 * Calculates the shortest path through the vault.
	 */
	public int getFewestSteps() {
		int steps = 0;
		for (Character start : getStarts().keySet()) {
			// In each vault, assume that the robot will eventually have access to any key it can't reach on its own.
			// For Part 1, this means the robot starts with no keys and eventually reaches them all.
			// NOTE: This assumption does not work for the maze in 18-10.txt.
			List<Character> acquiredKeys = new ArrayList<>();
			for (Character key : getKeys().keySet()) {
				if (!routeExists(start, key)) {
					acquiredKeys.add(key);
				}
			}
			steps += explore(new HashMap<>(), acquiredKeys, start);
		}
		return (steps);
	}

	/**
	 * Recursively explore to collect all keys.
	 */
	private int explore(Map<String, Integer> stepCache, List<Character> acquiredKeys, Character start) {
		String cacheKey = start + "-" + toCacheKey(acquiredKeys);
		if (stepCache.get(cacheKey) != null) {
			return (stepCache.get(cacheKey));
		}
		int steps = 0;
		List<Route> routes = getRoutes(acquiredKeys, start);
		if (routes.size() > 0) {
			Map<Character, Integer> possibleSteps = new HashMap<>();
			for (Route route : routes) {
				Character end = route.getOtherEndpoint(start);
				List<Character> newKeys = new ArrayList<>(acquiredKeys);
				newKeys.add(end);
				int interimSteps = route.getSteps() + explore(stepCache, newKeys, end);
				possibleSteps.put(end, interimSteps);
			}
			Map.Entry<Character, Integer> minimum = PuzzleMath.getMin(possibleSteps);
			steps = minimum.getValue();
		}
		stepCache.put(cacheKey, steps);
		return (steps);
	}

	/**
	 * Looks up all traversable routes from a position.
	 */
	private List<Route> getRoutes(List<Character> acquiredKeys, Character start) {
		List<Route> routes = new ArrayList<>();
		for (Route route : getRoutes()) {
			if (route.getEndpoints().contains(start) && route.isTraversable(acquiredKeys)) {
				Character end = route.getOtherEndpoint(start);
				// Don't return to beginning or an already claimed key.
				if (!getStarts().containsKey(end) && !acquiredKeys.contains(end)) {
					routes.add(route);
				}
			}
		}
		return (routes);
	}

	/**
	 * Returns true if there is a route between two locations.
	 */
	private boolean routeExists(Character start, Character end) {
		for (Route route : getRoutes()) {
			if (route.getEndpoints().contains(start) && route.getOtherEndpoint(start).equals(end)) {
				return (true);
			}
		}
		return (false);
	}

	/**
	 * Returns true for one of the four starting points.
	 */
	private boolean isStart(Character character) {
		return (Character.isDigit(character));
	}

	/**
	 * Returns true for keys.
	 */
	private boolean isKey(Character character) {
		return (Character.isLowerCase(character));
	}

	/**
	 * Returns true for doors.
	 */
	private boolean isDoor(Character character) {
		return (Character.isUpperCase(character));
	}

	/**
	 * Returns the location of a key or the start.
	 */
	private Pair<Integer> getRoutePositionFor(Character character) {
		if (isStart(character)) {
			return (getStarts().get(character));
		}
		return (getKeys().get(character));
	}

	/**
	 * Converts the key list into a string.
	 */
	private String toCacheKey(List<Character> acquiredKeys) {
		List<Character> sortedKeys = new ArrayList<>(acquiredKeys);
		Collections.sort(sortedKeys);
		StringBuffer buffer = new StringBuffer();
		for (Character key : sortedKeys) {
			buffer.append(key);
		}
		return (buffer.toString());
	}

	/**
	 * Accessor for the starting points
	 */
	private Map<Character, Pair> getStarts() {
		return _starts;
	}

	/**
	 * Accessor for the keys
	 */
	private Map<Character, Pair> getKeys() {
		return _keys;
	}

	/**
	 * Accessor for the routes
	 */
	private Set<Route> getRoutes() {
		return (_routes);
	}
}