package buri.aoc.y15.d22;

/**
 * @author Brian Uri!
 */
public class Effect {

	public enum Type {
		SHIELD, POISON, RECHARGE
	}

	private Type _type;
	private int _ticksLeft;
	private int _maxTicks;

	/**
	 * Constructor
	 */
	public Effect(Type type) {
		_type = type;
		_maxTicks = (type == Type.RECHARGE ? 5 : 6);
		_ticksLeft = getMaxTicks();
	}

	/**
	 * Returns true if the effect has never ticked.
	 */
	public boolean isNew() {
		return (getMaxTicks() == getTicksLeft());
	}

	/**
	 * Returns true if the effect will expire on the next tick.
	 */
	public boolean isExpiring() {
		return (getTicksLeft() == 1);
	}

	/**
	 * Decrements the effect counter. Returns true if the effect has ended.
	 */
	public boolean tick() {
		_ticksLeft = getTicksLeft() - 1;
		return (getTicksLeft() <= 0);
	}

	/**
	 * Accessor for the type
	 */
	public Type getType() {
		return _type;
	}

	/**
	 * Accessor for the ticksLeft
	 */
	private int getTicksLeft() {
		return _ticksLeft;
	}

	/**
	 * Accessor for the maxTicks
	 */
	private int getMaxTicks() {
		return _maxTicks;
	}
}
