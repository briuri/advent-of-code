package buri.aoc.y17.d13;

/**
 * Mutable data class for a firewall layer.
 *  
 * @author Brian Uri!
 */
public class Layer {

	int _depth;
	int _range;
	int _scannerPosition;
	int _scannerDirection;
	boolean _wasCaught;
	


	/**
	 * Constructor
	 * 
	 * 0: 3
	 */
	public Layer(String input) {
		String[] tokens = input.split(": ");
		_depth = Integer.valueOf(tokens[0]);
		_range = Integer.valueOf(tokens[1]);
	}
	
	/**
	 * Resets the layer
	 */
	public void reset() {
		setScannerPosition(0);
		setScannerDirection(1);
		setWasCaught(false);
	}
	/**
	 * Enters the 0 spot of the layer. Returns the severity of getting caught.
	 */
	public int enterLayer() {
		if (getScannerPosition() == 0) {
			setWasCaught(true);
			return (getDepth() * getRange());
		}
		return (0);
	}
	
	/**
	 * Oscillates the scanner across the range.
	 */
	public void moveScanner() {
		int nextPosition = getScannerPosition() + getScannerDirection();
		if (nextPosition == getRange() || nextPosition < 0) {
			reverseScannerDirection();
			nextPosition = getScannerPosition() + getScannerDirection();
		}
		setScannerPosition(nextPosition);
	}
	
	/**
	 * Sends the scanner in the other direction.
	 */
	private void reverseScannerDirection() {
		setScannerDirection(getScannerDirection() * -1);
	}	
	/**
	 * Accessor for the depth
	 */
	public int getDepth() {
		return _depth;
	}

	/**
	 * Accessor for the range
	 */
	private int getRange() {
		return _range;
	}

	/**
	 * Accessor for the scannerPosition
	 */
	private int getScannerPosition() {
		return _scannerPosition;
	}

	/**
	 * Accessor for the scannerPosition
	 */
	private void setScannerPosition(int scannerPosition) {
		_scannerPosition = scannerPosition;
	}

	/**
	 * Accessor for the scannerDirection
	 */
	private int getScannerDirection() {
		return _scannerDirection;
	}

	/**
	 * Accessor for the scannerDirection
	 */
	private void setScannerDirection(int scannerDirection) {
		_scannerDirection = scannerDirection;
	}
	
	/**
	 * Accessor for the wasCaught flag.
	 */
	public boolean getWasCaught() {
		return _wasCaught;
	}

	/**
	 * Accessor for the wasCaught flag.
	 */
	private void setWasCaught(boolean wasCaught) {
		_wasCaught = wasCaught;
	}
}