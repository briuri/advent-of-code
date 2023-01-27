package buri.aoc.y22.d17;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Day 17: Pyroclastic Flow
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(3068L, 1, false);
		assertRun(3197L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(1514285714288L, 1, false);
		assertRun(1568513119571L, 0, true);
	}

	// Need enough rows in the Part 2 snapshot to show uniqueness.
	// I tweaked this value until the answer was consistent with the smallest height.
	private static final int SNAPSHOT_HEIGHT = 375;

	/**
	 * Part 1:
	 * How many units tall will the tower of rocks be after 2022 rocks have stopped falling?
	 *
	 * Part 2:
	 * How tall will the tower be after 1000000000000 rocks have stopped?
	 */
	protected long runLong(Part part, List<String> input) {
		JetPattern pattern = new JetPattern(input.get(0));
		RockFactory rockFactory = new RockFactory();

		long rocksFallen = 0;
		long currentHeight = -1;
		final long MAX_ROCKS = (part == Part.ONE ? 2022L : 1000000000000L);
		Set<Pair<Long>> restingRocks = new HashSet<>();

		// Part 2 variables
		// Key is generated snapshot. Pair is the numRocks and the maxHeight.
		Map<String, Pair<Long>> states = new HashMap<>();
		long simulationHeight = 0;

		while (rocksFallen < MAX_ROCKS) {
			Set<Pair<Long>> rock = rockFactory.getNewRock(currentHeight + 4);
			while (!restingRocks.containsAll(rock)) {
				push(rock, restingRocks, pattern.next());
				fall(rock, restingRocks);
			}
			// Raise "floor" up to the highest part of the rock at rest.
			for (Pair<Long> point : rock) {
				currentHeight = Math.max(currentHeight, point.getY());
			}

			// Search for a cycle in Part 2.
			if (part == Part.TWO && currentHeight > SNAPSHOT_HEIGHT) {
				String stateKey = pattern.getIndex() + "-" + rockFactory.getIndex() + "-"
						+ getSnapshot(restingRocks, currentHeight);
				// When we reach a familiar state, simulate the cycle until we are close to a trillion rocks.
				if (states.containsKey(stateKey)) {
					long heightChangePerCycle = currentHeight - states.get(stateKey).getY();
					long rocksFallenPerCycle = rocksFallen - states.get(stateKey).getX();

					// How many more cycles will get us near maxRocks
					// (integer math gets us close, then we step through the last few rocks normally).
					long requiredCycles = (MAX_ROCKS - rocksFallen) / rocksFallenPerCycle;
					// Update the height growth over the cycles.
					simulationHeight += requiredCycles * heightChangePerCycle;
					// Set the rock counter ahead towards a trillon.
					rocksFallen += requiredCycles * rocksFallenPerCycle;
				}
				// Record new state.
				states.put(stateKey, new Pair<>(rocksFallen, currentHeight));
			}
			rocksFallen++;
		}

		if (part == Part.TWO) {
			// restingRocks is not updated during the simulation. So currentHeight only counts
			// the parts where we went step-by-step (pre-cycle and the last few rocks at the end).
			// Need to add the simulationHeight to account for all the complete cycles we simulated.
			currentHeight = currentHeight + simulationHeight;
		}
		// Floor is -1, so height is zero-based.
		return (currentHeight + 1);
	}

	/**
	 * Grabs the top rows of the blocks for use as part of a state key.
	 */
	protected static String getSnapshot(Set<Pair<Long>> restingRocks, long maxHeight) {
		StringBuilder builder = new StringBuilder();
		for (long y = maxHeight; y > maxHeight - SNAPSHOT_HEIGHT; y--) {
			for (long x = 0; x < 7; x++) {
				if (restingRocks.contains(new Pair<>(x, y))) {
					builder.append('#');
				}
				else {
					builder.append(".");
				}
			}
		}
		return builder.toString();
	}

	/**
	 * Pushes a rock left or right if it isn't blocked.
	 */
	protected static void push(Set<Pair<Long>> rock, Set<Pair<Long>> restingRocks, char direction) {
		int xInc = (direction == '<' ? -1 : 1);
		boolean isLegal = true;
		for (Pair<Long> point : rock) {
			long nextX = point.getX() + xInc;
			Pair<Long> testPoint = new Pair<>(nextX, point.getY());
			isLegal = isLegal && (nextX > -1 && nextX < 7) && !restingRocks.contains(testPoint);
		}
		if (isLegal) {
			for (Pair<Long> point : rock) {
				point.setX(point.getX() + xInc);
			}
		}
	}

	/**
	 * Pushes a rock down one level if it isn't blocked.
	 */
	protected static void fall(Set<Pair<Long>> rock, Set<Pair<Long>> restingRocks) {
		boolean isLegal = true;
		for (Pair<Long> point : rock) {
			long nextY = point.getY() - 1;
			Pair<Long> testPoint = new Pair<>(point.getX(), nextY);
			if (!rock.contains(testPoint)) {
				isLegal = isLegal && (nextY > -1) && !restingRocks.contains(testPoint);
			}
		}
		if (isLegal) {
			for (Pair<Long> point : rock) {
				point.setY(point.getY() - 1);
			}
		}
		else {
			restingRocks.addAll(rock);
		}
	}
}