package buri.aoc.data.path;

import java.util.List;

import buri.aoc.data.tuple.BaseTuple;

/**
 * Interface for a pathfinding strategy that identifies potential steps to move to next.
 * 
 * @author Brian Uri!
 */
public interface StepStrategy<T extends BaseTuple> {

	/**
	 * Returns a list of potential next steps from a current position.
	 */
	public List<T> getNextSteps(T current);
}
