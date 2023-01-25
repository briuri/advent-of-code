package buri.aoc.y17.d20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Triple;

/**
 * Day 20: Particle Swarm
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	private static final Triple<Long> ORIGIN = new Triple(0L, 0L, 0L);

	/**
	 * Part 1:
	 * Which particle will stay closest to position <0,0,0> in the long term?
	 *
	 * Part 2:
	 * How many particles are left after all collisions are resolved?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Particle> particles = new ArrayList<>();
		for (String line : input) {
			particles.add(new Particle(line));
		}

		if (part == Part.ONE) {
			return (getSlowestAccelerationIndex(particles));
		}

		// Part TWO
		for (int i = 0; i < 100; i++) {
			moveWithCollisions(particles);
		}
		return (particles.size());
	}

	/**
	 * Returns the particle index with the slowest acceleration.
	 */
	private static int getSlowestAccelerationIndex(List<Particle> input) {
		int index = -1;
		long minAcceleration = Long.MAX_VALUE;

		for (int i = 0; i < input.size(); i++) {
			Particle particle = input.get(i);
			long acceleration = particle.getAcceleration().getManhattanDistance(ORIGIN);
			if (acceleration < minAcceleration) {
				index = i;
				minAcceleration = acceleration;
			}
		}
		return (index);
	}

	/**
	 * Removes any particles with the same position.
	 */
	private static void moveWithCollisions(List<Particle> input) {
		Map<String, List<Particle>> collisions = new HashMap<>();
		for (Particle particle : input) {
			String position = particle.move();
			if (collisions.get(position) == null) {
				collisions.put(position, new ArrayList<>());
			}
			collisions.get(position).add(particle);
		}
		for (String position : collisions.keySet()) {
			if (collisions.get(position).size() > 1) {
				for (Particle collided : collisions.get(position)) {
					input.remove(collided);
				}
			}
		}
	}
}