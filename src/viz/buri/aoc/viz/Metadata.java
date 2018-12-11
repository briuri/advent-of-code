package buri.aoc.viz;

/**
 * Metadata for each 2018 puzzle.
 * 
 * @author Brian Uri!
 */
public class Metadata {

	private String _title;
	private int _globalCount;
	
	public Metadata(String title, int globalCount) {
		_title = title;
		_globalCount = globalCount;
	}

	/**
	 * Accessor for the title
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * Accessor for the number of spots on the global leaderboard
	 */
	public int getGlobalCount() {
		return _globalCount;
	}
}
