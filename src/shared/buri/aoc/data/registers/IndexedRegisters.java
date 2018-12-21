package buri.aoc.data.registers;

/**
 * @author Brian Uri!
 */
public class IndexedRegisters {
	private int[] _registers;
	
	// For readability of the opcodes.
	public static final boolean REGISTER = false;
	public static final boolean VALUE = true;
	
	/**
	 * Constructor
	 */
	public IndexedRegisters(int size) {
		_registers = new int[size];
	}
	
	/**
	 * Returns either the value itself (immediate/value) or the value from the register with that index (register).
	 */
	public int get(boolean isImmediate, int value) {
		return (isImmediate ? value : getRegisters()[value]);
	}
	
	/**
	 * Updates the value in a register.
	 */
	protected void set(int index, int value) {
		getRegisters()[index] = value;
	}
	
	/**
	 * Executes an actual instruction against the registers, based on data explored in Day 16.
	 */
	public void runIntCode(String[] code) {
		int opcode = Integer.valueOf(code[0]);
		int a = Integer.valueOf(code[1]);
		int b = Integer.valueOf(code[2]);
		int c = Integer.valueOf(code[3]);
		if (opcode == 0) { // mulr
			set(c, get(REGISTER, a) * get(REGISTER, b));
		}
		else if (opcode == 1) { // eqri
			set(c, (get(REGISTER, a) == get(VALUE, b) ? 1 : 0));
		}
		else if (opcode == 2) { // setr
			set(c, get(REGISTER, a));
		}
		else if (opcode == 3) {// eqrr
			set(c, (get(REGISTER, a) == get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode == 4) {// gtrr
			set(c, (get(REGISTER, a) > get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode == 5) {// muli
			set(c, get(REGISTER, a) * get(VALUE, b));
		}
		else if (opcode == 6) {// borr
			set(c, get(REGISTER, a) | get(REGISTER, b));
		}
		else if (opcode == 7) {// bani
			set(c, get(REGISTER, a) & get(VALUE, b));
		}
		else if (opcode == 8) {// addr
			set(c, get(REGISTER, a) + get(REGISTER, b));
		}
		else if (opcode == 9) {// banr
			set(c, get(REGISTER, a) & get(REGISTER, b));
		}
		else if (opcode == 10) {// eqir
			set(c, (get(VALUE, a) == get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode == 11) {// gtir
			set(c, (get(VALUE, a) > get(REGISTER, b) ? 1 : 0));
		}
		else if (opcode == 12) {// addi
			set(c, get(REGISTER, a) + get(VALUE, b));
		}
		else if (opcode == 13) {// gtri
			set(c, (get(REGISTER, a) > get(VALUE, b) ? 1 : 0));
		}
		else if (opcode == 14) {// seti
			set(c, get(VALUE, a));
		}
		else if (opcode == 15) {// bori
			set(c, get(REGISTER, a) | get(VALUE, b));
		}
	}
	
	/**
	 * Converts string opcodes into the integer values found on Day 16.
	 */
	public void runStringCode(String[] code) {
		String opcode = code[0];
		if (opcode.equals("mulr")) {
			code[0] = String.valueOf(0);
		}
		else if (opcode.equals("eqri")) {
			code[0] = String.valueOf(1);
		}
		else if (opcode.equals("setr")) {
			code[0] = String.valueOf(2);
		}
		else if (opcode.equals("eqrr")) {
			code[0] = String.valueOf(3);
		}
		else if (opcode.equals("gtrr")) {
			code[0] = String.valueOf(4);
		}
		else if (opcode.equals("muli")) {
			code[0] = String.valueOf(5);
		}
		else if (opcode.equals("borr")) {
			code[0] = String.valueOf(6);
		}
		else if (opcode.equals("bani")) {
			code[0] = String.valueOf(7);
		}
		else if (opcode.equals("addr")) {
			code[0] = String.valueOf(8);
		}
		else if (opcode.equals("banr")) {
			code[0] = String.valueOf(9);
		}
		else if (opcode.equals("eqir")) {
			code[0] = String.valueOf(10);
		}
		else if (opcode.equals("gtir")) {
			code[0] = String.valueOf(11);
		}
		else if (opcode.equals("addi")) {
			code[0] = String.valueOf(12);
		}
		else if (opcode.equals("gtri")) {
			code[0] = String.valueOf(13);
		}
		else if (opcode.equals("seti")) {
			code[0] = String.valueOf(14);
		}
		else if (opcode.equals("bori")) {
			code[0] = String.valueOf(15);
		}
		runIntCode(code);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i : getRegisters()) {
			buffer.append(i).append(" ");
		}
		return (buffer.toString().trim());
	}

	/**
	 * String-based equality used to quickly compare register states.
	 */
	@Override
	public boolean equals(Object obj) {
		return (toString().equals(obj.toString()));
	}
	
	/**
	 * Accessor for the registers
	 */
	protected int[] getRegisters() {
		return _registers;
	}
}
