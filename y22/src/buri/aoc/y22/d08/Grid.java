package buri.aoc.y22.d08;

import buri.aoc.Part;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.grid.IntGrid;
import buri.aoc.data.path.Path;
import buri.aoc.data.path.Pathfinder;
import buri.aoc.data.path.StepStrategy;
import buri.aoc.data.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Data model for the grid of trees.
 *
 * @author Brian Uri!
 */
public class Grid extends IntGrid {
	/**
	 * Constructor
	 */
	public Grid(List<String> input) {
		super(new Pair(input.get(0).length(), input.size()));
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				set(x, y, Character.getNumericValue(input.get(y).charAt(x)));
			}
		}
	}

	/**
	 * Returns the part-specific answer.
	 */
	public long getAnswer(Part part) {
		return (part == Part.ONE ? getVisibleTrees() : getBestScenicScore());
	}

	/**
	 * Counts the trees visible from an edge.
	 */
	protected long getVisibleTrees() {
		long count = 0;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if (isVisible(x, y)) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Finds the tree with the highest scenic score.
	 */
	protected long getBestScenicScore() {
		List<Long> scores = new ArrayList<>();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				scores.add(getScore(x, y));
			}
		}
		Collections.sort(scores);
		return (scores.get(scores.size() - 1));
	}

	/**
	 * Checks if a tree is visible from any cardinal direction.
	 *
	 * A tree is visible if every tree in that direction is shorter.
	 */
	protected boolean isVisible(int x, int y) {
		int tree = get(x, y);

		boolean isVisibleN = true;
		for (int j = y - 1; j >= 0; j--) {
			isVisibleN = isVisibleN && (get(x, j) < tree);
		}
		boolean isVisibleS = true;
		for (int j = y + 1; j < getHeight(); j++) {
			isVisibleS = isVisibleS && (get(x, j) < tree);
		}
		boolean isVisibleW = true;
		for (int i = x - 1; i >= 0; i--) {
			isVisibleW = isVisibleW && (get(i, y) < tree);
		}
		boolean isVisibleE = true;
		for (int i = x + 1; i < getWidth(); i++) {
			isVisibleE = isVisibleE && (get(i, y) < tree);
		}

		return (isVisibleN || isVisibleS || isVisibleW || isVisibleE);
	}

	/**
	 * Calculates the scenic score for a single tree. The score is the number of trees visible in each direction,
	 * multiplied together.
	 */
	protected long getScore(int x, int y) {
		int tree = get(x, y);

		long scoreN = 0;
		for (int j = y - 1; j >= 0; j--) {
			scoreN++;
			if (get(x, j) >= tree) {
				break;
			}
		}
		long scoreS = 0;
		for (int j = y + 1; j < getHeight(); j++) {
			scoreS++;
			if (get(x, j) >= tree) {
				break;
			}
		}
		long scoreW = 0;
		for (int i = x - 1; i >= 0; i--) {
			scoreW++;
			if (get(i, y) >= tree) {
				break;
			}
		}
		long scoreE = 0;
		for (int i = x + 1; i < getWidth(); i++) {
			scoreE++;
			if (get(i, y) >= tree) {
				break;
			}
		}
		return (scoreN * scoreS * scoreW * scoreE);
	}
}