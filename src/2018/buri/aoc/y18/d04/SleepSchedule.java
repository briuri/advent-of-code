package buri.aoc.y18.d04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Data class for a mutable sleep schedule.
 * 
 * Each schedule is a list of 60 integers (index 0 - 59). In each minute slot, the value is the number of different days
 * the guard is asleep at that time.
 * 
 * @author Brian Uri!
 */
public class SleepSchedule {
	private List<Integer> _schedule;
	
	/**
	 * Constructor
	 */
	public SleepSchedule() {
		_schedule = new ArrayList<>();
		for (int minute = 0; minute < 60; minute++) {
			getSchedule().add(0);
		}
	}
	
	/**
	 * Adds 1 to the number of times the guard is asleep at this minute.
	 */
	public void markAsleep(int minute) {
		getSchedule().set(minute, getSchedule().get(minute) + 1);
	}

	/**
	 * Gets the total sum of all minutes slept by this guard.
	 */
	public Integer getTotalSleep() {
		int sum = 0;
		for (int minute = 0; minute < 60; minute++) {
			sum += getSchedule().get(minute);
		}
		return (sum);
	}
	
	/**
	 * Gets the minute with the most recorded sleep.
	 */
	public Integer getMaxMinute() {
		return (getSchedule().indexOf(getMaxSleep()));
	}
	
	/**
	 * Gets the value of the minute with the most recorded sleep.
	 */
	public Integer getMaxSleep() {
		return (Collections.max(getSchedule()));
	}
	
	/**
	 * Accessor for the raw schedule
	 */
	private List<Integer> getSchedule() {
		return _schedule;
	}	
}
