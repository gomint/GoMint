package io.gomint.jraknet.datastructures;

import io.gomint.jraknet.EncapsulatedPacket;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public interface OrderingHeap {

	void insert( long weight, EncapsulatedPacket packet );

	boolean isEmpty();

	EncapsulatedPacket peek();

	EncapsulatedPacket poll();

}
