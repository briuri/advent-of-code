package buri.aoc.y15.d22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import buri.aoc.common.Part;
import buri.aoc.y15.d22.Effect.Type;

/**
 * @author Brian Uri!
 */
public class Battle {
	private List<Effect> _effects;
	private int _playerHealth = 50;
	private int _playerMana = 500;
	private int _playerManaUsed = 0;
	private int _playerArmor = 0;
	private int _bossHealth = 55;
	private int _bossAttack = 8;
	private Part _part;

	private static final Map<String, Integer> SPELLS = new HashMap<>();

	static {
		SPELLS.put("M", 53);
		SPELLS.put("D", 73);
		SPELLS.put("S", 113);
		SPELLS.put("P", 173);
		SPELLS.put("R", 229);
	}

	private static String[] ALL_SPELLS = new String[] { "M", "D", "S", "P", "R" };

	/**
	 * Constructor
	 */
	public Battle(Part part) {
		_effects = new ArrayList<>();
		_part = part;
	}

	/**
	 * Randomizes a fight. Returns the mana cost of a win or a high number for a loss.
	 */
	public int run(int currentMinManaCost) {
		List<String> spellsCast = new ArrayList<>();
		boolean someoneDied = false;
		while (!someoneDied) {

			List<String> allowedSpells = new ArrayList(Arrays.asList(ALL_SPELLS));
			List<Type> allowedEffects = getAllowedEffects();
			for (Iterator<String> iterator = allowedSpells.iterator(); iterator.hasNext();) {
				String spell = iterator.next();
				if ((SPELLS.get(spell) > getPlayerMana())
					|| (spell.equals("S") && !allowedEffects.contains(Type.SHIELD))
					|| (spell.equals("P") && !allowedEffects.contains(Type.POISON))
					|| (spell.equals("R") && !allowedEffects.contains(Type.RECHARGE))) {
					iterator.remove();
				}
			}
			// Short circuit if we're out of options or exceed a previous win.
			if (allowedSpells.isEmpty() || getPlayerManaUsed() > currentMinManaCost) {
				setPlayerHealth(0);
				break;
			}
			int selection = new Random().nextInt(allowedSpells.size());
			String spell = allowedSpells.get(selection);
			spellsCast.add(spell);
			someoneDied = runTurn(spell);
		}
		return (getPlayerHealth() > 0 ? getPlayerManaUsed() : Integer.MAX_VALUE);
	}

	/**
	 * Runs a single pair of player/boss turns. Short circuits and returns true when someone dies.
	 */
	public boolean runTurn(String spell) {
		// Player Turn
		if (getPart() == Part.TWO) {
			setPlayerHealth(getPlayerHealth() - 1);
		}
		if (isAnyoneDead()) {
			return (true);
		}

		processEffects();
		if (isAnyoneDead()) {
			return (true);
		}

		setPlayerMana(getPlayerMana() - SPELLS.get(spell));
		setPlayerManaUsed(getPlayerManaUsed() + SPELLS.get(spell));
		if (spell.equals("M")) {
			setBossHealth(getBossHealth() - 4);
		}
		if (spell.equals("D")) {
			setBossHealth(getBossHealth() - 2);
			setPlayerHealth(getPlayerHealth() + 2);
		}
		if (spell.equals("S")) {
			addEffect(Type.SHIELD);
		}
		if (spell.equals("P")) {
			addEffect(Type.POISON);
		}
		if (spell.equals("R")) {
			addEffect(Type.RECHARGE);
		}
		if (isAnyoneDead()) {
			return (true);
		}

		// Boss Turn
		processEffects();
		if (isAnyoneDead()) {
			return (true);
		}

		int damage = Math.max(1, getBossAttack() - getPlayerArmor());
		setPlayerHealth(getPlayerHealth() - damage);
		return (isAnyoneDead());
	}

	/**
	 * Returns true if either unit is dead.
	 */
	private boolean isAnyoneDead() {
		return (getPlayerHealth() <= 0 || getBossHealth() <= 0);
	}

	/**
	 * Returns a list of effect types that can be cast legally at this time.
	 */
	private List<Type> getAllowedEffects() {
		List<Type> types = new ArrayList<Type>(Arrays.asList(Type.values()));
		for (Effect effect : getEffects()) {
			if (!effect.isExpiring()) {
				types.remove(effect.getType());
			}
		}
		return (types);
	}

	/**
	 * Adds a new effect to the current effects (but does not tick).
	 */
	private void addEffect(Type type) {
		getEffects().add(new Effect(type));
	}

	/**
	 * Processes any active effects and removes expired ones.
	 */
	private void processEffects() {
		for (Iterator<Effect> iterator = getEffects().iterator(); iterator.hasNext();) {
			Effect effect = iterator.next();

			// Initial Effects
			if (effect.getType() == Type.SHIELD && effect.isNew()) {
				setPlayerArmor(7);
			}
			// Recurring Effects
			if (effect.getType() == Type.POISON) {
				setBossHealth(getBossHealth() - 3);
			}
			if (effect.getType() == Type.RECHARGE) {
				setPlayerMana(getPlayerMana() + 101);
			}
			// Post Effects
			if (effect.getType() == Type.SHIELD && effect.isExpiring()) {
				setPlayerArmor(0);
			}

			boolean isDone = effect.tick();
			if (isDone) {
				iterator.remove();
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\tP[").append(getPlayerHealth()).append("hp, ").append(getPlayerArmor()).append("ac, ").append(
			getPlayerMana()).append("mp] ");
		builder.append("B[").append(getBossHealth()).append("hp]");
		return (builder.toString());
	}

	/**
	 * Accessor for the effects
	 */
	private List<Effect> getEffects() {
		return _effects;
	}

	/**
	 * Accessor for the playerHealth
	 */
	private int getPlayerHealth() {
		return _playerHealth;
	}

	/**
	 * Accessor for the playerHealth
	 */
	private void setPlayerHealth(int playerHealth) {
		_playerHealth = playerHealth;
	}

	/**
	 * Accessor for the playerMana
	 */
	private int getPlayerMana() {
		return _playerMana;
	}

	/**
	 * Accessor for the playerMana
	 */
	private void setPlayerMana(int playerMana) {
		_playerMana = playerMana;
	}

	/**
	 * Accessor for the playerManaUsed
	 */
	private int getPlayerManaUsed() {
		return _playerManaUsed;
	}

	/**
	 * Accessor for the playerManaUsed
	 */
	private void setPlayerManaUsed(int playerManaUsed) {
		_playerManaUsed = playerManaUsed;
	}

	/**
	 * Accessor for the playerArmor
	 */
	private int getPlayerArmor() {
		return _playerArmor;
	}

	/**
	 * Accessor for the playerArmor
	 */
	private void setPlayerArmor(int playerArmor) {
		_playerArmor = playerArmor;
	}

	/**
	 * Accessor for the bossHealth
	 */
	private int getBossHealth() {
		return _bossHealth;
	}

	/**
	 * Accessor for the bossHealth
	 */
	private void setBossHealth(int bossHealth) {
		_bossHealth = bossHealth;
	}

	/**
	 * Accessor for the bossAttack
	 */
	private int getBossAttack() {
		return _bossAttack;
	}

	/**
	 * Accessor for the part
	 */
	private Part getPart() {
		return _part;
	}
}