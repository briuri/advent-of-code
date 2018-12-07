package buri.aoc.y18.d07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
		Steps steps = new Steps(input, 0);
		List<String> finishedSteps = new ArrayList<>();
		List<Step> nextSteps = steps.getStarts();
		
		while (!nextSteps.isEmpty()) {
			Step current = null;
			while (true) {
				current = nextSteps.remove(0);
				if (isAssignable(finishedSteps, current.getPrevious())) {
					break;
				}
				else {
					// If it's not assignable, return it to queue and keep checking.
					// Assumes there is no dependency deadlock.
					nextSteps.add(current);
				}
			}
			
			// Move the step to the finished list and add its next steps to the queue.
			finishedSteps.add(current.getName());
			addNextSteps(steps, nextSteps, current.getNext());
		}

		StringBuffer buffer = new StringBuffer();
		for (String stepName : finishedSteps) {
			buffer.append(stepName);
		}
		return (buffer.toString());
	}

	/**
	 * Part 2:
	 * With 5 workers and the 60+ second step durations described above, how long will it take to complete all of the
	 * steps?
	 */
	public static int getPart2Result(List<String> input, int workers, int baseTime) {
		Steps steps = new Steps(input, baseTime);
		List<Step> runningSteps = new ArrayList<>();
		List<String> finishedSteps = new ArrayList<>();
		List<Step> nextSteps = steps.getStarts();
		
		int time = 0;
		int availableWorkers = workers;
		while (true) {
			// Free up any workers whose steps have finished.
			for (Iterator<Step> iter = runningSteps.iterator(); iter.hasNext(); ) {
				Step step = iter.next();
				if (step.finishesAt(time)) {
					finishedSteps.add(step.getName());
					iter.remove();
					availableWorkers++;
					// Stop when last step has finished.
					if (runningSteps.size() + nextSteps.size() == 0) {
						return (time);
					}
				}
			}
			
			// Assign workers to queued steps.
			boolean stepsAvailable = true;
			while (availableWorkers > 0 && stepsAvailable) {
				Step current = null;
				for (Step step : nextSteps) {
					if (isAssignable(finishedSteps, step.getPrevious())) {
						current = step;
						break;
					}
				}
				if (current != null) {
					nextSteps.remove(current);
					availableWorkers--;
					current.setStartedAt(time);
					runningSteps.add(current);
					addNextSteps(steps, nextSteps, current.getNext());
				}
				else {
					stepsAvailable = false;
				}
			}
			time++;
		}
	}
		
	/**
	 * Checks if all prerequisites have been finished (allowing a step to be assigned).
	 */
	private static boolean isAssignable(List<String> finishedSteps, List<String> previousNames) {
		boolean finished = true;
		for (String name : previousNames) {
			finished = finished && finishedSteps.contains(name);
		}
		return (finished);
	}

	/**
	 * Adds a batch of next steps into the queue and sorts.
	 */
	private static void addNextSteps(Steps steps, List<Step> nextSteps, List<String> newNextSteps) {
		for (String stepName : newNextSteps) {
			Step next = steps.getStep(stepName);
			if (!nextSteps.contains(next)) {
				nextSteps.add(next);
			}
		}
		Collections.sort(nextSteps);
	}
}