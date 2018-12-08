package buri.aoc.y18.d08;

/**
 * Encapsulation of the data needed to recursively generate the tree of nodes.
 * 
 * Workaround for the fact that Java can only return 1 value from a method call.
 *  
 * @author Brian Uri!
 */
public class RecursionData {

	private int _nextIndex = -1;
	private Node _generatedNode;
	
	/**
	 * Constructor
	 */
	public RecursionData(int nextIndex, Node generatedNode) {
		_nextIndex = nextIndex;
		_generatedNode = generatedNode;		
	}

	/**
	 * Accessor for the nextIndex
	 */
	public int getNextIndex() {
		return _nextIndex;
	}

	/**
	 * Accessor for the generatedNode
	 */
	public Node getGeneratedNode() {
		return _generatedNode;
	}
}
