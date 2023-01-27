package buri.aoc.y15.d23;

import buri.aoc.common.Part;
import buri.aoc.common.data.registers.NamedRegisters;

import java.util.List;

/**
 * @author Brian Uri!
 */
public class Registers extends NamedRegisters {

	/**
	 * Constructor
	 */
	public Registers(Part part, List<String> instructions) {
		super(instructions);
		set("a", part == Part.ONE ? 0L : 1L);
		set("b", 0L);
	}

	/**
	 * Processes all instructions.
	 */
	public void process() {
		while (isWithinInstructions()) {
			String[] tokens = getInstructions().get(getCurrent()).split(" ");
			if (tokens[0].equals("hlf")) {
				long value = getRegisters().get(tokens[1]);
				getRegisters().put(tokens[1], value / 2);
				setCurrent(getCurrent() + 1);
			}
			if (tokens[0].equals("tpl")) {
				long value = getRegisters().get(tokens[1]);
				getRegisters().put(tokens[1], value * 3);
				setCurrent(getCurrent() + 1);
			}
			if (tokens[0].equals("inc")) {
				long value = getRegisters().get(tokens[1]);
				getRegisters().put(tokens[1], value + 1);
				setCurrent(getCurrent() + 1);
			}
			if (tokens[0].equals("jmp")) {
				int jump = Integer.parseInt(tokens[1]);
				setCurrent(getCurrent() + jump);
			}
			if (tokens[0].equals("jie")) {
				long condition = getRegisterOrValue(tokens[1]);
				int jump = (condition % 2 == 0 ? Integer.parseInt(tokens[2]) : 1);
				setCurrent(getCurrent() + jump);
			}
			if (tokens[0].equals("jio")) {
				long condition = getRegisterOrValue(tokens[1]);
				int jump = (condition == 1 ? Integer.parseInt(tokens[2]) : 1);
				setCurrent(getCurrent() + jump);
			}
		}
	}
}