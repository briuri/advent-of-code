package buri.aoc.y22.d10;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 10: Cathode-Ray Tube
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("13140", 1, false);
		assertRun("13680", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■  ■■  ", 1, false);
		// PZGPKPEB
		assertRun("■■■  ■■■■  ■■  ■■■  ■  ■ ■■■  ■■■■ ■■■  ", 0, true);
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
	protected String runString(Part part, List<String> input) {
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