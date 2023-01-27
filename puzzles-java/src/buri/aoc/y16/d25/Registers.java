package buri.aoc.y16.d25;

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
	public Registers(List<String> instructions, long start) {
		super(instructions);
		for (char name = 'a'; name <= 'd'; name++) {
			set(String.valueOf(name), 0L);
		}
		set("a", start);
	}

	/**
	 * Outputs the instruction data as pseudocode.
	 */
	public static void convertInput(List<String> input) {
		final String REGISTER = "[a-d]";
		for (int i = 0; i < input.size(); i++) {
			String[] code = input.get(i).split(" ");
			String opcode = code[0];

			switch (opcode) {
				case "cpy": {
					String registerOrValue = (code[1].matches(REGISTER) ? "reg[%s]" : "%s");
					System.out.printf("%d\treg[%s] = " + registerOrValue + "%n", i, code[2], code[1]);
					break;
				}
				case "out": {
					String registerOrValue = (code[1].matches(REGISTER) ? "reg[%s]" : "%s");
					System.out.printf("%d\ttransmit " + registerOrValue + "%n", i, code[1]);
					break;
				}
				case "inc":
					System.out.printf("%d\treg[%s] += 1%n", i, code[1]);
					break;
				case "dec":
					System.out.printf("%d\treg[%s] -= 1%n", i, code[1]);
					break;
				case "jnz":
					String registerOrValueCondition = (code[1].matches(REGISTER) ? "reg[%s]" : "%s");
					String registerOrValueJump = (code[2].matches(REGISTER) ? "reg[%s]" : "%s");
					System.out.printf("%d\tif (" + registerOrValueCondition + " != 0) then goto (%d + "
							+ registerOrValueJump + ")%n", i, code[1], i, code[2]);
					break;
			}
		}
	}

	/**
	 * Processes all instructions.
	 */
	public void process() {
		while (isWithinInstructions()) {
			String[] tokens = getInstructions().get(getCurrent()).split(" ");
			if (tokens[0].equals("cpy")) {
				if (!tokens[2].matches("[a-d]")) {
					continue;
				}
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
			if (tokens[0].equals("out")) {
				long value = getRegisterOrValue(tokens[1]);
				System.out.println(value);
			}
			if (tokens[0].equals("jnz")) {
				long condition = getRegisterOrValue(tokens[1]);
				int jump = getRegisterOrValue(tokens[2]).intValue();
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

	@Override
	public String toString() {
		return (String.format("[%d, %d, %d, %d]", get("a"), get("b"), get("c"), get("d")));
	}
}