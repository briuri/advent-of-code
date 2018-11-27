package buri.aoc.y2017;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import buri.aoc.model.Program;

/**
 * One program at the bottom supports the entire tower. It's holding a large disc, and on the disc are balanced several
 * more sub-towers. At the bottom of these sub-towers, standing on the bottom disc, are other programs, each holding
 * their own disc, and so on. At the very tops of these sub-sub-sub-...-towers, many programs stand simply keeping the
 * disc below them balanced but with no disc of their own.
 * 
 * You offer to help, but first you need to understand the structure of these towers. You ask each program to yell out
 * their name, their weight, and (if they're holding a disc) the names of the programs immediately above them balancing
 * on that disc. You write this information down (your puzzle input). Unfortunately, in their panic, they don't do this
 * in an orderly fashion; by the time you're done, you're not sure which program gave which information.
 * 
 * @author Brian Uri!
 */
public class Day07 {

	/**
	 * Find out name of bottom program (the one that does not appear in any child lists)
	 */
	public static String getBottomName(Map<String, Program> programs) {
		Set<String> uniqueChildren = new HashSet<>();
		for (Program program : programs.values()) {
			for (String child : program.getChildNames()) {
				uniqueChildren.add(child);
			}
		}
		for (String parent : programs.keySet()) {
			if (!uniqueChildren.contains(parent)) {
				return (parent);
			}
		}
		throw new RuntimeException("Could not find bottom program.");
	}

	/**
	 * Find the weight differential.
	 * 
	 * TODO: This algorithm works for the input data but makes some assumptions that don't cover edge cases. I assume
	 * that we always have 2 siblings to compare total weights to as we explore the tower. If the path from the bottom
	 * program to the incorrect node involved a program with 1-2 children, it would not be trivially easy to identify
	 * the imbalanced child.
	 */
	public static int getWeightDiff(Map<String, Program> programs) {
		String bottomName = getBottomName(programs);
		Program imbalancedProgram = programs.get(bottomName);
		List<Program> siblings = null;

		// Find the program along the imbalanced path whose children are all correct.
		Program next = imbalancedProgram;
		while (next != null) {
			siblings = imbalancedProgram.getChildren();
			imbalancedProgram = next;
			next = imbalancedProgram.getImbalancedChild();
		}

		// Compare this program's weight to its siblings to determine diff.
		int expectedTotalWeight = 0;
		for (Program sibling : siblings) {
			if (!sibling.getName().equals(imbalancedProgram.getName())) {
				expectedTotalWeight = sibling.getTotalWeight();
			}
		}

		// Calculate how much extra weight is needed to correct the imbalanced program.
		return (imbalancedProgram.getWeight() + (expectedTotalWeight - imbalancedProgram.getTotalWeight()));
	}
}
