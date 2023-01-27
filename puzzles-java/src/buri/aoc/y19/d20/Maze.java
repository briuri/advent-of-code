package buri.aoc.y19.d20;

import buri.aoc.common.Part;
import buri.aoc.common.data.grid.CharGrid;
import buri.aoc.common.data.path.Path;
import buri.aoc.common.data.path.Pathfinder;
import buri.aoc.common.data.path.StepStrategy;
import buri.aoc.common.data.tuple.Pair;
import buri.aoc.common.data.tuple.Triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Grid for the recursive maze.
 *
 * @author Brian Uri!
 */
public class Maze extends CharGrid {
	private Triple<Integer> _start;
	private Triple<Integer> _end;
	private Map<Character, Triple> _warpPoints;

	private static final char WALL = '#';
	private static final char START = '@';
	private static final char END = '!';

	/**
	 * Constructor
	 */
	public Maze(List<String> input) {
		super(new Pair(input.get(0).length(), input.size()));
		_warpPoints = new HashMap<>();

		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < line.length(); x++) {
				Character tile = line.charAt(x);
				Triple<Integer> position = new Triple(x, y, 0);
				if (tile == START) {
					_start = position;
				}
				else if (tile == END) {
					_end = position;
				}
				// I hand-edited inputs to show letters/numbers on warp points, then removed AA labels and walled in.
				else if (Character.isAlphabetic(tile) || Character.isDigit(tile)) {
					getWarpPoints().put(tile, position);
				}
				set((int) position.getX(), (int) position.getY(), tile);
			}
		}
	}

	/**
	 * Counts steps using part-specific rules for neighbor selection.
	 */
	public int getSteps(Part part) {
		WarpStepStrategy strategy = new WarpStepStrategy(part);
		Map<Triple<Integer>, Triple<Integer>> cameFrom = Pathfinder.breadthFirstSearch(getStart(), strategy);
		List<Triple<Integer>> ends = new ArrayList<>();
		ends.add(getEnd());
		List<Path<Triple<Integer>>> paths = Pathfinder.toPaths(ends, cameFrom);
		return (paths.get(0).getLength());
	}

	/**
	 * Returns true if on a teleport tile.
	 */
	private boolean isTeleport(Part part, Triple<Integer> current) {
		Character tile = get((int) current.getX(), (int) current.getY());
		boolean isPossibleTP = (Character.isAlphabetic(tile) || Character.isDigit(tile));
		if (part == Part.ONE || current.getZ() != 0) {
			return (isPossibleTP);
		}
		// Only inner TPs work on outermost level.
		return (isPossibleTP && Character.isLowerCase(tile) || tile == '0');
	}

	/**
	 * Returns the target of the teleport tile.
	 */
	private Character getTarget(Character teleport) {
		Character target;
		if (Character.isDigit(teleport)) {
			target = (teleport == '0' ? '1' : '0');
		}
		else {
			target = (Character.isUpperCase(teleport) ? Character.toLowerCase(teleport)
				: Character.toUpperCase(teleport));
		}
		return (target);
	}

	/**
	 * Accessor for the start
	 */
	private Triple<Integer> getStart() {
		return _start;
	}

	/**
	 * Accessor for the end
	 */
	private Triple<Integer> getEnd() {
		return _end;
	}

	/**
	 * Accessor for the warp points
	 */
	private Map<Character, Triple> getWarpPoints() {
		return _warpPoints;
	}

	class WarpStepStrategy implements StepStrategy<Triple<Integer>> {
		private Part _part;

		/**
		 * Constructor
		 */
		public WarpStepStrategy(Part part) {
			_part = part;
		}

		@Override
		public List<Triple<Integer>> getNextSteps(Triple<Integer> current) {
			List<Triple<Integer>> nextSteps = new ArrayList<>();
			nextSteps.add(new Triple(current.getX(), current.getY() - 1, current.getZ()));
			nextSteps.add(new Triple(current.getX() - 1, current.getY(), current.getZ()));
			nextSteps.add(new Triple(current.getX() + 1, current.getY(), current.getZ()));
			nextSteps.add(new Triple(current.getX(), current.getY() + 1, current.getZ()));
			for (Iterator<Triple<Integer>> iterator = nextSteps.iterator(); iterator.hasNext();) {
				Triple<Integer> position = iterator.next();
				Character tile = get(position.getX(), position.getY());
				// Remove any that are not traversable.
				if (tile == WALL) {
					iterator.remove();
				}
			}
			// Add a teleport. Don't go too deep, in hopes that the answer is a closer path.
			if (isTeleport(getPart(), current) && current.getZ() < getWarpPoints().size()) {
				Character tile = get(current.getX(), current.getY());
				Triple target = getWarpPoints().get(getTarget(tile)).copy();
				if (getPart() == Part.TWO) {
					if (Character.isLowerCase(tile) || tile == '0') {
						target.setZ(current.getZ() + 1);
					}
					else {
						target.setZ(current.getZ() - 1);
					}
				}
				nextSteps.add(target);
			}
			return (nextSteps);

		}

		/**
		 * Accessor for the part
		 */
		private Part getPart() {
			return _part;
		}
	}
}