package buri.aoc.y22.d19;

import buri.aoc.common.data.tuple.Pair;

/**
 * Data model for a blueprint.
 *
 * @author Brian Uri!
 */
public class Blueprint {
	private final int _id;
	private final Pair<Integer> _oreRobot;
	private final Pair<Integer> _clayRobot;
	private final Pair<Integer> _obsidianRobot;
	private final Pair<Integer> _geodeRobot;

	public static final int ORE = 0;
	public static final int CLAY = 1;
	public static final int OBSIDIAN = 2;

	/**
	 * Constructor
	 */
	public Blueprint(String line) {
		_id = Integer.parseInt(line.split(": ")[0].split(" ")[1]);
		_oreRobot = new Pair<>(Integer.parseInt(line.split("ore robot costs ")[1].split(" ore")[0]), 0);
		_clayRobot = new Pair<>(Integer.parseInt(line.split("clay robot costs ")[1].split(" ore")[0]), 0);

		int ore = Integer.parseInt(line.split("obsidian robot costs ")[1].split(" ore")[0]);
		int clay = Integer.parseInt(line.split("ore and ")[1].split(" clay")[0]);
		_obsidianRobot = new Pair<>(ore, clay);

		ore = Integer.parseInt(line.split("geode robot costs ")[1].split(" ore")[0]);
		clay = Integer.parseInt(line.split("ore and ")[2].split(" obsidian")[0]);
		_geodeRobot = new Pair<>(ore, clay);
	}

	@Override
	public String toString() {
		return (getId() + "[" + getOreRobot() + " " + getClayRobot() + " " + getObsidianRobot() + " " + getGeodeRobot() + "]");
	}

	/**
	 * Accessor for the blueprint ID
	 */
	public int getId() {
		return _id;
	}

	/**
	 * Accessor for the cost of an ore robot.
	 */
	public int getOreRobotCost(int resourceType) {
		if (resourceType == ORE) {
			return getOreRobot().getX();
		}
		return (0);
	}

	/**
	 * Accessor for the cost of a clay robot.
	 */
	public int getClayRobotCost(int resourceType) {
		if (resourceType == ORE) {
			return getClayRobot().getX();
		}
		return (0);
	}

	/**
	 * Accessor for the cost of an obsidian robot.
	 */
	public int getObsidianRobotCost(int resourceType) {
		if (resourceType == ORE) {
			return getObsidianRobot().getX();
		}
		if (resourceType == CLAY) {
			return getObsidianRobot().getY();
		}
		return (0);
	}

	/**
	 * Accessor for the cost of a geode robot.
	 */
	public int getGeodeRobotCost(int resourceType) {
		if (resourceType == ORE) {
			return getGeodeRobot().getX();
		}
		if (resourceType == OBSIDIAN) {
			return getGeodeRobot().getY();
		}
		return (0);
	}

	private Pair<Integer> getOreRobot() {
		return _oreRobot;
	}

	private Pair<Integer> getClayRobot() {
		return _clayRobot;
	}

	private Pair<Integer> getObsidianRobot() {
		return _obsidianRobot;
	}

	private Pair<Integer> getGeodeRobot() {
		return _geodeRobot;
	}
}