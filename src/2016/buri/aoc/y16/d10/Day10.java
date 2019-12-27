package buri.aoc.y16.d10;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 10: Balance Bots
 * 
 * @author Brian Uri!
 */
public class Day10 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * What is the number of the bot that is responsible for comparing value-61 microchips with value-17 microchips?
	 * 
	 * Part 2:
	 * What do you get if you multiply together the values of one chip in each of outputs 0, 1, and 2?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Bot> bots = new ArrayList<>();
		for (int i = 0; i < 210; i++) {
			bots.add(new Bot());
		}
		// Map target values (0,1,2...x) to (-1,-2,-3....(-x-1))
		List<Integer> output = new ArrayList<>();
		for (int i = 0; i < 21; i++) {
			output.add(0);
		}

		for (String line : input) {
			String[] tokens = line.split(" ");
			if (tokens[0].equals("value")) {
				int value = Integer.valueOf(tokens[1]);
				int bot = Integer.valueOf(tokens[5]);
				bots.get(bot).addValue(value);
			}
			else if (tokens[0].equals("bot")) {
				int bot = Integer.valueOf(tokens[1]);
				int lowTarget = Integer.valueOf(tokens[6]);
				if (tokens[5].equals("output")) {
					lowTarget = (lowTarget * -1) - 1;
				}
				int highTarget = Integer.valueOf(tokens[11]);
				if (tokens[10].equals("output")) {
					highTarget = (highTarget * -1) - 1;
				}
				bots.get(bot).setLowTarget(lowTarget);
				bots.get(bot).setHighTarget(highTarget);
			}
		}

		int index = getFullBotIndex(bots);
		while (index != -1) {
			Bot bot = bots.get(index);
			int lowTarget = bot.getLowTarget();
			if (lowTarget < 0) {
				output.set((lowTarget + 1) * -1, bot.getLowValue());
			}
			else {
				bots.get(lowTarget).addValue(bot.getLowValue());
			}
			int highTarget = bot.getHighTarget();
			if (highTarget < 0) {
				output.set((highTarget + 1) * -1, bot.getHighValue());
			}
			else {
				bots.get(highTarget).addValue(bot.getHighValue());
			}
			index = getFullBotIndex(bots);
		}

		if (part == Part.ONE) {
			for (int i = 0; i < bots.size(); i++) {
				if (bots.get(i).getValueSnapshot().equals("[17, 61]")) {
					return (i);
				}
			}
			throw new RuntimeException("No bot evaluated [17, 61].");
		}

		// Part TWO
		return (output.get(0) * output.get(1) * output.get(2));
	}

	/**
	 * Returns the index of the first full bot or -1 if none are full.
	 */
	public static int getFullBotIndex(List<Bot> bots) {
		for (int i = 0; i < bots.size(); i++) {
			if (bots.get(i).isFull()) {
				return (i);
			}
		}
		return (-1);
	}
}