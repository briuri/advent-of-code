package buri.aoc.y18.d07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The entire graph of steps
 *
 * @author Brian Uri!
 */
public class Steps {
	private final Map<String, Step> _steps;

	/**
	 * Builds a graph from input strings
	 */
	public Steps(List<String> input, int baseTime) {
		_steps = new HashMap<>();
		for (String data : input) {
			String[] tokens = data.split(" ");
			String prev = tokens[1];
			String next = tokens[7];

			// Load this step as a pre-req for its next steps.
			Step step = getStep(next);
			if (step == null) {
				step = new Step(next, baseTime);
				_steps.put(next, step);
			}
			step.addPrevious(prev);

			// Load this step as a next step for its pre-reqs.
			step = getStep(prev);
			if (step == null) {
				step = new Step(prev, baseTime);
				_steps.put(prev, step);
			}
			step.addNext(next);
		}
	}

	/**
	 * Returns the steps with no prerequisites, sorted by name.
	 */
	public List<Step> getStarts() {
		List<Step> starts = new ArrayList<>();
		for (Step step : getSteps().values()) {
			if (step.getPrevious().isEmpty()) {
				starts.add(step);
			}
		}
		Collections.sort(starts);
		return (starts);
	}

	/**
	 * Returns a step by name.
	 */
	public Step getStep(String name) {
		return (getSteps().get(name));
	}

	/**
	 * Accessor for the steps
	 */
	private Map<String, Step> getSteps() {
		return _steps;
	}
}