package buri.aoc.y21.d13;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.grid.CharGrid;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Day 13: Transparent Origami
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals("17", Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("729", result);
	}

	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertTrue(result.startsWith("■■■   ■■  ■■■■ ■    ■■■  ■  ■ ■■■■ ■■■"));
		// RGZLBHFP
	}

	/**
	 * Part 1:
	 * How many dots are visible after completing just the first fold instruction on your transparent paper?
	 *
	 * Part 2:
	 * What code do you use to activate the infrared thermal imaging camera system?
	 */
	public static String getResult(Part part, List<String> input) {
		Set<Pair<Integer>> dots = new HashSet<>();
		List<String> folds = new ArrayList<>();
		boolean atFolds = false;
		for (String line : input) {
			if (line.length() == 0) {
				atFolds = true;
				continue;
			}
			if (!atFolds) {
				dots.add(new Pair(line, Integer.class));
			}
			else {
				folds.add(line);
			}
		}

		for (String fold : folds) {
			String[] tokens = fold.split("along ")[1].split("=");
			fold(dots, tokens[0], Integer.valueOf(tokens[1]));
			// Quit after one fold in part one.
			if (part == Part.ONE) {
				return ("" + dots.size());
			}
		}
		CharGrid grid = new CharGrid(new Pair<Integer>(40, 6));
		for (Pair<Integer> dot : dots) {
			grid.set(dot, '■');
		}
		return (grid.toString());
	}

	/**
	 * Executes one fold in the paper.
	 */
	protected static void fold(Set<Pair<Integer>> dots, String axis, int value) {
		Set<Pair<Integer>> foldedDots = new HashSet<>();
		for (Iterator<Pair<Integer>> iter = dots.iterator(); iter.hasNext();) {
			Pair<Integer> dot = iter.next();
			if (axis.equals("x") && dot.getX() > value) {
				iter.remove();
				dot.setX(value - (dot.getX() - value));
				foldedDots.add(dot);
			}
			if (axis.equals("y") && dot.getY() > value) {
				iter.remove();
				dot.setY(value - (dot.getY() - value));
				foldedDots.add(dot);
			}
		}
		dots.addAll(foldedDots);
	}
}