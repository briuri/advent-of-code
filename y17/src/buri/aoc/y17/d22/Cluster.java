package buri.aoc.y17.d22;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.data.grid.IntGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Clean nodes are shown as .; infected nodes are shown as #. This map only shows the center of the grid; there are many
 * more nodes beyond those shown, but none of them are currently infected.
 * 
 * @author Brian Uri!
 */
public class Cluster extends IntGrid {
	private Virus _virus;
	private int _infections;

	private static final int GRID_BUFFER_RATIO = 243;

	public static final int CLEAN = 0;
	public static final int WEAK = 1;
	public static final int INFECTED = 2;
	public static final int FLAGGED = 3;

	/**
	 * Constructor
	 */
	public Cluster(List<String> input) {
		super(new Pair(input.get(0).length() * GRID_BUFFER_RATIO, input.size() * GRID_BUFFER_RATIO));
		int offset = input.get(0).length() * ((GRID_BUFFER_RATIO - 1) / 2);
		int centerCoordX = getWidth() / 2;
		int centerCoordY = getHeight() / 2;
		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < line.length(); x++) {
				Pair position = new Pair(x + offset, y + offset);
				char icon = line.charAt(x);
				set(position, (icon == '#' ? INFECTED : CLEAN));
			}
		}
		_virus = new Virus(new Pair(centerCoordX, centerCoordY));
		setInfections(0);
	}

	/**
	 * If the current node is infected, it turns to its right. Otherwise, it turns to its left. (Turning is done
	 * in-place; the current node does not change.)
	 * 
	 * If the current node is clean, it becomes infected. Otherwise, it becomes cleaned. (This is done after the node is
	 * considered for the purposes of changing direction.)
	 * 
	 * The virus carrier moves forward one node in the direction it is facing.
	 */
	public void move(Part part) {
		long node = get(getVirus().getPosition());
		getVirus().turn(node);
		alterNode(part);
		getVirus().move();
	}

	/**
	 * Updates the value of the node where the virus carrier is.
	 * 
	 * Part 1:
	 * Flip between CLEAN and INFECTED.
	 * 
	 * Part 2:
	 * Iterate across CLEAN -> WEAK -> INFECTED -> FLAGGED -> CLEAN.
	 */
	private void alterNode(Part part) {
		Integer node = get(getVirus().getPosition());
		if (part == Part.ONE) {
			node = (node == CLEAN ? INFECTED : CLEAN);
		}
		else {
			node += 1;
			if (node == 4) {
				node = CLEAN;
			}
		}
		set(getVirus().getPosition(), node);
		if (get(getVirus().getPosition()) == INFECTED) {
			setInfections(getInfections() + 1);
		}
	}

	/**
	 * Accessor for the virus
	 */
	private Virus getVirus() {
		return _virus;
	}

	/**
	 * Accessor for the infections
	 */
	public int getInfections() {
		return _infections;
	}

	/**
	 * Accessor for the infections
	 */
	private void setInfections(int infections) {
		_infections = infections;
	}
}
