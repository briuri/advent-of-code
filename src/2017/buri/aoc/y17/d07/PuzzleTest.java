package buri.aoc.y17.d07;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import buri.aoc.BaseTest;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testGetInput() {
		Map<String, Program> programs = Puzzle.getInput(0);
		assertEquals(1288, programs.size());
		assertTrue(programs.keySet().contains("mmqyju"));
		assertTrue(programs.get("mmqyju").getChildNames().contains("rjzvwv"));
		assertTrue(programs.get("mmqyju").getChildNames().contains("noybkx"));
		assertEquals(2, programs.get("mmqyju").getChildNames().size());
	}

	@Test
	public void testPart1Example() {
		assertEquals("tknk", Puzzle.getPart1Result(Puzzle.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getPart1Result(Puzzle.getInput(0));
		toConsole(result);
		assertEquals("svugo", result);
	}

	@Test
	public void testPart2Example() {
		Map<String, Program> programs = Puzzle.getInput(1);
		assertEquals(68, programs.get("ugml").getWeight());
		assertEquals(61, programs.get("gyxo").getWeight());
		assertEquals(61, programs.get("ebii").getWeight());
		assertEquals(61, programs.get("jptl").getWeight());
		assertEquals(251, programs.get("ugml").getTotalWeight());
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getPart2Result(Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1152, result);
	}
}
