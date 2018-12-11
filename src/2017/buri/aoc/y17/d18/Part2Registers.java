package buri.aoc.y17.d18;

import java.util.List;
import java.util.Queue;

/**
 * Implementation of Registers for Part 2.
 * 
 * @author Brian Uri!
 */
public class Part2Registers extends AbstractRegisters {
	private Queue<Long> _incomingQueue;
	private Queue<Long> _outgoingQueue;
	private int _outgoingCount;

	/**
	 * Constructor. Fills register p with the program id.
	 */
	public Part2Registers(List<String> instructions, long programId, Queue<Long> incomingQueue,
		Queue<Long> outgoingQueue) {
		super(instructions);
		getRegisters().put("p", programId);
		_incomingQueue = incomingQueue;
		_outgoingQueue = outgoingQueue;
		_outgoingCount = 0;
	}

	/**
	 * snd X sends the value of X to the other program. These values wait in a queue until that program is ready to
	 * receive them. Each program has its own message queue, so a program can never receive a message it sent.
	 */
	@Override
	protected void snd(String[] tokens) {
		getOutgoingQueue().add(getParameter(tokens[1]));
		setOutgoingCount(getOutgoingCount() + 1);
	}

	/**
	 * rcv X receives the next value and stores it in register X. If no values are in the queue, the program waits for a
	 * value to be sent to it. Programs do not continue to the next instruction until they have received a value. Values
	 * are received in the order they are sent.
	 */
	@Override
	protected boolean rcv(String[] tokens) {
		if (getIncomingQueue().isEmpty()) {
			return (false);
		}
		getRegisters().put(tokens[1], getIncomingQueue().remove());
		return (true);
	}

	/**
	 * Returns true if this set of registers is waiting on a rcv signal.
	 */
	public boolean isWaitingForQueue() {
		return (isWithinInstructions() && getIncomingQueue().isEmpty());
	}

	/**
	 * Accessor for the incomingQueue
	 */
	private Queue<Long> getIncomingQueue() {
		return _incomingQueue;
	}

	/**
	 * Accessor for the outgoingQueue
	 */
	private Queue<Long> getOutgoingQueue() {
		return _outgoingQueue;
	}

	/**
	 * Accessor for the number of times this program sent a value.
	 */
	public int getOutgoingCount() {
		return _outgoingCount;
	}

	/**
	 * Accessor for the number of times this program sent a value.
	 */
	private void setOutgoingCount(int outgoingCount) {
		_outgoingCount = outgoingCount;
	}
}