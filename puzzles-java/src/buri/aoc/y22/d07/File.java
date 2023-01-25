package buri.aoc.y22.d07;

import java.util.ArrayList;
import java.util.List;

/**
 * Data class for a file/directory.
 *
 * @author Brian Uri!
 */
public class File {

	private final String _name;
	private final boolean _directory;
	private long _size;
	private File _parent;
	private final List<File> _files = new ArrayList<>();

	/**
	 * Constructor for a directory
	 */
	public File(String name) {
		_directory = true;
		_name = name;
	}

	/**
	 * Constructor for a file
	 */
	public File(String name, long size) {
		_directory = false;
		_name = name;
		_size = size;
	}

	@Override
	public String toString() {
		return (getName() + "[" + isDirectory() + ", " + getSize() + "]");
	}

	/**
	 * Accessor for the directory flag
	 */
	public boolean isDirectory() {
		return _directory;
	}
	/**
	 * Accessor for the name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the size
	 */
	public long getSize() {
		long total = _size;
		if (isDirectory()) {
			for (File file : getFiles()) {
				total += file.getSize();
			}
		}
		return (total);
	}

	/**
	 * Adds a file to this directory
	 */
	public void addFile(File file) {
		if (isDirectory()) {
			getFiles().add(file);
			file.setParent(this);
		}
	}

	/**
	 * Accessor for this file's parent
	 */
	public File getParent() {
		return _parent;
	}

	/**
	 * Accessor for this file's parent
	 */
	public void setParent(File parent) {
		_parent = parent;
	}

	/**
	 * Accessor for the list of nested files / directories
	 */
	private List<File> getFiles() {
		return _files;
	}
}