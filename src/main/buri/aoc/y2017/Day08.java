package buri.aoc.y2017;

import java.util.List;

import buri.aoc.model.Part;
import buri.aoc.model.RegisterInstruction;
import buri.aoc.model.Registers;

/**
 * You receive a signal directly from the CPU. Because of your recent assistance with jump instructions, it would like
 * you to compute the result of a series of unusual register instructions.
 * 
 * Each instruction consists of several parts: the register to modify, whether to increase or decrease that register's
 * value, the amount by which to increase or decrease it, and a condition. If the condition fails, skip the instruction
 * without modifying the register. The registers all start at 0. The instructions look like this:
 * 
 * Part 2:
 * To be safe, the CPU also needs to know the highest value held in any register during this process so that it can
 * decide how much memory to allocate to these operations.
 * 
 * @author Brian Uri!
 */
public class Day08 {
	
	/**
	 * Executes the register instructions.
	 */
	public static int getLargestValue(Part part, List<RegisterInstruction> instructions) {
		Registers registers = new Registers();
		registers.process(instructions);
		return (registers.getLargestValue(part));
	}
}
