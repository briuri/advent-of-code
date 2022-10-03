package buri.aoc.y19.d06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 06: Universal Orbit Map
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the total number of direct and indirect orbits in your map data?
	 *
	 * Part 2:
	 * What is the minimum number of orbital transfers required to move from the object YOU are orbiting to the object
	 * SAN is orbiting?
	 */
	public static int getResult(Part part, List<String> input) {
		// Build orbital map
		Map<String, Planet> planets = new HashMap<>();
		Planet com = new Planet("COM", null);
		planets.put(com.getName(), com);
		for (String orbit : input) {
			String[] tokens = orbit.split("\\)");
			if (!planets.containsKey(tokens[1])) {
				planets.put(tokens[1], new Planet(tokens[1], tokens[0]));
			}
		}
		// Initialize all parents.
		for (Planet planet : planets.values()) {
			if (planet.getParentName() != null) {
				planet.setParent(planets.get(planet.getParentName()));
			}
		}

		if (part == Part.ONE) {
			int orbits = 0;
			for (Planet planet : planets.values()) {
				while (planet.getParent() != null) {
					orbits++;
					planet = planet.getParent();
				}
			}
			return (orbits);
		}

		// Part TWO
		// Construct path from YOU to COM.
		Planet current = planets.get("YOU").getParent();
		List<String> youPath = new ArrayList<>();
		youPath.add(current.getName());
		while (current.getParent() != null) {
			youPath.add(current.getParent().getName());
			current = current.getParent();
		}

		// Count steps from SAN to first intersection in youPath.
		int steps = 0;
		current = planets.get("SAN").getParent();
		while (!youPath.contains(current.getName())) {
			steps++;
			current = current.getParent();
		}

		// Now add steps from YOU to intersection.
		steps += youPath.indexOf(current.getName());
		return (steps);
	}
}