package buri.aoc.y22.d10;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Day 10: Cathode-Ray Tube
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals("13140", Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("13680", result);
	}

	@Test
	public void testPart2Examples() {
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(1));
		assertTrue(result.startsWith("■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■  "));
	}

	@Test
	public void testPart2Puzzle() {
		// Visual inspection: PZGPKPEB
		String result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertTrue(result.startsWith("■■■  ■■■■  ■■  ■■■  ■  ■ ■■■  ■■■■ ■■■  "));
	}

	private static final List<Integer> STRENGTH_CYCLES = new ArrayList<>();
	static {
		STRENGTH_CYCLES.add(20);
		STRENGTH_CYCLES.add(60);
		STRENGTH_CYCLES.add(100);
		STRENGTH_CYCLES.add(140);
		STRENGTH_CYCLES.add(180);
		STRENGTH_CYCLES.add(220);
	}

	private static final int GRID_WIDTH = 40;
	private static final int GRID_HEIGHT = 6;

	/**
	 * Part 1:
	 * What is the sum of these six signal strengths?
	 *
	 * Part 2:
	 * What eight capital letters appear on your CRT?
	 */
	public static String getResult(Part part, List<String> input) {
		int xRegister = 1;
		int cycle = 1;
		int signalStrength = 0;
		StringBuilder grid = new StringBuilder();

		for (String line : input) {
			String[] tokens = line.split(" ");
			if (tokens[0].equals("noop")) {
				draw(grid, cycle, xRegister);
				cycle += 1;
				signalStrength = signalStrength + checkStrength(cycle, xRegister);
			}
			else if (tokens[0].equals("addx")) {
				draw(grid, cycle, xRegister);
				cycle += 1;
				signalStrength = signalStrength + checkStrength(cycle, xRegister);

				draw(grid, cycle, xRegister);
				cycle += 1;
				xRegister += Integer.parseInt(tokens[1]);
				signalStrength = signalStrength + checkStrength(cycle, xRegister);
			}
		}

		// Covert into 40x6 grid after the fact.
		for (int height = GRID_HEIGHT - 1; height >= 1; height--) {
			grid.insert(height * GRID_WIDTH, '\n');
		}
		return (part == Part.ONE ? String.valueOf(signalStrength) : grid.toString());
	}

	/**
	 * Calculates a signal strength at specific cycles.
	 */
	protected static int checkStrength(int cycle, int xRegister) {
		return (STRENGTH_CYCLES.contains(cycle) ? cycle * xRegister : 0);
	}

	/**
	 * Writes to the CRT grid if the cursor's x-position is inside the xRegister sprite.
	 */
	protected static void draw(StringBuilder grid, int cycle, int xRegister) {
		int x = (cycle - 1) % GRID_WIDTH;
		grid.append(xRegister - 1 <= x && x <= xRegister + 1 ? '■' : ' ');
	}
}