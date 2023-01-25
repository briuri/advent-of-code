package buri.aoc.y16.d19;

import java.util.ArrayDeque;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 19: An Elephant Named Joseph
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(3, Puzzle.getResult(Part.ONE, 5));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, 3005290);
		toConsole(result);
		assertEquals(1816277, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2, Puzzle.getResult(Part.TWO, 5));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, 3005290);
		toConsole(result);
		assertEquals(1410967, result);
	}

	/**
	 * Part 1:
	 * With the number of Elves given in your puzzle input, which Elf gets all the presents?
	 *
	 * Part 2:
	 * With the number of Elves given in your puzzle input, which Elf now gets all the presents?
	 */
	public static int getResult(Part part, int numElves) {
		if (part == Part.ONE) {
			ArrayDeque<Integer> circle = new ArrayDeque<>();
			for (int i = 1; i <= numElves; i++) {
				circle.add(i);
			}
			while (circle.size() > 1) {
				circle.addLast(circle.removeFirst());
				circle.removeFirst();
			}
			return (circle.removeFirst());
		}

		// Part TWO
		// Use a queue on each side of the loop to reduce rotation.
		ArrayDeque<Integer> left = new ArrayDeque<>();
		for (int i = 1; i <= numElves / 2; i++) {
			left.add(i);
		}
		ArrayDeque<Integer> right = new ArrayDeque<>();
		for (int i = numElves / 2 + 1; i <= numElves; i++) {
			right.add(i);
		}
		while (left.size() + right.size() > 1) {
			if (left.size() > right.size()) {
				left.removeLast();
			}
			else {
				right.removeFirst();
			}
			right.addLast(left.removeFirst());
			left.addLast(right.removeFirst());
		}
		return (left.isEmpty() ? right.removeFirst() : left.removeFirst());
	}
}