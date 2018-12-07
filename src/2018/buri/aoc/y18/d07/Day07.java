package buri.aoc.y18.d07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day07 extends Puzzle {

	/**
	 * Input: List of prerequisites.
	 * Output: Prereqs as strings.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2018/07", fileIndex));
	}
	
	/**
	 * Part 1:
	 * In what order should the steps in your instructions be completed?
	 */
	public static String getPart1Result(List<String> input) {
		Map<String, Step> steps = buildGraph(input, 0);

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
		}

		StringBuffer buffer = new StringBuffer();
		for (Step step : completedSteps) {
			buffer.append(step.getName());
		}
		return (buffer.toString());
	}

	/**
	 * Part 2:
	 * With 5 workers and the 60+ second step durations described above, how long will it take to complete all of the
	 * steps?
	 */
	public static int getPart2Result(List<String> input, int workers, int baseTime) {
		Map<String, Step> steps = buildGraph(input, baseTime);

		List<Step> runningSteps = new ArrayList<>();
		List<Step> completedSteps = new ArrayList<>();
		List<Step> nextSteps = getStarts(steps.values());
		int time = 0;
		int availableWorkers = workers;
		while (!nextSteps.isEmpty()) {
			// Free up any workers whose steps have finished.
			for (Iterator<Step> iter = runningSteps.iterator(); iter.hasNext(); ) {
				Step step = iter.next();
				if (step.finishesNow(time)) {
					completedSteps.add(step);
					iter.remove();
					availableWorkers++;
				}
			}
			
			// Assign workers to queued steps.
			boolean stepsAvailable = true;
			while (availableWorkers > 0 && stepsAvailable) {
				Step current = null;
				for (Step step : nextSteps) {
					if (step.getPrevious().isEmpty() || allCompleted(steps, completedSteps, step.getPrevious())) {
						current = step;
						break;
					}
				}
				if (current != null) {
					nextSteps.remove(current);
					runningSteps.add(current);
					current.setStartedAt(time);
					availableWorkers--;
					for (String stepName : current.getNext()) {
						Step next = steps.get(stepName);
						if (!nextSteps.contains(next)) {
							nextSteps.add(next);
						}
					}
					Collections.sort(nextSteps);
				}
				else {
					stepsAvailable = false;
				}
			}
			time++;
		}
		// Loop exits when only one step is left running (no more next steps). Add that duration.
		Step lastStep = runningSteps.get(0);
		while (!lastStep.finishesNow(time)) {
			time++;			
		}
		return (time);
	}
	
	/**
	 * Creates the steps.
	 */
	private static Map<String, Step> buildGraph(List<String> input, int baseTime) {
		Map<String, Step> steps = new HashMap<>();
		for (String data : input) {
			String[] tokens = data.split(" ");
			String prev = tokens[1];
			String next = tokens[7];
			
			Step step = steps.get(next);
			if (step == null) {
				step = new Step(next, baseTime);
				steps.put(next, step);
			}
			step.addPrevious(prev);
			
			step = steps.get(prev);
			if (step == null) {
				step = new Step(prev, baseTime);
				steps.put(prev, step);
			}
			step.addNext(next);
		}
		return (steps);
	}
	
	/**
	 * Finds the steps with no prerequisites.
	 */
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
	
	/**
	 * Checks if all prerequisites have been completed.
	 */
	private static boolean allCompleted(Map<String, Step> steps, List<Step> completedSteps, List<String> previousNames) {
		boolean completed = true;
		for (String name : previousNames) {
			completed = completed && completedSteps.contains(steps.get(name));
		}
		return (completed);
	}
}