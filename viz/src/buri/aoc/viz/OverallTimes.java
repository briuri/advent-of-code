package buri.aoc.viz;

import java.util.List;

import buri.aoc.TimeType;

/**
 * Data class for all of a player's times.
 *
 * In 2016, the total time was used as a tiebreaker.
 * In 2017 and beyond, the median time is a tiebreaker.
 *
 * @author Brian Uri!
 */
public class OverallTimes implements Comparable<OverallTimes> {
	private final String _name;
	private final Integer _stars;
	private final long _tiebreakerTime;
	private final List<Long> _times;

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
		for (List<PuzzleTime> places : puzzleTimes.getTimes(TimeType.TOTAL)) {
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
		return (times.stream().mapToLong(Long::longValue).sum());
	}

	/**
	 * Calculates the median of the given times.
	 */
	private static long calculateMedianTime(List<Long> times) {
		// Odd number of times, so median is middle time.
		if (times.size() % 2 == 1) {
			return (times.get(times.size() / 2));
		}

		// Otherwise, take the average of 2 middle times.
		long low = times.get(times.size() / 2 - 1);
		long high = times.get(times.size() / 2);
		long median = (high + low) / 2;

		// Round up 0.5 seconds in average.
		if ((high + low) % 2 != 0) {
			median++;
		}
		return (median);
	}

	/**
	 * Sort on number of stars, then tiebreaker time.
	 */
	@Override
	public int compareTo(OverallTimes o) {
		// Most stars is the main rank.
		int compare = -1 * getStars().compareTo(o.getStars());
		// Median/Total time is the tiebreaker.
		if (compare == 0) {
			compare = getTiebreakerTime().compareTo(o.getTiebreakerTime());
		}
		// For ties, alphabetize on last name.
		if (compare == 0) {
			String last1 = (getName().indexOf(" ") != -1) ? getName().split(" ")[1] : getName();
			String last2 = (o.getName().indexOf(" ") != -1) ? o.getName().split(" ")[1] : o.getName();
			compare = last1.compareTo(last2);
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
	public Integer getStars() {
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