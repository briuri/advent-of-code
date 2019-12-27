package buri.aoc.y19.d23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.intcode.Computer;
import buri.aoc.data.tuple.Pair;

/**
 * Day 23: Category Six
 * 
 * @author Brian Uri!
 */
public class Day23 extends BasePuzzle {

	/**
	 * Returns the input file as an Intcode program
	 */
	public static List<Long> getInput(int fileIndex) {
		List<Long> list = new ArrayList<>();
		for (String input : readFile("2019/23", fileIndex).get(0).split(",")) {
			list.add(Long.valueOf(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * Boot up all 50 computers and attach them to your network. What is the Y value of the first packet sent to address
	 * 255?
	 * 
	 * Part 2:
	 * Monitor packets released to the computer at address 0 by the NAT. What is the first Y value delivered by the NAT
	 * to the computer at address 0 twice in a row?
	 */
	public static long getResult(Part part, List<Long> program) {
		// Initialize computers.
		Map<Long, Computer> computers = new HashMap<>();
		for (long i = 0; i < 50; i++) {
			Computer computer = new Computer(program);
			computers.put(i, computer);
			computer.getInputs().add(i);
			computer.run();
		}

		Set<Pair> sentPackets = new HashSet<>();
		Pair nat = null;
		while (true) {
			// Queue up any packets.
			for (Computer sender : computers.values()) {
				while (sender.getOutputs().size() > 0) {
					Long address = sender.getOutputs().remove(0);
					Long x = sender.getOutputs().remove(0);
					Long y = sender.getOutputs().remove(0);
					if (address == 255L) {
						if (part == Part.ONE) {
							return (y);
						}
						nat = new Pair(x.intValue(), y.intValue());
					}
					else {
						Computer receiver = computers.get(address);
						receiver.getInputs().add(x);
						receiver.getInputs().add(y);
					}
				}
			}

			// In Part TWO only, allow the NAT to check network idleness.
			if (part == Part.TWO) {
				boolean allIdle = true;
				for (Computer receiver : computers.values()) {
					allIdle = allIdle && (receiver.getInputs().size() == 0);
				}
				if (allIdle && nat != null) {
					Computer receiver = computers.get(0L);
					receiver.getInputs().add(Long.valueOf(nat.getX()));
					receiver.getInputs().add(Long.valueOf(nat.getY()));
					if (sentPackets.contains(nat)) {
						return (nat.getY());
					}
					sentPackets.add(nat);
				}
			}

			// Fill any computers with no packets with -1 then run again.
			for (Computer receiver : computers.values()) {
				if (receiver.getInputs().size() == 0) {
					receiver.getInputs().add(-1L);
				}
				receiver.run();
			}
		}
	}
}