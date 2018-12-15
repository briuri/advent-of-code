package buri.aoc.y17.d20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day20 extends Puzzle {

	/**
	 * Input: 9 comma-separated values, 3 position, 3 velocity, 3 acceleration
	 * Output: List of Particles
	 */
	public static List<Particle> getInput(int fileIndex) {
		List<Particle> list = new ArrayList<>();
		for (String particle : readFile("2017/20", fileIndex)) {
			list.add(new Particle(particle));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * Which particle will stay closest to position <0,0,0> in the long term?
	 * 
	 * Part 2:
	 * How many particles are left after all collisions are resolved?
	 */
	public static int getResult(Part part, List<Particle> input) {
		if (part == Part.ONE) {
			return (getSlowestAccelerationIndex(input));
		}
		
		// Part TWO
		for (int i = 0; i < 100; i++) {
			moveWithCollisions(input);
		}
		return (input.size());
	}

	/**
	 * Returns the particle index with the slowest acceleration.
	 */
	private static int getSlowestAccelerationIndex(List<Particle> input) {
		int index = -1;
		long minAcceleration = Long.MAX_VALUE;
		for (int i = 0; i < input.size(); i++) {
			Particle particle = input.get(i);
			long acceleration = particle.getAcceleration().getManhattanDistance();
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