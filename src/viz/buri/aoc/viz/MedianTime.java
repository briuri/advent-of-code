package buri.aoc.viz;

import java.util.List;

/**
 * Data class for a player's median time.
 * 
 * @author Brian Uri!
 */
public class MedianTime implements Comparable {
	private String _name;
	private int _stars;
	private String _medianTime;
	private List<String> _times;

	private int _first = 0;
	private int _second = 0;
	private int _third = 0;

	/**
	 * Constructor
	 */
	public MedianTime(List<List<PuzzleTime>> puzzleTimes, String name, int stars, List<String> times) {
		_name = name;
		_stars = stars;
		_medianTime = calculateMedianTime(times);
		_times = times;
		for (List<PuzzleTime> places : puzzleTimes) {
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
	 * Calculates the median of the given times.
	 */
	private static String calculateMedianTime(List<String> times) {
		if (times.size() % 2 == 1) {
			return (times.get(times.size() / 2));
		}
		int low = toSeconds(times.get(times.size() / 2 - 1));
		int high = toSeconds(times.get(times.size() / 2));
		int median = (high + low) / 2;
		// Round up 0.5 seconds.
		if ((high + low) % 2 != 0) {
			median++;
		}

		String hours = String.valueOf(median / (60 * 60));
		if (hours.length() == 1) {
			hours = "0" + hours;
		}

		median = median % (60 * 60);
		String minutes = String.valueOf(median / 60);
		if (minutes.length() == 1) {
			minutes = "0" + minutes;
		}
		median = median % 60;
		String seconds = String.valueOf(median);
		if (seconds.length() == 1) {
			seconds = "0" + seconds;
		}
		return (hours + ":" + minutes + ":" + seconds);
	}
	
	/**
	 * Converts a time in the format HH:MM:SS to seconds.
	 */
	private static int toSeconds(String time) {
		int seconds = Integer.valueOf(time.substring(time.lastIndexOf(":") + 1));
		seconds += 60 * Integer.valueOf(time.substring(time.indexOf(":") + 1, time.lastIndexOf(":")));
		seconds += 60 * 60 * Integer.valueOf(time.substring(0, time.indexOf(":")));
		return (seconds);
	}
	
	/**
	 * Sort on number of stars, then median time.
	 */
	@Override
	public int compareTo(Object obj) {
		MedianTime time = (MedianTime) obj;
		if (getStars() > time.getStars()) {
			return (-1);
		}
		if (getStars() < time.getStars()) {
			return (1);
		}
		return (getMedianTime().compareTo(time.getMedianTime()));
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
	 * Accessor for the median time
	 */
	public String getMedianTime() {
		return _medianTime;
	}

	/**
	 * Accessor for the times
	 */
	public List<String> getTimes() {
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