package buri.aoc.y18.d23;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.tuple.Triple;

/**
 * Day 23: Experimental Emergency Teleportation
 * 
 * @author Brian Uri!
 */
public class Day23 extends BasePuzzle {

	/**
	 * Returns the input file as a list of nanobots.
	 */
	public static List<Bot> getInput(int fileIndex) {
		List<Bot> list = new ArrayList<>();
		for (String line : readFile(fileIndex)) {
			list.add(new Bot(line));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * How many nanobots are in range of its signals?
	 * 
	 * Part 2:
	 * Find the coordinates that are in range of the largest number of nanobots. What is the shortest manhattan distance
	 * between any of those points and 0,0,0?
	 */
	public static long getResult(Part part, List<Bot> bots) {
		if (part == Part.ONE) {
			return (getBotsInRangeOfStrongest(bots));
		}
		return (getShortestDistance(bots));
	}

	/**
	 * Calculates how many bots are in range of a specific one.
	 */
	public static long getBotsInRangeOfStrongest(List<Bot> bots) {
		long radius = Integer.MIN_VALUE;
		Bot strongest = null;
		for (Bot bot : bots) {
			if (bot.getRadius() > radius) {
				radius = bot.getRadius();
				strongest = bot;
			}
		}

		long inRange = 0;
		for (Bot bot : bots) {
			if (strongest.inRange(bot.getPosition())) {
				inRange++;
			}
		}
		return (inRange);
	}

	/**
	 * Find the coordinates that are in range of the largest number of nanobots. if there are multiple, choose the one
	 * closest to origin.
	 * 
	 * Naive solution (too slow):
	 * - Store all possible coordinates in range of each bot in a frequency map.
	 * - Determine the frequency of positions that occurs most.
	 * - Find all positions that had that frequency and get the one with the smallest MD to origin.
	 * 
	 * Optimized solution (still too slow):
	 * - Only put the coordinates that are common between any 2 bots in the frequency map.
	 * - Determine the frequency of positions that occurs most.
	 * - Find all positions that had that frequency and get the one with the smallest MD to origin.
	 * 
	 * Also failed:
	 * - Try to calculate some sort of weighted center-of-mass (using radius to mock the weight) and search around
	 * there.
	 * 
	 * Final solution:
	 * - Get the bounds of all bot positions.
	 * - Break volume into chunks and take sample readings of the maxBotsInRange at different positions.
	 * - For the position with the most bots in range, resize the bounds and try again.
	 * - Keep trying until the position converges to a maxBots and the returned MD no longer changes.
	 */
	public static long getShortestDistance(List<Bot> bots) {
		// Get the general area.
		Triple min = new Triple(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);
		Triple max = new Triple(Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE);
		for (Bot bot : bots) {
			min.setX(Math.min(min.getX().longValue(), bot.getPosition().getX().longValue()));
			min.setY(Math.min(min.getY().longValue(), bot.getPosition().getY().longValue()));
			min.setZ(Math.min(min.getZ().longValue(), bot.getPosition().getZ().longValue()));
			max.setX(Math.max(max.getX().longValue(), bot.getPosition().getX().longValue()));
			max.setY(Math.max(max.getY().longValue(), bot.getPosition().getY().longValue()));
			max.setZ(Math.max(max.getZ().longValue(), bot.getPosition().getZ().longValue()));
		}

		// For my input, it took about 25 iterations before dx/dy/dz were all 1 and MD stabilized.
		int i = 0;
		long distance = 0;
		while (i < 30) {
			distance = sampleVolumeForMaxBots(bots, min, max);
			i++;
		}
		return (distance);
	}

	/**
	 * Samples points within a volume to see how many bots are in range. Finds the position with the most bots, then
	 * resizes min / max around it so this method can be called again. Returns the current smallest MD.
	 */
	private static long sampleVolumeForMaxBots(List<Bot> bots, Triple min, Triple max) {
		long searchRatio = 4;
		long resizeRatio = 1;
		long dx = Math.max(1, (max.getX().longValue() - min.getX().longValue()) / searchRatio);
		long dy = Math.max(1, (max.getY().longValue() - min.getY().longValue()) / searchRatio);
		long dz = Math.max(1, (max.getZ().longValue() - min.getZ().longValue()) / searchRatio);
		Triple origin = new Triple(0L, 0L, 0L);
		Triple positionWithMaxBots = null;
		int maxBotsInRange = 0;
		for (long z = min.getZ().longValue(); z < max.getZ().longValue() + 1; z += dz) {
			for (long y = min.getY().longValue(); y < max.getY().longValue() + 1; y += dy) {
				for (long x = min.getX().longValue(); x < max.getX().longValue() + 1; x += dx) {

					// Count up bots in range of this position.
					Triple sampledTriple = new Triple(x, y, z);
					int sampledMaxBots = 0;
					for (Bot bot : bots) {
						if (bot.inRange(sampledTriple)) {
							sampledMaxBots++;
						}
					}

					// We found a position with a higher number of bots than previously found.
					if (positionWithMaxBots == null || sampledMaxBots > maxBotsInRange) {
						positionWithMaxBots = sampledTriple;
						maxBotsInRange = sampledMaxBots;
					}
					// When we have a tie, favor the one with the smaller distance.
					else if (sampledMaxBots == maxBotsInRange
						&& sampledTriple.getManhattanDistance(origin) < positionWithMaxBots.getManhattanDistance(origin)) {
						positionWithMaxBots = sampledTriple;
					}
				}
			}
		}

		// Adjust min / max for next iteration.
		min.setX(positionWithMaxBots.getX().longValue() - (resizeRatio * dx));
		min.setY(positionWithMaxBots.getY().longValue() - (resizeRatio * dy));
		min.setZ(positionWithMaxBots.getZ().longValue() - (resizeRatio * dz));
		max.setX(positionWithMaxBots.getX().longValue() + (resizeRatio * dx));
		max.setY(positionWithMaxBots.getY().longValue() + (resizeRatio * dy));
		max.setZ(positionWithMaxBots.getZ().longValue() + (resizeRatio * dz));
		return (positionWithMaxBots.getManhattanDistance(origin));
	}
}