package buri.aoc.y17.d22;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.data.AbstractGrid;

/**
 * Tracks consist of straight paths (| and -), curves (/ and \), and intersections (+). Curves connect exactly two
 * perpendicular pieces of track; for example, this is a closed loop:
 * 
 * Several carts are also on the tracks. Carts always face either up (^), down (v), left (<), or right (>). (On your
 * initial map, the track under each cart is a straight path matching the direction the cart is facing.)
 * 
 * @author Brian Uri!
 */
public class Cluster extends AbstractGrid {
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
		super(input.get(0).length() * GRID_BUFFER_RATIO);
		int offset = input.get(0).length() * ((GRID_BUFFER_RATIO - 1) / 2);
		int centerCoord = getSize() / 2;
		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < line.length(); x++) {
				Position position = new Position(x + offset, y + offset);
				char icon = line.charAt(x);
				set(position, (icon == '#' ? INFECTED : CLEAN));
			}
		}
		_virus = new Virus(new Position(centerCoord, centerCoord));
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
		long node = get(getVirus().getPosition());
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
	public Virus getVirus() {
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
