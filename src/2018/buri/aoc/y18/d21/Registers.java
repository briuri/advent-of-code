package buri.aoc.y18.d21;

import java.util.List;

import buri.aoc.data.registers.IndexedRegisters;

/**
 * Representation of the registers based on logic from Day 16 and 19.
 * 
 * @author Brian Uri!
 */
public class Registers extends IndexedRegisters {

	/**
	 * Constructor
	 */
	public Registers(int ipRegister, List<String> codes) {
		super(ipRegister, codes);
	}

	/**
	 * Runs the instructions just like Day 19. Starts with reg[0] = 0 and quits at the expected value for reg[3].
	 * 
	 * Logic is specific to my input (control line 28, control check reg[3]).
	 */
	public int trialRun() {
		while (true) {
			String code = getCodes().get(getIp());
			set(getIpRegister(), getIp());
			runStringCode(code.split(" "));
			setIp((int) get(Registers.REGISTER, getIpRegister()) + 1);

			if (getIp() == 28) {
				return (get(Registers.REGISTER, 3));
			}
		}
	}
}