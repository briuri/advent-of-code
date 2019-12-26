package buri.aoc.y18.d07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;

/**
 * Day 7: The Sum of Its Parts
 * 
 * @author Brian Uri!
 */
public class Day07 extends BasePuzzle {

	/**
	 * Returns input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2018/07", fileIndex));
	}
	
	/**
	 * Part 1:
	 * In what order should the steps in your instructions be completed?
	 * 
	 * (Note: Part 1 is a simple case of Part 2's algorithm using workers = 1 and any baseTime. I discarded the original
	 * Part 1 algorithm and refactored to support this).
	 * 
	 * Part 2:
	 * With 5 workers and the 60+ second step durations described above, how long will it take to complete all of the
	 * steps?
	 */
	public static String getResult(Part part, List<String> input, int workers, int baseTime) {
		Steps steps = new Steps(input, baseTime);
		List<Step> nextSteps = steps.getStarts();
		List<Step> runningSteps = new ArrayList<>();
		List<String> finishedStepNames = new ArrayList<>();
		
		int time = -1;
		while (runningSteps.size() + nextSteps.size() != 0) {
			time++;
			// First, release any workers whose steps have finished.
			for (Iterator<Step> iter = runningSteps.iterator(); iter.hasNext(); ) {
				Step step = iter.next();
				if (step.finishesAt(time)) {
					iter.remove();
					finishedStepNames.add(step.getName());
					workers++;
				}
			}
			
			// Next, assign available workers to queued, assignable steps.
			boolean stepsAvailable = true;
			while (workers > 0 && stepsAvailable) {
				Step assignment = null;
				for (Step step : nextSteps) {
					if (isAssignable(finishedStepNames, step.getPrevious())) {
						assignment = step;
						break;
					}
				}
				// No steps currently available (pre-reqs are running).
				if (assignment == null) {
					stepsAvailable = false;
				}
				else {
					workers--;
					nextSteps.remove(assignment);
					assignment.setStartedAt(time);
					addNextSteps(steps, nextSteps, assignment.getNext());
					runningSteps.add(assignment);
				}
			}
		}
		// Generate the final path through the graph.
		StringBuffer buffer = new StringBuffer();
		for (String stepName : finishedStepNames) {
			buffer.append(stepName);
		}
		return (part == Part.ONE ? buffer.toString() : String.valueOf(time));
	}
		
	/**
	 * Checks if all prerequisites have been finished (allowing a step to be assigned).
	 */
	private static boolean isAssignable(List<String> finishedStepNames, List<String> previousStepNames) {
		boolean finished = true;
		for (String name : previousStepNames) {
			finished = finished && finishedStepNames.contains(name);
		}
		return (finished);
	}

	/**
	 * Adds a batch of next steps into the queue and sorts.
	 */
	private static void addNextSteps(Steps steps, List<Step> nextSteps, List<String> newNextStepNames) {
		for (String stepName : newNextStepNames) {
			Step next = steps.getStep(stepName);
			if (!nextSteps.contains(next)) {
				nextSteps.add(next);
			}
		}
		Collections.sort(nextSteps);
	}
}