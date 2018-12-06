package buri.aoc.y17.d13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * The firewall consists of several layers, each with a security scanner that moves back and forth across the layer. To
 * succeed, you must not be detected by a scanner.
 * 
 * By studying the firewall briefly, you are able to record (in your puzzle input) the depth of each layer and the range
 * of the scanning area for the scanner within it, written as depth: range. Each layer has a thickness of exactly 1. A
 * layer at depth 0 begins immediately inside the firewall; a layer at depth 1 would start immediately after that.
 * 
 * Within each layer, a security scanner moves back and forth within its range. Each security scanner starts at the top
 * and moves down until it reaches the bottom, then moves up until it reaches the top, and repeats. A security scanner
 * takes one picosecond to move one step.
 * 
 * Your plan is to hitch a ride on a packet about to move through the firewall. The packet will travel along the top of
 * each layer, and it moves at one layer per picosecond. Each picosecond, the packet moves one layer forward (its first
 * move takes it into layer 0), and then the scanners move one step. If there is a scanner at the top of the layer as
 * your packet enters it, you are caught. (If a scanner moves into the top of its layer while you are there, you are not
 * caught: it doesn't have time to notice you before you leave.)
 * 
 * @author Brian Uri!
 */
public class Day13 extends Puzzle {

	/**
	 * Input: A list of layer depths to scanner ranges
	 * Output:
	 */
	public static List<Layer> getInput(int fileIndex) {
		List<Layer> data = new ArrayList<>();
		for (String rawData : readFile("2017/13", fileIndex)) {
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
			return (navigateFirewall(Part.ONE, firewall, 0));
		}		
		// Part TWO
		//int delay = -1;
		int delay = -2;
		while (true) {
			//delay++;
			delay = delay + 2;
			int severity = navigateFirewall(Part.TWO, firewall, delay);
			boolean wasCaught = false;
			for (Layer layer : firewall.values()) {
				wasCaught = wasCaught || layer.getWasCaught();
			}
			if (severity == 0 && !wasCaught) {
				return (delay);
			}
		}		
	}
	
	/**
	 * Sends the packet through the firewall and returns the total severity.
	 */
	private static int navigateFirewall(Part part, Map<Integer, Layer> firewall, int delay) {
		// Reset the firewall and then allow it to run after the specified time.
		for (Layer layer : firewall.values()) {
			layer.reset();
		}
		for (int i = 0; i < delay; i++) {
			for (Layer layer : firewall.values()) {
				layer.moveScanner();
			}	
		}
				
		int lastLayer = Collections.max(firewall.keySet());
		int severity = 0;
		for (int i = 0; i <= lastLayer; i++) {
			Layer currentLayer = firewall.get(i);
			if (currentLayer != null) {
				severity += currentLayer.enterLayer();
			}
			for (Layer layer : firewall.values()) {
				layer.moveScanner();
			}
		}
		return (severity);	
	}
}