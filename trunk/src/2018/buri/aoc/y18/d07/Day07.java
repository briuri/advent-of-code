package buri.aoc.y18.d07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day07 extends Puzzle {

	/**
	 * Input: 
	 * Output: 
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2018/07", fileIndex));
	}
	
	/**
	 * 
	 */
	public static String getResult(Part part, List<String> input) {
		Map<String, Step> steps = new HashMap<>();
		for (String data : input) {
			String[] tokens = data.split(" ");
			String prev = tokens[1];
			String next = tokens[7];
			
			Step step = steps.get(next);
			if (step == null) {
				step = new Step(next);
				steps.put(next, step);
			}
			step.addPrevious(prev);
			
			step = steps.get(prev);
			if (step == null) {
				step = new Step(prev);
				steps.put(prev, step);
			}
			step.addNext(next);
		}
		for (Step step : steps.values()) {
			System.out.println(step);
		}
		
		List<Step> completedSteps = new ArrayList<>();
		List<Step> nextSteps = getStarts(steps.values());
		while (!nextSteps.isEmpty()) {
			Step current = null;
			while (true) {
				current = nextSteps.remove(0);
				if (current.getPrevious().isEmpty() || allCompleted(steps, completedSteps, current.getPrevious())) {
					break;
				}
				else {
					nextSteps.add(current);
				}
			}
			completedSteps.add(current);
			for (String stepName : current.getNext()) {
				Step next = steps.get(stepName);
				if (!nextSteps.contains(next)) {
					nextSteps.add(next);
				}
			}
			Collections.sort(nextSteps);
			System.out.println("at " + current.getName() + " with choices " + nextSteps);
		}

		if (part == Part.ONE) {
			StringBuffer buffer = new StringBuffer();
			for (Step step : completedSteps) {
				buffer.append(step.getName());
			}
			System.out.println(buffer.toString());
			return (buffer.toString());
		}
		return ("");
	}

	private static List<Step> getStarts(Collection<Step> steps) {
		List<Step> starts = new ArrayList<>();
		for (Step step : steps) {
			if (step.getPrevious().isEmpty()) {
				starts.add(step);
			}
		}
		Collections.sort(starts);
		return (starts);
	}
	
	private static boolean allCompleted(Map<String, Step> steps, List<Step> completedSteps, List<String> previousNames) {
		boolean completed = true;
		for (String name : previousNames) {
			completed = completed && completedSteps.contains(steps.get(name));
		}
		return (completed);
	}
}