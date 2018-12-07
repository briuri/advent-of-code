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

	private String _targetRegister;
	private Integer _targetIncrement;
	private String _conditionRegister;
	private Conditional _conditional;
	private Integer _conditionValue;

	/**
	 * Constructor: Parses from single string
	 */
	public RegisterInstruction(String instruction) {
		String[] tokens = instruction.split(" ");
		assertValidInstruction(tokens);
		_targetRegister = tokens[0];
		_targetIncrement = Integer.valueOf(tokens[2]);
		if (tokens[1].equals("dec")) {
			_targetIncrement = _targetIncrement * -1;
		}
		_conditionRegister = tokens[4];
		Conditional operator = null;
		if (tokens[5].equals("<")) {
			operator = Conditional.LESS;
		}
		else if (tokens[5].equals("<=")) {
			operator = Conditional.LESS_OR_EQUAL;
		}
		else if (tokens[5].equals("==")) {
			operator = Conditional.EQUAL;
		}
		else if (tokens[5].equals("!=")) {
			operator = Conditional.NOT_EQUAL;
		}
		else if (tokens[5].equals(">=")) {
			operator = Conditional.GREATER_OR_EQUAL;
		}
		else if (tokens[5].equals(">")) {
			operator = Conditional.GREATER;
		}
		_conditional = operator;
		_conditionValue = Integer.valueOf(tokens[6]);
	}

	/**
	 * Validates that the instruction is reasonably well-formed.
	 */
	private static void assertValidInstruction(String[] instruction) {
		// a inc 1 if b < 5
		boolean hasCorrectLength = (instruction.length == 7);
		boolean hasValidIncrement = (instruction[1].equals("inc") || instruction[1].equals("dec"));
		boolean hasCondition = (instruction[3].equals("if"));
		boolean hasValidConditional = (instruction[5].equals("<") || instruction[5].equals("<=")
			|| instruction[5].equals("==") || instruction[5].equals("!=") || instruction[5].equals(">=")
			|| instruction[5].equals(">"));
		if (!(hasCorrectLength && hasValidIncrement && hasCondition && hasValidConditional)) {
			throw new IllegalArgumentException("Malformed instruction.");
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getTargetRegister()).append(" ").append(getTargetIncrement());
		buffer.append(" if ");
		buffer.append(getConditionRegister()).append(" ");
		buffer.append(getConditional()).append(" ").append(getConditionValue());
		return (buffer.toString());
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
	public Integer getTargetIncrement() {
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