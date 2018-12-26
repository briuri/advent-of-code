package buri.aoc.y18.d21;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 21: Chronal Conversion
 * 
 * @author Brian Uri!
 */
public class Day21 extends Puzzle {

/**
 * Instructions converted into pseudocode:
 * 																nextIp = 0
 * 0 seti 123 0 3			reg[3] = 123						nextIp = 1
 * 1 bani 3 456 3			reg[3] = reg[3] & 456				nextIp = 2
 * 2 eqri 3 72 3			reg[3] = (reg[3] == 72 ? 1 : 0)		nextIp = 3
 * 3 addr 3 4 4				reg[4] = reg[3] + reg[4]			nextIp = 4 or 5
 * 4 seti 0 0 4				reg[4] = 0							nextIp = 1 (infinite loop)
 * 
 * 5 seti 0 5 3				reg[3] = 0							nextIp = 6
 * 6 bori 3 65536 2			reg[2] = reg[3] | 65536				nextIp = 7
 * 7 seti 10736359 9 3		reg[3] = 10736359					nextIp = 8
 * 8 bani 2 255 1			reg[1] = reg[2] & 255				nextIp = 9
 * 9 addr 3 1 3				reg[3] = reg[3] + reg[1]			nextIp = 10
 * 10 bani 3 16777215 3		reg[3] = reg[3] & 16777215			nextIp = 11
 * 11 muli 3 65899 3		reg[3] = reg[3] * 65899				nextIp = 12
 * 12 bani 3 16777215 3		reg[3] = reg[3] & 16777215			nextIp = 13
 * 13 gtir 256 2 1			reg[1] = (256 > reg[2] ? 1 : 0)		nextIp = 14
 * 14 addr 1 4 4			reg[4] = reg[4] + reg[1]			nextIp = 15 or 16
 * 15 addi 4 1 4			reg[4] = reg[4] + 1					nextIp = 17
 * 16 seti 27 2 4			reg[4] = 27							nextIp = 28
 * 17 seti 0 3 1			reg[1] = 0							nextIp = 18
 * 18 addi 1 1 5			reg[5] = reg[1] + 1					nextIp = 19
 * 19 muli 5 256 5			reg[5] = reg[5] * 256				nextIp = 20
 * 20 gtrr 5 2 5			reg[5] = (reg[5] > reg[2] ? 1 : 0)	nextIp = 21
 * 21 addr 5 4 4			reg[4] = reg[4] + reg[5]			nextIp = 22 or 23
 * 22 addi 4 1 4			reg[4] = reg[4] + 1					nextIp = 24
 * 23 seti 25 8 4			reg[4] = 25							nextIp = 26
 * 24 addi 1 1 1			reg[1] = reg[1] + 1					nextIp = 25
 * 25 seti 17 6 4			reg[4] = 17							nextIp = 18
 * 26 setr 1 5 2			reg[2] = reg[1]						nextIp = 27
 * 27 seti 7 7 4			reg[4] = 7							nextIp = 8
 * 
 * 28 eqrr 3 0 1			reg[1] = (reg[3] == reg[0] ? 1 : 0)	nextIp = 29
 * 29 addr 1 4 4			reg[4] = reg[4] + reg[1]			nextIp = 30 or 31
 * 30 seti 5 1 4			reg[4] = 5							nextIp = 6
 */
	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2018/21", fileIndex));
	}

	/**
	 * Part 1:
	 * What is the lowest non-negative integer value for register 0 that causes the program to halt after executing the
	 * fewest instructions?
	 * 
	 * Part 2:
	 * What is the lowest non-negative integer value for register 0 that causes the program to halt after executing the
	 * most instructions?
	 */
	public static int getResult(Part part, List<String> input) {
		String ipRegister = input.remove(0);

		/**
		 * This solution was exploratory. I converted the input into pseudocode (see above). After wasting too much
		 * time uselessly messing with the loops, I finally honed in on instruction 28 (the control point to exit the
		 * program). I then added output to an trial run (reg[0]=0) of the program to find out what the first value of
		 * reg[3] was when instruction 28 was reached.
		 * 
		 * For Part 2, I used the same idea but looked for when the reg[3] check started repeating itself and chose
		 * the number right before the repeat. (This takes a long time, about 9 minutes to find the value and 8 minutes
		 * to confirm it!)
		 * 
		 * TODO: Optimize part 2 so it doesn't take so long.
		 */
		Registers registers = new Registers(Character.getNumericValue(ipRegister.charAt(4)), input);
		int regThree = registers.trialRun(part);

		// Confirm the correct answer (should not loop infinitely).
		// registers.reset();
		// registers.run(regThree);
		return (regThree);
	}
}