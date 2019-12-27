package buri.aoc.y17.d18;

import java.util.List;

/**
 * Implementation of Registers for Part 1.
 * 
 * @author Brian Uri!
 */
public class Part1Registers extends AbstractRegisters {

	private Long _lastFrequency;

	/**
	 * Constructor
	 */
	public Part1Registers(List<String> instructions) {
		super(instructions);
	}

	/**
	 * snd X plays a sound with a frequency equal to the value of X.
	 */
	@Override
	protected void snd(String[] tokens) {
		setLastFrequency(getRegisterOrValue(tokens[1]));
	}

	/**
	 * rcv X recovers the frequency of the last sound played, but only when the value of X is not zero. (If it is zero,
	 * the command does nothing.)
	 */
	@Override
	protected boolean rcv(String[] tokens) {
		long value = getRegisterOrValue(tokens[1]);
		// Quit processing the first time we hit a rcv that recovers.
		return (value <= 0);
	}

	/**
	 * Accessor for the last frequency played
	 */
	public Long getLastFrequency() {
		return _lastFrequency;
	}

	/**
	 * Accessor for the last frequency played
	 */
	private void setLastFrequency(Long lastFrequency) {
		_lastFrequency = lastFrequency;
	}
}