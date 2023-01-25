package buri.aoc.y16.d10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A single balance bot.
 * 
 * @author Brian Uri!
 */
public class Bot {
	private List<Integer> _values;
	private String _valueSnapshot;
	private Integer _lowTarget;
	private Integer _highTarget;

	/**
	 * Constructor
	 */
	public Bot() {
		_values = new ArrayList<>();
	}

	/**
	 * Adds a value to this bot.
	 */
	public void addValue(Integer value) {
		if (isFull()) {
			throw new RuntimeException("This bot is full.");
		}
		getValues().add(value);
		Collections.sort(getValues());
		if (isFull()) {
			setValueSnapshot(getValues().toString());
		}
	}

	/**
	 * Returns true if this bot holds 2 values.
	 */
	public boolean isFull() {
		return (getValues().size() == 2);
	}

	/**
	 * Removes and returns the high value.
	 */
	public Integer getHighValue() {
		return (getValues().remove(getValues().size() - 1));
	}

	/**
	 * Removes and returns the low value.
	 */
	public Integer getLowValue() {
		return (getValues().remove(0));
	}

	/**
	 * Accessor for a snapshot of the values this bot processes.
	 */
	public String getValueSnapshot() {
		return _valueSnapshot;
	}

	/**
	 * Accessor for a snapshot of the values this bot processes.
	 */
	public void setValueSnapshot(String valueSnapshot) {
		_valueSnapshot = valueSnapshot;
	}

	/**
	 * Accessor for the values
	 */
	private List<Integer> getValues() {
		return _values;
	}

	/**
	 * Accessor for the index of the bot to send the low value to. Negative values denote an output bin.
	 */
	public Integer getLowTarget() {
		return _lowTarget;
	}

	/**
	 * Accessor for the index of the bot to send the low value to. Negative values denote an output bin.
	 */
	public void setLowTarget(Integer lowTarget) {
		_lowTarget = lowTarget;
	}

	/**
	 * Accessor for the index of the bot to send the high value to. Negative values denote an output bin.
	 */
	public Integer getHighTarget() {
		return _highTarget;
	}

	/**
	 * Accessor for the index of the bot to send the high value to. Negative values denote an output bin.
	 */
	public void setHighTarget(Integer highTarget) {
		_highTarget = highTarget;
	}
}
