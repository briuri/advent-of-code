package buri.aoc.y17.d08;

/**
 * The instructions look like this:
 *
 * b inc 5 if a > 1
 * a inc 1 if b < 5
 * c dec -10 if a >= 1
 * c inc -20 if c == 10
 *
 * You might also encounter <= (less than or equal to) or != (not equal to). However, the CPU doesn't have the bandwidth
 * to tell you what all the registers are named, and leaves that to you to determine.
 *
 * @author Brian Uri!
 */
public class RegisterInstruction {

	public enum Conditional {
		LESS, LESS_OR_EQUAL, EQUAL, NOT_EQUAL, GREATER_OR_EQUAL, GREATER
	}

	private final String _targetRegister;
	private Long _targetIncrement;
	private final String _conditionRegister;
	private final Conditional _conditional;
	private final Integer _conditionValue;

	/**
	 * Constructor: Parses from single string
	 */
	public RegisterInstruction(String instruction) {
		String[] tokens = instruction.split(" ");
		_targetRegister = tokens[0];
		_targetIncrement = Long.valueOf(tokens[2]);
		if (tokens[1].equals("dec")) {
			_targetIncrement = _targetIncrement * -1;
		}
		_conditionRegister = tokens[4];
		Conditional operator = null;
		switch (tokens[5]) {
			case "<":
				operator = Conditional.LESS;
				break;
			case "<=":
				operator = Conditional.LESS_OR_EQUAL;
				break;
			case "==":
				operator = Conditional.EQUAL;
				break;
			case "!=":
				operator = Conditional.NOT_EQUAL;
				break;
			case ">=":
				operator = Conditional.GREATER_OR_EQUAL;
				break;
			case ">":
				operator = Conditional.GREATER;
				break;
		}
		_conditional = operator;
		_conditionValue = Integer.valueOf(tokens[6]);
	}

	@Override
	public String toString() {
		return (getTargetRegister() + " " + getTargetIncrement() +
				" if " + getConditionRegister() + " " + getConditional() + " " + getConditionValue());
	}

	/**
	 * Accessor for the target register name
	 */
	public String getTargetRegister() {
		return _targetRegister;
	}

	/**
	 * Accessor for the target increment value
	 */
	public Long getTargetIncrement() {
		return _targetIncrement;
	}

	/**
	 * Accessor for the condition register name
	 */
	public String getConditionRegister() {
		return _conditionRegister;
	}

	/**
	 * Accessor for the conditional operator
	 */
	public Conditional getConditional() {
		return _conditional;
	}

	/**
	 * Accessor for the condition value
	 */
	public Integer getConditionValue() {
		return _conditionValue;
	}
}