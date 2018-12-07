/**
 * 
 */
package buri.aoc.y18.d07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Uri!
 */
public class Step implements Comparable<Step> {

	private String _name;
	private List<String> _previous;
	private List<String> _next;
	
	public Step(String name) {
		_name = name;
		_previous = new ArrayList<>();
		_next = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return (getPrevious() + " -> " + getName() + " -> " + getNext());
	}	
	
	@Override
	public int compareTo(Step o) {
		return (getName().compareTo(o.getName()));
	}
	
	public void addPrevious(String name) {
		if (!getPrevious().contains(name)) {
			getPrevious().add(name);
			Collections.sort(getPrevious());
		}
	}
	
	public void addNext(String name) {
		if (!getNext().contains(name)) {
			getNext().add(name);
			Collections.sort(getNext());
		}
	}
	
	/**
	 * Accessor for the name
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Accessor for the previous
	 */
	public List<String> getPrevious() {
		return _previous;
	}
	
	/**
	 * Accessor for the next
	 */
	public List<String> getNext() {
		return _next;
	}
}