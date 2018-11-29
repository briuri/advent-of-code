package buri.aoc.y17.d07;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

/**
 * @author Brian Uri!
 */
public class Day07Test {

	@Test
	public void testGetInput() {
		Map<String, Program> programs = Day07.getInput(0);
		assertEquals(1288, programs.size());
		assertTrue(programs.keySet().contains("mmqyju"));
		assertTrue(programs.get("mmqyju").getChildNames().contains("rjzvwv"));
		assertTrue(programs.get("mmqyju").getChildNames().contains("noybkx"));
		assertEquals(2, programs.get("mmqyju").getChildNames().size());
	}
	
	/**
	 * In this example, tknk is at the bottom of the tower (the bottom program), and is holding up ugml, padx, and fwft.
	 * Those programs are, in turn, holding up other programs; in this example, none of those programs are holding up
	 * any other programs, and are all the tops of their own towers. (The actual tower balancing in front of you is much
	 * larger.)
	 */
	@Test
	public void testPart1Example() {
		assertEquals("tknk", Day07.getBottomName(Day07.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day07.getBottomName(Day07.getInput(0));
		System.out.println("Day 7 Part 1\n\t" + result);
		assertEquals("svugo", result);
	}

	/**
	 * In the example above, this means that for ugml's disc to be balanced, gyxo, ebii, and jptl must all have the same
	 * weight, and they do: 61.
	 * 
	 * However, for tknk to be balanced, each of the programs standing on its disc and all programs above it must each
	 * match. This means that the following sums must all be the same:
	 * 
	 * ugml + (gyxo + ebii + jptl) = 68 + (61 + 61 + 61) = 251
	 * padx + (pbga + havc + qoyq) = 45 + (66 + 66 + 66) = 243
	 * fwft + (ktlj + cntj + xhth) = 72 + (57 + 57 + 57) = 243
	 * 
	 * As you can see, tknk's disc is unbalanced: ugml's stack is heavier than the other two. Even though the nodes
	 * above ugml are balanced, ugml itself is too heavy: it needs to be 8 units lighter for its stack to weigh 243 and
	 * keep the towers balanced. If this change were made, its weight would be 60.
	 */
	@Test
	public void testPart2Example() {
		Map<String, Program> programs = Day07.getInput(1);
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
		int result = Day07.getWeightDiff(Day07.getInput(0));
		System.out.println("Day 7 Part 2\n\t" + result);
		assertEquals(1152, result);
	}
}
