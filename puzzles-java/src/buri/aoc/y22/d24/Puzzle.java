package buri.aoc.y22.d24;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.Direction;
import buri.aoc.common.data.tuple.Pair;
import buri.aoc.common.data.tuple.Triple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Day 24: Blizzard Basin
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(18L, 1, false);
		assertRun(314L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(54L, 1, false);
		assertRun(896L, 0, true);
	}

	private static final int MAX_MINUTES = 900;

	/**
	 * Part 1:
	 * What is the fewest number of minutes required to avoid the blizzards and reach the goal?
	 *
	 * Part 2:
	 * What is the fewest number of minutes required to reach the goal, go back to the start, then reach the goal again?
	 */
	protected long runLong(Part part, List<String> input) {
		int downWall = input.size() - 1;
		int rightWall = input.get(0).length() - 1;
		Blizzards blizzards = new Blizzards(MAX_MINUTES, 0, downWall, 0, rightWall);
		for (int y = 0; y <= downWall; y++) {
			for (int x = 0; x <= rightWall; x++) {
				Direction direction = Direction.getDirectionFor(input.get(y).charAt(x));
				if (direction != null) {
					blizzards.addBlizzard(x, y, direction);
				}
			}
		}

		Triple<Integer> start = new Triple<>(1, 0, 0);
		Pair<Integer> end = new Pair<>(rightWall - 1, downWall);
		int fewest = getFewestMinutes(start, end, blizzards);
		if (part == Part.TWO) {
			// Back to start
			start = new Triple<>(rightWall - 1, downWall, fewest);
			end = new Pair<>(1, 0);
			fewest += getFewestMinutes(start, end, blizzards);
			// Back to end
			start = new Triple<>(1, 0, fewest);
			end = new Pair<>(rightWall - 1, downWall);
			fewest += getFewestMinutes(start, end, blizzards);
		}
		return fewest;
	}

	/**
	 * Calculates the number of minutes needed to get from one spot (at a time) to another.
	 */
	protected static int getFewestMinutes(Triple<Integer> start, Pair<Integer> end, Blizzards blizzards) {
		List<Triple<Integer>> frontier = new ArrayList<>();
		frontier.add(start);
		while (!frontier.isEmpty()) {
			Set<Triple<Integer>> newFrontier = new HashSet<>();
			for (Triple<Integer> current : frontier) {
				if (current.getZ() > MAX_MINUTES) {
					break;
				}
				for (Triple<Integer> next : getNextSteps(current, start, end, blizzards)) {
					newFrontier.add(next);
					if (next.getX().equals(end.getX()) && next.getY().equals(end.getY())) {
						return (next.getZ() - start.getZ());
					}
				}
			}
			frontier = new ArrayList<>(newFrontier);
		}
		throw new RuntimeException("Couldn't find the destination.");
	}

	protected static List<Triple<Integer>> getNextSteps(Triple<Integer> current, Triple<Integer> start,
														Pair<Integer> end, Blizzards blizzards) {
		boolean onStart = current.getX().equals(start.getX()) && current.getY().equals(start.getY());
		List<Triple<Integer>> nextSteps = new ArrayList<>();
		nextSteps.add(new Triple<>(current.getX(), current.getY(), current.getZ() + 1));
		nextSteps.add(new Triple<>(current.getX(), current.getY() - 1, current.getZ() + 1));
		nextSteps.add(new Triple<>(current.getX(), current.getY() + 1, current.getZ() + 1));
		nextSteps.add(new Triple<>(current.getX() - 1, current.getY(), current.getZ() + 1));
		nextSteps.add(new Triple<>(current.getX() + 1, current.getY(), current.getZ() + 1));

		for (Iterator<Triple<Integer>> iterator = nextSteps.iterator(); iterator.hasNext(); ) {
			Triple<Integer> position = iterator.next();
			boolean nextIsStart = position.getX().equals(start.getX()) && position.getY().equals(start.getY());
			// Special wall case: Allow start spot if we haven't left it yet.
			if (onStart && nextIsStart) {
				continue;
			}
			// Special wall case: Allow us to reach the end spot.
			if (position.getX().equals(end.getX()) && position.getY().equals(end.getY())) {
				continue;
			}
			// Remove out of bounds, walls, or blizzards
			if (position.getX() <= blizzards.getLeftWall() || position.getX() >= blizzards.getRightWall()
					|| position.getY() <= blizzards.getUpWall() || position.getY() >= blizzards.getDownWall()
					|| blizzards.getBlizzards().contains(position)) {
				iterator.remove();
			}
		}
		return (nextSteps);
	}
}