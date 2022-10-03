package buri.aoc.y20.d24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import buri.aoc.data.tuple.Triple;

/**
 * Data model for a hex grid floor. Each tile can be white (0) or black (1).
 *
 * @author Brian Uri!
 */
public class Floor {
	private Map<Triple<Integer>, Integer> _tiles;

	/**
	 * Constructor
	 */
	public Floor(List<String> input) {
		setTiles(new HashMap<>());
		Triple<Integer> start = new Triple<Integer>(0, 0, 0);
		for (String directions : input) {
			Triple<Integer> tile = getTile(directions, start);
			if (!getTiles().containsKey(tile)) {
				getTiles().put(tile, 0);
			}
			int newValue = (getTiles().get(tile) == 0 ? 1 : 0);
			getTiles().put(tile, newValue);
		}
	}

	/**
	 * Counts the number of black tiles.
	 */
	public int getBlackCount() {
		int count = 0;
		for (int value : getTiles().values()) {
			count += value;
		}
		return (count);
	}

	/**
	 * Follows a line of directions and reaches a tile.
	 */
	private Triple<Integer> getTile(String directions, Triple<Integer> start) {
		// Tokenize all steps.
		List<String> steps = new ArrayList<>();
		for (int i = 0; i < directions.length(); i++) {
			if (directions.charAt(i) == 'n' || directions.charAt(i) == 's') {
				steps.add(directions.substring(i, i + 2));
				i += 1;
			}
			else {
				steps.add(directions.substring(i, i + 1));
			}
		}

		// Step through directions.
		int x = start.getX();
		int y = start.getY();
		int z = start.getZ();
		for (String step : steps) {
			if (step.equals("nw")) {
				z = z - 1;
				y = y + 1;
			}
			else if (step.equals("ne")) {
				x = x + 1;
				z = z - 1;
			}
			else if (step.equals("e")) {
				x = x + 1;
				y = y - 1;
			}
			else if (step.equals("se")) {
				z = z + 1;
				y = y - 1;
			}
			else if (step.equals("sw")) {
				x = x - 1;
				z = z + 1;
			}
			else if (step.equals("w")) {
				x = x - 1;
				y = y + 1;
			}
		}
		return (new Triple(x, y, z));
	}

	/**
	 * Runs the living art cycle for 100 turns.
	 *
	 * Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
	 * Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
	 * All other tiles remain the same.
	 */
	public void cycleArt() {
		for (int i = 0; i < 100; i++) {
			// Add any tile adjacent to a black one (not just ones we have flipped before).
			Set<Triple<Integer>> allTiles = new HashSet<>(getTiles().keySet());
			for (Triple<Integer> tile : getTiles().keySet()) {
				if (getTiles().get(tile) == 1) {
					allTiles.addAll(getNeighbors(tile));
				}
			}

			Map<Triple<Integer>, Integer> next = new HashMap<>();
			for (Triple<Integer> tile : allTiles) {
				int blackCount = 0;
				for (Triple<Integer> neighbor : getNeighbors(tile)) {
					blackCount += getTiles().getOrDefault(neighbor, 0);
				}
				int value = getTiles().getOrDefault(tile, 0);
				if (value == 1 && (blackCount == 0 || blackCount > 2)) {
					value = 0;
				}
				else if (value == 0 && blackCount == 2) {
					value = 1;
				}
				next.put(tile, value);
			}
			setTiles(next);
		}
	}

	/**
	 * Returns all adjacent tiles.
	 */
	private Set<Triple<Integer>> getNeighbors(Triple<Integer> tile) {
		Set<Triple<Integer>> neighbors = new HashSet<>();
		neighbors.add(getTile("nw", tile));
		neighbors.add(getTile("ne", tile));
		neighbors.add(getTile("e", tile));
		neighbors.add(getTile("se", tile));
		neighbors.add(getTile("sw", tile));
		neighbors.add(getTile("w", tile));
		return (neighbors);
	}

	/**
	 * Accessor for the underlying floor data structure
	 */
	private Map<Triple<Integer>, Integer> getTiles() {
		return _tiles;
	}

	/**
	 * Accessor for the underlying floor data structure
	 */
	private void setTiles(Map<Triple<Integer>, Integer> tiles) {
		_tiles = tiles;
	}
}