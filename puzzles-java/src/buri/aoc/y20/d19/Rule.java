package buri.aoc.y20.d19;

/**
 * Data model for a message rule.
 *
 * @author Brian Uri!
 */
public class Rule {
	private int _id;
	private String _rule;

	/**
	 * Constructor
	 */
	public Rule(String line) {
		String[] tokens = line.split(": ");
		_id = Integer.valueOf(tokens[0]);
		if (tokens[1].indexOf("\"") != -1) {
			tokens[1] = tokens[1].substring(1, 2);
		}
		setRule(tokens[1]);
	}

	/**
	 * Returns true of this rule is just a or b.
	 */
	public boolean isSimple() {
		return (getRule().equals("a") || getRule().equals("b"));
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (getId() + ": " + getRule());
	}

	/**
	 * Accessor for the id
	 */
	public int getId() {
		return _id;
	}

	/**
	 * Accessor for the rule
	 */
	public String getRule() {
		return _rule;
	}

	/**
	 * Accessor for the rule
	 */
	public void setRule(String rule) {
		_rule = rule;
	}
}
