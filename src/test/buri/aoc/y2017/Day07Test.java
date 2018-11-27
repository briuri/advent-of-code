package buri.aoc.y2017;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import buri.aoc.model.Program;
import buri.aoc.y2017.util.FileUtil;

/**
 * @author Brian Uri!
 */
public class Day07Test {

	/**
	 * Example Data
	 * 
	 * pbga (66)
	 * xhth (57)
	 * ebii (61)
	 * havc (66)
	 * ktlj (57)
	 * fwft (72) -> ktlj, cntj, xhth
	 * qoyq (66)
	 * padx (45) -> pbga, havc, qoyq
	 * tknk (41) -> ugml, padx, fwft
	 * jptl (61)
	 * ugml (68) -> gyxo, ebii, jptl
	 * gyxo (61)
	 * cntj (57)
	 */
	private Map<String, Program> getExampleInput() {
		List<String> fwftChildren = new ArrayList<>();
		fwftChildren.add("ktlj");
		fwftChildren.add("cntj");
		fwftChildren.add("xhth");
		List<String> padxChildren = new ArrayList<>();
		padxChildren.add("pbga");
		padxChildren.add("havc");
		padxChildren.add("qoyq");
		List<String> tknkChildren = new ArrayList<>();
		tknkChildren.add("ugml");
		tknkChildren.add("padx");
		tknkChildren.add("fwft");
		List<String> ugmlChildren = new ArrayList<>();
		ugmlChildren.add("gyxo");
		ugmlChildren.add("ebii");
		ugmlChildren.add("jptl");

		Map<String, Program> programs = new HashMap<>();
		programs.put("pbga", new Program("pbga", 66, Collections.EMPTY_LIST));
		programs.put("xhth", new Program("xhth", 57, Collections.EMPTY_LIST));
		programs.put("ebii", new Program("ebii", 61, Collections.EMPTY_LIST));
		programs.put("havc", new Program("havc", 66, Collections.EMPTY_LIST));
		programs.put("ktlj", new Program("ktlj", 57, Collections.EMPTY_LIST));
		programs.put("fwft", new Program("fwft", 72, fwftChildren));
		programs.put("qoyq", new Program("qoyq", 66, Collections.EMPTY_LIST));
		programs.put("padx", new Program("padx", 45, padxChildren));
		programs.put("tknk", new Program("tknk", 41, tknkChildren));
		programs.put("jptl", new Program("jptl", 61, Collections.EMPTY_LIST));
		programs.put("ugml", new Program("ugml", 68, ugmlChildren));
		programs.put("gyxo", new Program("gyxo", 61, Collections.EMPTY_LIST));
		programs.put("cntj", new Program("cntj", 57, Collections.EMPTY_LIST));
		return (programs);
	}

	/**
	 * In this example, tknk is at the bottom of the tower (the bottom program), and is holding up ugml, padx, and fwft.
	 * Those programs are, in turn, holding up other programs; in this example, none of those programs are holding up
	 * any other programs, and are all the tops of their own towers. (The actual tower balancing in front of you is much
	 * larger.)
	 */
	@Test
	public void testPart1Example() {
		assertEquals("tknk", Day07.getBottomName(getExampleInput()));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day07.getBottomName(FileUtil.getDay07());
		System.out.println("Day 7 Part 1\n\t" + result);
		assertEquals("svugo", result);
	}
}
