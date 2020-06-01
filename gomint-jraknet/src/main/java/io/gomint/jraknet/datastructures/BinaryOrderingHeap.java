package io.gomint.jraknet.datastructures;

import io.gomint.jraknet.EncapsulatedPacket;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class BinaryOrderingHeap implements OrderingHeap {

	// Implicitly represented binary heap:
	//  - left element of i-th child: (i << 1)
	//  - right element of i-th child: (i << 1) + 1
	private EncapsulatedPacket[] heap;
	// Current size of heap:
	private int                  size;

	public BinaryOrderingHeap() {
		this( 64 );
	}

	public BinaryOrderingHeap( int capacity ) {
		this.heap = new EncapsulatedPacket[capacity];
	}

	@Override
	public void insert( long weight, EncapsulatedPacket packet ) {
		if ( this.size >= this.heap.length ) {
			this.reallocate( this.size << 1 );
		}

		this.heap[this.size] = packet;
		this.heap[this.size].setWeight( weight );
		this.siftUp( this.size );
		this.size++;
	}

	@Override
	public boolean isEmpty() {
		return ( this.size == 0 );
	}

	@Override
	public EncapsulatedPacket peek() {
		return this.heap[0];
	}

	@Override
	public EncapsulatedPacket poll() {
		// Cache result for later
		EncapsulatedPacket tmp = this.heap[0];

		// Bringt last element to root (needs to be sifted down later on)
		this.size--;
		this.heap[0] = this.heap[this.size];
		this.heap[this.size] = null;

		// Sift down possibly invalid root element
		this.siftDown( 0 );

		// Save memory:
		if ( ( this.size << 2 ) < this.heap.length && this.size > 4 ) {
			this.reallocate( this.size << 1 );
		}

		// Return result
		return tmp;
	}

	private void siftDown( int i ) {
		int child;

		while ( true ) {
			child = ( i << 1 );
			if ( child >= this.size /* Node has no children */ ) {
				return;
			}

			if ( child + 1 < this.size /* Right child does exist */ && this.heap[child].getWeight() > this.heap[child + 1].getWeight() /* Left child is greater than right child */ ) {
				child++;
			}

			if ( this.heap[i].getWeight() > this.heap[child].getWeight() ) {
				// Swap parent and child (parent is larger thus move it to bottom)
				EncapsulatedPacket tmp = this.heap[i];
				this.heap[i] = this.heap[child];
				this.heap[child] = tmp;
				i = child;
			} else {
				return;
			}
		}
	}

	private void reallocate( int capacity ) {
		EncapsulatedPacket[] a = new EncapsulatedPacket[capacity];
		System.arraycopy( this.heap, 0, a, 0, this.size );
		this.heap = a;
	}

	private void siftUp( int i ) {
		while ( true ) {
			if ( i == 0 /* is root */ || this.heap[( i >> 1 )].getWeight() <= this.heap[i].getWeight() /* parent is larger */ ) {
				return;
			}

			// Swap parent and child (child is smaller thus move it to top)
			EncapsulatedPacket tmp = this.heap[i];
			this.heap[i] = this.heap[( i >> 1 )];
			this.heap[( i >> 1 )] = tmp;

			i = ( i >> 1 );
		}
	}

}
