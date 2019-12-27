package buri.aoc.y19.d21;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.y19.intcode.Computer;

/**
 * Day 21: Springdroid Adventure
 * 
 * @author Brian Uri!
 */
public class Day21 extends BasePuzzle {

	/**
	 * Returns the input file as an Intcode program
	 */
	public static List<Long> getInput(int fileIndex) {
		List<Long> list = new ArrayList<>();
		for (String input : readFile("2019/21", fileIndex).get(0).split(",")) {
			list.add(Long.valueOf(input));
		}
		return (list);
	}
		
	/**
	 * Part 1:
	 * What amount of hull damage does it report?
	 * 
	 * Part 2:
	 * What amount of hull damage does the springdroid now report?
	 */
	public static long getResult(Part part, List<Long> program) {
		// Jump if a hole is in A, B, or C, but only if D is ground.
		// J = (!A || !B || !C) && D
		String[] springscript1 = new String[] {
			"NOT A J",	// J = !A
			"NOT B T",	// T = !B
			"OR T J",	// J = (!A || !B)
			"NOT C T",	// T = !C
			"OR T J",	// J = (!A || !B || !C)
			"AND D J",	// J = (!A || !B || !C) && D
			"WALK",
		};

		// Jump if a hole is in A, B or C, but only if D is ground and you can walk or jump again.
		// J = (!A || !B || !C) && D && (E || H))
		String[] springscript2 = new String[] {
			"NOT A J",	// J = !A
			"NOT B T",	// T = !B
			"OR T J",	// J = (!A || !B)
			"NOT C T",	// T = !C
			"OR T J",	// J = (!A || !B || !C)
			"AND D J",	// J = (!A || !B || !C) && D
			"NOT E T",	// T = !E
			"NOT T T",	// T = E
			"OR H T",	// T = (E || H)
			"AND T J",	// J = (!A || !B || !C) && D && (E || H)
			"RUN\n",
		};

		String[] script = (part == Part.ONE ? springscript1 : springscript2);
		List<Long> inputs = new ArrayList<>();
		for (String line : script) {
			inputs.addAll(Computer.toAscii(line));
		}
		
		Computer computer = new Computer(program);
		computer.getInputs().addAll(inputs);
		computer.run();
		// Only build grid if springdroid failed.
		if (computer.getOutputs().size() > 34) {
			CharGrid grid = new CharGrid(60);
			int x = 0;
			int y = 0;
			for (Long value : computer.getOutputs()) {
				int tile = value.intValue();
				if (tile == Computer.LINE_BREAK) {
					y++;
					x = 0;
				}
				else {
					grid.set(x, y, (char) tile);
					x++;
				}
			}
			System.out.println(computer.getOutputs().size());
		}
		
		return (computer.getLastOutput());
	}
}