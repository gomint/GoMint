package io.gomint.jraknet.datastructures;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class TriadRange {

	private int min;
	private int max;

	public TriadRange( int min, int max ) {
		this.min = min;
		this.max = max;
	}

	public int getMin() {
		return this.min;
	}

	public void setMin( int min ) {
		this.min = min;
	}

	public int getMax() {
		return this.max;
	}

	public void setMax( int max ) {
		this.max = max;
	}

}
