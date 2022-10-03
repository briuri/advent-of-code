package buri.aoc.y19.d17;

import java.util.List;

import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Data model for the vacuum scaffolding.
 * 
 * @author Brian Uri!
 */
public class VacuumGrid extends CharGrid {
	private static final char SCAFFOLD = 35;
	private static final char LINE_BREAK = 10;

	/**
	 * Constructor
	 */
	public VacuumGrid(List<Long> outputs) {
		super(new Pair(50, 50));
		int x = 0;
		int y = 0;
		for (Long value : outputs) {
			int tile = value.intValue();
			if (tile == LINE_BREAK) {
				y++;
				x = 0;
			}
			else {
				set(x, y, (char) tile);
				x++;
			}
		}
	}

	/**
	 * Returns the alignment parameters.
	 */
	public int getParameters() {
		int sum = 0;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				Pair<Integer> pos = new Pair(x, y);
				if (get(pos) == SCAFFOLD && isIntersection(pos)) {
					sum += (pos.getX() * pos.getY());
				}
			}
		}
		return (sum);
	}

	/**
	 * Checks the positions around a scaffold to see if it's an intersection.
	 */
	private boolean isIntersection(Pair<Integer> center) {
		boolean isIntersection = 
			(center.getY() > 0 && get(center.getX(), center.getY()	- 1) == SCAFFOLD)
			&& (center.getX() > 0 && get(center.getX() - 1, center.getY()) == SCAFFOLD) 
			&& (center.getX() < getWidth() - 1 && get(center.getX() + 1, center.getY()) == SCAFFOLD)
			&& (center.getY() < getHeight() - 1 && get(center.getX(), center.getY() + 1) == SCAFFOLD);
		return (isIntersection);
	}
}
