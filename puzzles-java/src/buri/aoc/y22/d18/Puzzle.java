package buri.aoc.y22.d18;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Triple;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 18: Boiling Boulders
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {


	/**
	 * Part 1:
	 * What is the surface area of your scanned lava droplet?
	 *
	 * Part 2:
	 * What is the exterior surface area of your scanned lava droplet?
	 */
	public static long getResult(Part part, List<String> input) {
		long area = 0;
		Set<Triple<Integer>> cubes = new HashSet<>();
		for (String line : input) {
			String[] tokens = line.split(",");
			int x = Integer.parseInt(tokens[0]);
			int y = Integer.parseInt(tokens[1]);
			int z = Integer.parseInt(tokens[2]);
			cubes.add(new Triple<>(x, y, z));
		}
		for (Triple<Integer> cube : cubes) {
			// Get all adjacent spots that are cubes.
			Set<Triple<Integer>> neighbors = getAdjacent(cube);
			neighbors.retainAll(cubes);
			area += 6 - neighbors.size();
		}
		if (part == Part.ONE) {
			return (area);
		}

		// Part 2
		long innerArea = 0;
		Set<Triple<Integer>> spaces = new HashSet<>();
		// Get all adjacent spots that are not cubes.
		for (Triple<Integer> cube : cubes) {
			Set<Triple<Integer>> localSpaces = getAdjacent(cube);
			localSpaces.removeAll(cubes);
			spaces.addAll(localSpaces);
		}

		// Try to explore area starting from each not-cube spot.
		Set<Triple<Integer>> visited = new HashSet<>();
		for (Triple<Integer> space : spaces) {
			try {
				if (!visited.contains(space)) {
					Set<Triple<Integer>> reachable = new HashSet<>();
					explore(space, cubes, reachable);
					visited.addAll(reachable);
				}
			}
			catch (IllegalArgumentException e) {
				// Searching infinite space. Just move on to next test.
			}
		}

		// Now calculate the inner surface area of all the inner spaces.
		for (Triple<Integer> innerSpace : visited) {
			Set<Triple<Integer>> neighbors = getAdjacent(innerSpace);
			neighbors.removeAll(cubes);
			innerArea += 6 - neighbors.size();
		}
		return (area - innerArea);
	}

	/**
	 * Returns all 6 adjacent spots around a cube.
	 */
	protected static Set<Triple<Integer>> getAdjacent(Triple<Integer> cube) {
		Set<Triple<Integer>> neighbors = new HashSet<>();
		neighbors.add(new Triple<>(cube.getX() - 1, cube.getY(), cube.getZ()));
		neighbors.add(new Triple<>(cube.getX() + 1, cube.getY(), cube.getZ()));
		neighbors.add(new Triple<>(cube.getX(), cube.getY() - 1, cube.getZ()));
		neighbors.add(new Triple<>(cube.getX(), cube.getY() + 1, cube.getZ()));
		neighbors.add(new Triple<>(cube.getX(), cube.getY(), cube.getZ() - 1));
		neighbors.add(new Triple<>(cube.getX(), cube.getY(), cube.getZ() + 1));
		return neighbors;
	}

	/**
	 * Tries to explore from a space, in search of a finite (inner) space. Fail if we expect to be searching
	 * outside the shape.
	 */
	protected static void explore(Triple<Integer> space, Set<Triple<Integer>> cubes, Set<Triple<Integer>> reachable) {
		// Arbitrary bounds based on examining input data.
		if (space.getX() < 0 || space.getX() > 21
				|| space.getY() < 0 || space.getY() > 21
				|| space.getZ() < 0 || space.getZ() > 21) {
			throw new IllegalArgumentException("Can't explore an infinite space.");
		}
		reachable.add(space);
		for (Triple<Integer> neighbor : getAdjacent(space)) {
			if (!cubes.contains(neighbor) && !reachable.contains(neighbor)) {
				explore(neighbor, cubes, reachable);
			}
		}
	}
}