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
	private List<Chemical> _inputs;
	private Chemical _output;

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
		StringBuffer buffer = new StringBuffer();
		for (Iterator<Chemical> iterator = getInputs().iterator(); iterator.hasNext();) {
			Chemical chemical = (Chemical) iterator.next();
			buffer.append(chemical);
			if (iterator.hasNext()) {
				buffer.append(" + ");
			}
		}
		buffer.append(" = ");
		buffer.append(getOutput());
		return (buffer.toString());
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
