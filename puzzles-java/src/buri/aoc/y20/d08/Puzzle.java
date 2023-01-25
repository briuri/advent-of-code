package buri.aoc.y20.d08;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 08: Handheld Halting
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(5, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(2025, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(8, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(2001, result);
	}

	/**
	 * Part 1:
	 * Immediately before any instruction is executed a second time, what value is in the accumulator?
	 *
	 * Part 2:
	 * What is the value of the accumulator after the program terminates?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Instruction> instructions = new ArrayList<>();
		for (String line : input) {
			instructions.add(new Instruction(line));
		}

		if (part == Part.ONE) {
			return (runProgram(instructions));
		}

		ModIndex modIndex = new ModIndex();
		while (modIndex.getIndex() < input.size()) {
			List<Instruction> modded = modProgram(instructions, modIndex);
			if (!repeatsInstruction(modded)) {
				return (runProgram(modded));
			}
		}
		throw new RuntimeException("Every modified program repeated itself.");
	}

	/**
	 * Creates a copy of the input instructions, changing 1 nop or jmp. Keeps track of progress in the mutable modIndex
	 * value.
	 */
	protected static List<Instruction> modProgram(List<Instruction> input, ModIndex modIndex) {
		List<Instruction> copy = new ArrayList<>(input);
		for (int i = modIndex.getIndex(); i < copy.size(); i++) {
			Instruction instruction = copy.get(i);
			if (instruction.getOpcode() == Opcode.NOP || instruction.getOpcode() == Opcode.JMP) {
				modIndex.setIndex(i + 1);
				String newOperation = (instruction.getOpcode() == Opcode.NOP ? "jmp" : "nop");
				copy.set(i, new Instruction(newOperation, instruction.getArgument()));
				break;
			}
		}
		return copy;
	}

	/**
	 * Executes a list of instructions. If the program runs infinitely, stops the first time an instruction repeats.
	 * Returns the global value stored after all instructions run.
	 */
	protected static int runProgram(List<Instruction> input) {
		Set<Integer> ips = new HashSet<>();
		int ip = 0;
		int value = 0;
		while (true) {
			if (ips.contains(ip) || ip >= input.size()) {
				break;
			}
			ips.add(ip);

			Instruction i = input.get(ip);
			if (i.getOpcode() == Opcode.ACC) {
				value += i.getArgument();
				ip++;
			}
			else if (i.getOpcode()  == Opcode.JMP) {
				ip = ip + i.getArgument();
			}
			else if (i.getOpcode()  == Opcode.NOP) {
				ip++;
			}
		}
		return (value);
	}

	/**
	 * Checks if a program ever repeats an instruction.
	 */
	protected static boolean repeatsInstruction(List<Instruction> input) {
		Set<Integer> ips = new HashSet<>();
		int ip = 0;
		while (true) {
			if (ips.contains(ip)) {
				return (true);
			}
			else if (ip >= input.size()) {
				return (false);
			}
			ips.add(ip);

			Instruction i = input.get(ip);
			if (i.getOpcode() == Opcode.JMP) {
				ip = ip + i.getArgument();
			}
			else {
				ip++;
			}
		}
	}
}