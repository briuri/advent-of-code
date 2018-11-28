package buri.aoc.y2017;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import buri.aoc.model.Part;
import buri.aoc.model.RegisterInstruction;
import buri.aoc.y2017.util.FileUtil;

/**
 * @author Brian Uri!
 */
public class Day08Test {

	/**
	 * Example Data
	 * 
	 * b inc 5 if a > 1
	 * a inc 1 if b < 5
	 * c dec -10 if a >= 1
	 * c inc -20 if c == 10
	 */
	private List<RegisterInstruction> getExampleInput() {
		List<RegisterInstruction> instructions = new ArrayList<>();
		instructions.add(new RegisterInstruction("b inc 5 if a > 1"));
		instructions.add(new RegisterInstruction("a inc 1 if b < 5"));
		instructions.add(new RegisterInstruction("c dec -10 if a >= 1"));
		instructions.add(new RegisterInstruction("c inc -20 if c == 10"));
		return (instructions);
	}

	/**
	 * These instructions would be processed as follows:
	 * 
	 * Because a starts at 0, it is not greater than 1, and so b is not modified.
	 * a is increased by 1 (to 1) because b is less than 5 (it is 0).
	 * c is decreased by -10 (to 10) because a is now greater than or equal to 1 (it is 1).
	 * c is increased by -20 (to -10) because c is equal to 10.
	 * After this process, the largest value in any register is 1.
	 */
	@Test
	public void testPart1Example() {
		assertEquals(1, Day08.getLargestValue(Part.ONE, getExampleInput()));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day08.getLargestValue(Part.ONE, FileUtil.getDay08());
		System.out.println("Day 8 Part 1\n\t" + result);
		assertEquals(4888, result);
	}

	/**
	 * For example, in the above instructions, the highest value ever held was 10 (in register c after the third
	 * instruction was evaluated).
	 */
	@Test
	public void testPart2Example() {
		assertEquals(10, Day08.getLargestValue(Part.TWO, getExampleInput()));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day08.getLargestValue(Part.TWO, FileUtil.getDay08());
		System.out.println("Day 8 Part 2\n\t" + result);
		assertEquals(7774, result);
	}
	
}
