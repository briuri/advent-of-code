package buri.aoc.y16.d23;

import java.util.List;

import buri.aoc.data.registers.NamedRegisters;

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
			
			// New instruction to optimize Part 2
			if (opcode.equals("mul")) {
				System.out.println(String.format("%d\treg[%s] = reg[%s] * reg[%s]", i, code[3], code[1], code[2]));
			}
			else if (opcode.equals("cpy")) {
				String registerOrValue = (code[1].matches(REGISTER) ? "reg[%s]" : "%s");
				System.out.println(String.format("%d\treg[%s] = " + registerOrValue, i, code[2], code[1]));
			}
			else if (opcode.equals("inc")) {
				System.out.println(String.format("%d\treg[%s] += 1", i, code[1]));
			}
			else if (opcode.equals("dec")) {
				System.out.println(String.format("%d\treg[%s] -= 1", i, code[1]));
			}
			else if (opcode.equals("jnz")) {
				String registerOrValueCondition = (code[1].matches(REGISTER) ? "reg[%s]" : "%s");
				String registerOrValueJump = (code[2].matches(REGISTER) ? "reg[%s]" : "%s");
				System.out.println(String.format("%d\tif (" + registerOrValueCondition + " != 0) then goto (%d + "
					+ registerOrValueJump + ")", i, code[1], i, code[2]));
			}
			else if (opcode.equals("tgl")) {
				String registerOrValueToggle = (code[1].matches(REGISTER) ? "reg[%s]" : "%s");
				System.out.println(String.format("%d\ttoggle (%d + " + registerOrValueToggle + ")", i, i, code[1]));
			}
		}
	}

	/**
	 * Processes all instructions.
	 */
	public void process() {
		while (true) {
			if (!isWithinInstructions()) {
				break;
			}
			String[] tokens = getInstructions().get(getCurrent()).split(" ");
			// New instruction to optimize Part 2
			if (tokens[0].equals("mul")) {
				long value1 = getRegisterOrValue(tokens[1]);
				long value2 = getRegisterOrValue(tokens[2]);
				getRegisters().put(tokens[3], value1 * value2);
			}
			if (tokens[0].equals("tgl")) {
				toggle(getRegisterOrValue(tokens[1]).intValue());
			}
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

	/**
	 * Toggles an instruction for future processing.
	 * 
	 * For one-argument instructions, inc becomes dec, and all other one-argument instructions become inc.
	 * For two-argument instructions, jnz becomes cpy, and all other two-instructions become jnz.
	 * 
	 * If an attempt is made to toggle an instruction outside the program, nothing happens.
	 * If toggling produces an invalid instruction (like cpy 1 2) and an attempt is later made to execute that
	 * instruction, skip it instead.
	 * If tgl toggles itself (for example, if a is 0, tgl a would target itself and become inc a), the resulting
	 * instruction is not executed until the next time it is reached.
	 * 
	 */
	private void toggle(int jump) {
		int instruction = getCurrent() + jump;
		if (instruction >= 0 && instruction < getInstructions().size()) {
			String[] tokens = getInstructions().get(instruction).split(" ");
			if (tokens.length == 2) {
				tokens[0] = (tokens[0].equals("inc") ? "dec" : "inc");
			}
			if (tokens.length == 3) {
				tokens[0] = (tokens[0].equals("jnz") ? "cpy" : "jnz");
			}
			getInstructions().set(instruction, String.join(" ", tokens));
		}
	}

	@Override
	public String toString() {
		return (String.format("[%d, %d, %d, %d]", get("a"), get("b"), get("c"), get("d")));
	}
}
