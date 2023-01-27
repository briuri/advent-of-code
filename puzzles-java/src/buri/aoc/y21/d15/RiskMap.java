package buri.aoc.y21.d15;

import buri.aoc.common.Part;
import buri.aoc.common.data.grid.IntGrid;
import buri.aoc.common.data.tuple.Pair;

import java.util.List;

/**
 * Data model for the risk map.
 *
 * @author Brian Uri!
 */
public class RiskMap extends IntGrid {
	private Pair<Integer> _end;

	/**
	 * Factory method to build a risk map.
	 */
	public static RiskMap buildMap(Part part, List<String> input) {
		RiskMap smallMap = new RiskMap(input);
		if (part == Part.ONE) {
			return (smallMap);
		}
		return (new RiskMap(smallMap));
	}

	/**
	 * Constructor for the smaller version of the map.
	 */
	private RiskMap(List<String> input) {
		super(new Pair<>(input.get(0).length(), input.size()));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, Character.getNumericValue(input.get(y).charAt(x)));
			}
		}
	}

	/**
	 * Constructor for the larger version of the risk map used in Part Two.
	 */
	public RiskMap(RiskMap smallMap) {
		super(new Pair<>(smallMap.getWidth() * 5, smallMap.getHeight() * 5));
		insert(smallMap, new Pair<>(0, 0));
		smallMap = smallMap.incrementedCopy();
		insert(smallMap, new Pair<>(1, 0));
		insert(smallMap, new Pair<>(0, 1));
		smallMap = smallMap.incrementedCopy();
		insert(smallMap, new Pair<>(2, 0));
		insert(smallMap, new Pair<>(1, 1));
		insert(smallMap, new Pair<>(0, 2));
		smallMap = smallMap.incrementedCopy();
		insert(smallMap, new Pair<>(3, 0));
		insert(smallMap, new Pair<>(2, 1));
		insert(smallMap, new Pair<>(1, 2));
		insert(smallMap, new Pair<>(0, 3));
		smallMap = smallMap.incrementedCopy();
		insert(smallMap, new Pair<>(4, 0));
		insert(smallMap, new Pair<>(3, 1));
		insert(smallMap, new Pair<>(2, 2));
		insert(smallMap, new Pair<>(1, 3));
		insert(smallMap, new Pair<>(0, 4));
		smallMap = smallMap.incrementedCopy();
		insert(smallMap, new Pair<>(4, 1));
		insert(smallMap, new Pair<>(3, 2));
		insert(smallMap, new Pair<>(2, 3));
		insert(smallMap, new Pair<>(1, 4));
		smallMap = smallMap.incrementedCopy();
		insert(smallMap, new Pair<>(4, 2));
		insert(smallMap, new Pair<>(3, 3));
		insert(smallMap, new Pair<>(2, 4));
		smallMap = smallMap.incrementedCopy();
		insert(smallMap, new Pair<>(4, 3));
		insert(smallMap, new Pair<>(3, 4));
		smallMap = smallMap.incrementedCopy();
		insert(smallMap, new Pair<>(4, 4));
	}

	/**
	 * Copy Constructor
	 */
	private RiskMap(Pair<Integer> size) {
		super(size);
	}

	/**
	 * Makes a copy of this risk map incremented by 1 risk level (wrap > 9 to 1).
	 */
	private RiskMap incrementedCopy() {
		RiskMap copy = new RiskMap(new Pair<>(this.getWidth(), this.getHeight()));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				int risk = this.get(x, y) + 1;
				if (risk > 9) {
					risk = 1;
				}
				copy.set(x, y, risk);
			}
		}
		return (copy);
	}

	/**
	 * Inserts another risk map at some offset in the larger risk map.
	 */
	private void insert(RiskMap smallGrid, Pair<Integer> offset) {
		offset.setX(offset.getX() * smallGrid.getWidth());
		offset.setY(offset.getY() * smallGrid.getHeight());
		for (int y = 0; y < smallGrid.getHeight(); y++) {
			for (int x = 0; x < smallGrid.getWidth(); x++) {
				set(offset.getX() + x, offset.getY() + y, smallGrid.get(x, y));
			}
		}
	}

	/**
	 * Accessor for the end position
	 */
	public Pair<Integer> getEnd() {
		if (_end == null) {
			_end = new Pair<>(getWidth() - 1, getHeight() - 1);
		}
		return (_end);
	}
}