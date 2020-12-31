package buri.aoc.viz;

import java.util.List;

/**
 * Data class for all of a player's times.
 *
 * In 2016, the total time was used as a tie breaker.
 * In 2017 and beyond, the median time is a tie breaker.
 *
 * @author Brian Uri!
 */
public class OverallTimes implements Comparable {
	private String _name;
	private int _stars;
	private long _tiebreakerTime;
	private List<Long> _times;

	private int _first = 0;
	private int _second = 0;
	private int _third = 0;

	/**
	 * Constructor
	 */
	public OverallTimes(PuzzleTimes puzzleTimes, String name, List<Long> times, boolean useMedian) {
		_name = name;
		_stars = puzzleTimes.getStars(name);
		_tiebreakerTime = (useMedian ? calculateMedianTime(times) : calculateTotalTime(times));
		_times = times;
		for (List<PuzzleTime> places : puzzleTimes.getPart2Times()) {
			if (places.size() >= 1 && places.get(0).getName().equals(name)) {
				_first += 1;
			}
			if (places.size() >= 2 && places.get(1).getName().equals(name)) {
				_second += 1;
			}
			if (places.size() >= 3 && places.get(2).getName().equals(name)) {
				_third += 1;
			}
		}
	}

	/**
	 * Calculates the sum of the given times.
	 */
	private static long calculateTotalTime(List<Long> times) {
		long total = 0;
		for (Long time : times) {
			total += time;
		}
		return (total);
	}

	/**
	 * Calculates the median of the given times.
	 */
	private static long calculateMedianTime(List<Long> times) {
		if (times.size() % 2 == 1) {
			return (times.get(times.size() / 2));
		}
		long low = times.get(times.size() / 2 - 1);
		long high = times.get(times.size() / 2);
		long median = (high + low) / 2;
		// Round up 0.5 seconds.
		if ((high + low) % 2 != 0) {
			median++;
		}
		return (median);
	}

	/**
	 * Sort on number of stars, then tiebreaker time.
	 */
	@Override
	public int compareTo(Object obj) {
		OverallTimes time = (OverallTimes) obj;
		if (getStars() > time.getStars()) {
			return (-1);
		}
		if (getStars() < time.getStars()) {
			return (1);
		}
		int compare = getTiebreakerTime().compareTo(time.getTiebreakerTime());
		// Exact timestamp ties
		if (compare == 0) {
			compare = getName().split(" ")[1].compareTo(time.getName().split(" ")[1]);
		}
		return (compare);
	}

	/**
	 * Returns true if at least 1 medal is found.
	 */
	public boolean hasMedals() {
		return (getFirst() + getSecond() + getThird() > 0);
	}

	/**
	 * Acessor for the name of the player
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the stars
	 */
	public int getStars() {
		return _stars;
	}

	/**
	 * Accessor for the time used in tiebreaker situations
	 */
	public Long getTiebreakerTime() {
		return _tiebreakerTime;
	}

	/**
	 * Accessor for the times
	 */
	public List<Long> getTimes() {
		return _times;
	}

	/**
	 * Accessor for the number of first place finishes
	 */
	public int getFirst() {
		return _first;
	}

	/**
	 * Accessor for the number of second place finishes
	 */
	public int getSecond() {
		return _second;
	}

	/**
	 * Accessor for the number of third place finishes
	 */
	public int getThird() {
		return _third;
	}
}