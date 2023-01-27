package buri.aoc.y19.d14;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Data model for a chemical reaction.
 *
 * @author Brian Uri!
 */
public class Reaction {
	private final List<Chemical> _inputs;
	private final Chemical _output;

	/**
	 * Constructor
	 *
	 * 7 A, 1 E => 1 FUEL
	 */
	public Reaction(String reaction) {
		String[] tokens = reaction.split(" => ");
		_inputs = new ArrayList<>();
		for (String input : tokens[0].split(", ")) {
			_inputs.add(new Chemical(input));
		}
		_output = new Chemical(tokens[1]);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Iterator<Chemical> iterator = getInputs().iterator(); iterator.hasNext();) {
			Chemical chemical = iterator.next();
			builder.append(chemical);
			if (iterator.hasNext()) {
				builder.append(" + ");
			}
		}
		builder.append(" = ");
		builder.append(getOutput());
		return (builder.toString());
	}

	/**
	 * Accessor for the output
	 */
	public Chemical getOutput() {
		return _output;
	}

	/**
	 * Accessor for the inputs
	 */
	public List<Chemical> getInputs() {
		return _inputs;
	}
}