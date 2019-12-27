package buri.aoc.y17.d13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 13: Packet Scanners
 * 
 * @author Brian Uri!
 */
public class Day13 extends BasePuzzle {

	/**
	 * Returns input file as a list of layers
	 */
	public static List<Layer> getInput(int fileIndex) {
		List<Layer> data = new ArrayList<>();
		for (String rawData : readFile(fileIndex)) {
			data.add(new Layer(rawData));
		}
		return (data);
	}

	/**
	 * Part 1:
	 * The severity of getting caught on a layer is equal to its depth multiplied by its range. (Ignore layers in which
	 * you do not get caught.) The severity of the whole trip is the sum of these values.
	 * 
	 * Part 2:
	 * What is the fewest number of picoseconds that you need to delay the packet to pass through the firewall without
	 * being caught?
	 */
	public static int getResult(Part part, List<Layer> input) {
		Map<Integer, Layer> firewall = new HashMap<>();
		for (Layer layer : input) {
			firewall.put(layer.getDepth(), layer);
		}
		if (part == Part.ONE) {
			return (getSeverity(firewall, 0));
		}
		// Part TWO
		int delay = -1;
		while (true) {
			delay++;
			if (!getCaught(firewall, delay)) {
				return (delay);
			}
		}
	}

	/**
	 * Sends the packet through the firewall and returns whether it got caught.
	 */
	private static boolean getCaught(Map<Integer, Layer> firewall, int delay) {
		for (Layer layer : firewall.values()) {
			int timeToArriveHere = delay + layer.getDepth();
			if (layer.isCaught(timeToArriveHere)) {
				return (true);
			}
		}
		return (false);
	}

	/**
	 * Sends the packet through the firewall and returns the total severity.
	 */
	private static int getSeverity(Map<Integer, Layer> firewall, int delay) {
		int severity = 0;
		for (Layer layer : firewall.values()) {
			int timeToArriveHere = delay + layer.getDepth();
			if (layer.isCaught(timeToArriveHere)) {
				severity += layer.getSeverity();
			}
		}
		return (severity);
	}
}