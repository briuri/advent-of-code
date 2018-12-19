package buri.aoc.y18.d15;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import buri.aoc.data.Pair;
import buri.aoc.data.grid.CharGrid;

/**
 * You scan the area, generating a map of the walls (#), open cavern (.), and starting position of every Goblin (G) and
 * Elf (E) (your puzzle input).
 * 
 * @author Brian Uri!
 */
public class Grid extends CharGrid {
	private boolean _elfDied;
	private List<Unit> _elves;
	private List<Unit> _goblins;

	private static final char OPEN = '.';
	
	/**
	 * Constructor
	 */
	public Grid(List<String> input, int elfAttackPower) {
		super(input.size());
		_elfDied = false;
		_elves = new ArrayList<>();
		_goblins = new ArrayList<>();

		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y).trim();
			for (int x = 0; x < line.length(); x++) {
				Character type = line.charAt(x);
				set(x, y, type);
				if (type == Unit.ELF || type == Unit.GOBLIN) {
					Unit unit = new Unit(type, new Pair(x, y));
					if (unit.isElf()) {
						getElves().add(unit);
						unit.setAttackPower(elfAttackPower);
					}
					else {
						getGoblins().add(unit);
					}
				}
			}
		}
	}

	/**
	 * Starts the simulation.
	 */
	public int run() {
		int round = 0;
		boolean combatEnded = false;
		while (!combatEnded) {
			for (Unit unit : getAllLivingUnits()) {
				if (noEnemiesRemain()) {
					combatEnded = true;
					break;
				}
				moveWith(unit);
				attackWith(unit);
			}
			round++;
		}
		return ((round - 1) * getWinnerHealthSum());
	}

	/**
	 * Takes a living unit with no adjacent enemies and moves 1 step along the shortest path to the nearest enemy. Ties
	 * for shortest path are handled in reading order.
	 */
	private void moveWith(Unit unit) {
		if (!unit.isDead() && getAdjacentEnemies(unit).size() == 0) {
			List<Path> paths = getShortestPathsFor(unit);
			if (!paths.isEmpty()) {
				set(unit.getPosition(), OPEN);
				unit.setPosition(paths.get(0).getNextPosition());
				set(unit.getPosition(), unit.getType());
			}
		}
	}

	/**
	 * Takes a living unit and attacks the adjacent enemy with the lowest health. Removes any dead enemies from their
	 * respective lists.
	 */
	private void attackWith(Unit unit) {
		if (!unit.isDead()) {
			List<Unit> adjacent = getAdjacentEnemies(unit);
			if (!adjacent.isEmpty()) {
				Unit weakest = getWeakestUnit(adjacent);
				weakest.setHealth(weakest.getHealth() - unit.getAttackPower());
				if (weakest.isDead()) {
					set(weakest.getPosition(), OPEN);
					if (weakest.isElf()) {
						getElves().remove(weakest);
						setElfDied(true);
					}
					else {
						getGoblins().remove(weakest);
					}
				}
			}
		}
	}

	/**
	 * Returns true if either side has been defeated.
	 */
	private boolean noEnemiesRemain() {
		return (getElves().isEmpty() || getGoblins().isEmpty());
	}

	/**
	 * Returns the list of enemies for a unit.
	 */
	private List<Unit> getEnemies(Unit unit) {
		return (unit.isElf() ? getGoblins() : getElves());
	}

	/**
	 * Returns a list of all living units, in the order that they will take turns.
	 */
	private List<Unit> getAllLivingUnits() {
		List<Unit> allUnits = new ArrayList<>(getElves());
		allUnits.addAll(getGoblins());
		Collections.sort(allUnits);
		return (allUnits);
	}

	/**
	 * Finds the unit with the lowest HP in a list of units.
	 */
	private Unit getWeakestUnit(List<Unit> units) {
		int minHealth = Integer.MAX_VALUE;
		Unit weakest = null;
		for (Unit unit : units) {
			if (unit.getHealth() < minHealth) {
				minHealth = unit.getHealth();
				weakest = unit;
			}
		}
		return (weakest);
	}

	/**
	 * Returns the sum of health for all living winners.
	 */
	private int getWinnerHealthSum() {
		int sumHealth = 0;
		List<Unit> victors = (getElves().isEmpty() ? getGoblins() : getElves());
		for (Unit unit : victors) {
			sumHealth += unit.getHealth();
		}
		return (sumHealth);
	}

	/**
	 * Returns a list of all living enemies in adjacent positions to the unit, sorted in reading order.
	 */
	private List<Unit> getAdjacentEnemies(Unit unit) {
		List<Unit> adjacent = new ArrayList<>();
		for (Unit enemy : getEnemies(unit)) {
			if (enemy.isAdjacent(unit)) {
				adjacent.add(enemy);
			}
		}
		Collections.sort(adjacent);
		return (adjacent);
	}

	/**
	 * Returns the shortest paths to each of the possible enemy-adjacent destinations, using a breadth-first search.
	 */
	private List<Path> getShortestPathsFor(Unit unit) {
		// Get all open cells adjacent to enemies.
		List<Pair> destinations = new ArrayList<>();
		for (Unit enemy : getEnemies(unit)) {
			destinations.addAll(getOpenAdjacentCells(enemy.getPosition()));
		}

		// Generate breadth first mapping to find shortest paths to all points.
		Queue<Pair> frontier = new ArrayDeque<>();
		frontier.add(unit.getPosition());
		Map<Pair, Pair> cameFrom = new HashMap<>();
		cameFrom.put(unit.getPosition(), null);
		Pair current = null;
		while (!frontier.isEmpty()) {
			current = frontier.remove();
			for (Pair next : getOpenAdjacentCells(current)) {
				if (cameFrom.get(next) == null) {
					frontier.add(next);
					cameFrom.put(next, current);
				}
			}
		}

		// Use mapping to create the paths to all destination cells.
		List<Path> shortestPaths = new ArrayList<>();
		for (Pair destination : destinations) {
			if (cameFrom.get(destination) != null) {
				shortestPaths.add(new Path(unit.getPosition(), destination, cameFrom));
			}
			Collections.sort(shortestPaths);
		}
		return (shortestPaths);
	}

	/**
	 * Returns open cells adjacent to some position, in reading order (up, left, right, down).
	 */
	private List<Pair> getOpenAdjacentCells(Pair center) {
		List<Pair> openCells = new ArrayList<>();
		openCells.add(new Pair(center.getX(), center.getY() - 1));
		openCells.add(new Pair(center.getX() - 1, center.getY()));
		openCells.add(new Pair(center.getX() + 1, center.getY()));
		openCells.add(new Pair(center.getX(), center.getY() + 1));
		// Remove any that are already filled up.
		for (Iterator<Pair> iterator = openCells.iterator(); iterator.hasNext();) {
			Pair position = iterator.next();
			if (get(position) != OPEN) {
				iterator.remove();
			}
		}
		return (openCells);
	}

	/**
	 * Accessor for the flag denoting whether a single elf died.
	 */
	public boolean getElfDied() {
		return _elfDied;
	}

	/**
	 * Accessor for the flag denoting whether a single elf died.
	 */
	private void setElfDied(boolean elfDied) {
		_elfDied = elfDied;
	}

	/**
	 * Accessor for the living elves.
	 */
	private List<Unit> getElves() {
		return _elves;
	}

	/**
	 * Accessor for the living goblins.
	 */
	private List<Unit> getGoblins() {
		return _goblins;
	}
}