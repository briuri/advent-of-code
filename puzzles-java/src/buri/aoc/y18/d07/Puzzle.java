package buri.aoc.y18.d07;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Day 7: The Sum of Its Parts
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("CABDFE", 1, false);
		assertRun("ABGKCMVWYDEHFOPQUILSTNZRJX", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("898", 0, true);
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
	protected String runString(Part part, List<String> input) {
		int workers = (part == Part.ONE ? 1 : 5);
		int baseTime = (part == Part.ONE ? 0 : 60);
		Steps steps = new Steps(input, baseTime);
		List<Step> nextSteps = steps.getStarts();
		List<Step> runningSteps = new ArrayList<>();
		List<String> finishedStepNames = new ArrayList<>();

		int time = -1;
		while (runningSteps.size() + nextSteps.size() != 0) {
			time++;
			// First, release any workers whose steps have finished.
			for (Iterator<Step> iter = runningSteps.iterator(); iter.hasNext();) {
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