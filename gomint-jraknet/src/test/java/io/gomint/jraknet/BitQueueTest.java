package io.gomint.jraknet;

import io.gomint.jraknet.datastructures.BitQueue;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class BitQueueTest {

	@Test
	public void testMisalignedReallocation() {
		BitQueue queue;
		Random random = new Random();

		for ( int i = 1; i < 8; ++i ) {
			queue = new BitQueue( 16 );

			// Misalign the bit queue's internal tail pointer:
			for ( int j = 0; j < i; ++j ) {
				queue.add( true );
				queue.poll();
			}

			Queue<Boolean> expected = new LinkedList<>();
			for ( int j = 0; j < 17; ++j ) {
				boolean result = random.nextBoolean();
				queue.add( result );
				expected.add( result );
			}

			assertEquals( expected.size(), queue.size() );

			while ( !queue.isEmpty() ) {
				assertEquals( expected.poll(), queue.poll() );
			}
		}
	}

	@Test
	public void testAlignedReallocation() {
		BitQueue queue = new BitQueue( 16 );
		Random random = new Random();
		Queue<Boolean> expected = new LinkedList<>();

		for ( int i = 0; i < 17; ++i ) {
			boolean result = random.nextBoolean();
			queue.add( result );
			expected.add( result );
		}

		assertEquals( expected.size(), queue.size() );

		while ( !queue.isEmpty() ) {
			assertEquals( expected.poll(), queue.poll() );
		}
	}

}
