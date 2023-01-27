package buri.aoc.y16.d12;

import buri.aoc.common.Part;
import buri.aoc.common.data.registers.NamedRegisters;

import java.util.List;

/**
 * Data class for the assembunny registers.
 *
 * @author Brian Uri!
 */
public class Registers extends NamedRegisters {

	/**
	 * Constructor
	 */
	public Registers(Part part, List<String> instructions) {
		super(instructions);
		for (char name = 'a'; name <= 'd'; name++) {
			set(String.valueOf(name), 0L);
		}
		if (part == Part.TWO) {
			set("c", 1L);
		}
	}

	/**
	 * Processes all instructions.
	 */
	public void process() {
		while (isWithinInstructions()) {
			String[] tokens = getInstructions().get(getCurrent()).split(" ");
			if (tokens[0].equals("cpy")) {
				long value = getRegisterOrValue(tokens[1]);
				getRegisters().put(tokens[2], value);
			}
			if (tokens[0].equals("inc")) {
				long originalValue = get(tokens[1]);
				getRegisters().put(tokens[1], originalValue + 1);
			}
			if (tokens[0].equals("dec")) {
				long originalValue = get(tokens[1]);
				getRegisters().put(tokens[1], originalValue - 1);
			}
			if (tokens[0].equals("jnz")) {
				long condition = getRegisterOrValue(tokens[1]);
				int jump = Integer.parseInt(tokens[2]);
				if (condition != 0) {
					setCurrent(getCurrent() + jump);
				}
				else {
					setCurrent(getCurrent() + 1);
				}
			}
			else {
				setCurrent(getCurrent() + 1);
			}
		}
	}
}