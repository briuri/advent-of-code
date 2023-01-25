package buri.aoc.y17.d18;

import java.util.List;

import buri.aoc.common.data.registers.NamedRegisters;

/**
 * Abstract base class for registers in each part.
 *
 * @author Brian Uri!
 */
public abstract class AbstractRegisters extends NamedRegisters {

	/**
	 * Constructor
	 */
	public AbstractRegisters(List<String> instructions) {
		super(instructions);
		for (char name = 'a'; name <= 'z'; name++) {
			set(String.valueOf(name), 0L);
		}
	}

	/**
	 * Executes a sequence of instructions.
	 *
	 * snd X does something different depending on the implementation.
	 * rcv X does something different depending on the implementation.
	 * set X Y sets register X to the value of Y.
	 * add X Y increases register X by the value of Y.
	 * mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
	 * mod X Y sets register X to the remainder of dividing the value contained in register X by the value of Y (that
	 * is, it sets X to the result of X modulo Y).
	 * jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero. (An offset of 2
	 * skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
	 *
	 * Many of the instructions can take either a register (a single letter) or a number. The value of a register is the
	 * integer it contains; the value of a number is that number.
	 *
	 * After each jump instruction, the program continues with the instruction to which the jump jumped. After any other
	 * instruction, the program continues with the next instruction. Continuing (or jumping) off either end of the
	 * program terminates it.
	 */
	public void process() {
		while (true) {
			if (!isWithinInstructions()) {
				break;
			}
			String[] tokens = getInstructions().get(getCurrent()).split(" ");
			if (tokens[0].equals("snd")) {
				snd(tokens);
			}
			if (tokens[0].equals("rcv")) {
				boolean continueProcess = rcv(tokens);
				if (!continueProcess) {
					break;
				}
			}
			if (tokens[0].equals("set")) {
				long value = getRegisterOrValue(tokens[2]);
				getRegisters().put(tokens[1], value);
			}
			if (tokens[0].equals("add")) {
				long originalValue = get(tokens[1]);
				long addValue = getRegisterOrValue(tokens[2]);
				getRegisters().put(tokens[1], originalValue + addValue);
			}
			if (tokens[0].equals("mul")) {
				long originalValue = get(tokens[1]);
				long multiplyValue = getRegisterOrValue(tokens[2]);
				getRegisters().put(tokens[1], originalValue * multiplyValue);
			}
			if (tokens[0].equals("mod")) {
				long originalValue = get(tokens[1]);
				long modValue = getRegisterOrValue(tokens[2]);
				getRegisters().put(tokens[1], originalValue % modValue);
			}
			if (tokens[0].equals("jgz")) {
				long value = getRegisterOrValue(tokens[1]);
				if (value > 0) {
					setCurrent(getCurrent() + getRegisterOrValue(tokens[2]).intValue());
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
	 * snd X does something different depending on the implementation.
	 */
	protected abstract void snd(String[] tokens);

	/**
	 * rcv X does something different depending on the implementation.
	 */
	protected abstract boolean rcv(String[] tokens);

}