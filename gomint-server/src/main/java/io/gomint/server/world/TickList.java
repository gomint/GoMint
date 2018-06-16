/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.math.BlockPosition;
import io.gomint.server.util.collection.LongList;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TickList {

    private LongElement head;

    /**
     * Add a new Element to the tasklist
     *
     * @param key     which should be used to sort the element
     * @param element which should be stored
     */
    public synchronized void add( long key, BlockPosition element ) {
        // Check if we have a head state
        if ( this.head == null ) {
            this.head = new LongElement( key, null, new LinkedList<BlockPosition>() {{
                add( element );
            }} );
        } else {
            LongElement longElement = this.head;
            LongElement previousLongElement = null;

            // Check until we got a element with a key higher than us or we reached the end
            while ( longElement != null && longElement.getKey() < key ) {
                previousLongElement = longElement;
                longElement = longElement.getNext();
            }

            // We are at the end of the chain
            if ( longElement == null ) {
                previousLongElement.setNext( new LongElement( key, null, new LinkedList<BlockPosition>() {{
                    add( element );
                }} ) );
            } else {
                // Check if we need to insert a element
                if ( longElement.getKey() != key ) {
                    LongElement newLongElement = new LongElement( key, longElement, new LinkedList<BlockPosition>() {{
                        add( element );
                    }} );

                    if ( previousLongElement != null ) {
                        previousLongElement.setNext( newLongElement );
                    } else {
                        // We added a new head
                        this.head = newLongElement;
                    }
                } else {
                    // We already have this key, append task
                    longElement.getValues().add( element );
                }
            }
        }
    }

    /**
     * @return
     */
    public synchronized long getNextTaskTime() {
        return this.head != null ? this.head.getKey() : Long.MAX_VALUE;
    }

    /**
     * Gets the next element in this List. The Element will be removed from the list
     *
     * @return next element out of this list or null when there is none
     */
    public synchronized BlockPosition getNextElement() {
        // There is nothing we can reach
        if ( this.head == null ) return null;

        // Check if we have a head node
        while ( this.head != null && this.head.getValues().size() == 0 ) {
            // This head is empty, remove it
            this.head = this.head.getNext();
        }

        // This list has reached its end
        if ( this.head == null ) return null;

        // Extract the element
        BlockPosition element = this.head.getValues().poll();
        while ( this.head.getValues().size() == 0 ) {
            this.head = this.head.getNext();
            if ( this.head == null ) break;
        }

        return element;
    }

    public synchronized int size( long key ) {
        LongElement element = this.head;
        if ( element == null ) {
            return 0;
        }

        do {
            if ( element.getKey() == key ) {
                return element.getValues().size();
            }
        } while ( ( element = element.getNext() ) != null );

        return 0;
    }

    public synchronized boolean contains( BlockPosition hash ) {
        LongElement element = this.head;
        if ( element == null ) {
            return false;
        }

        do {
            if ( element.getValues().contains( hash ) ) {
                return true;
            }
        } while ( ( element = element.getNext() ) != null );

        return false;
    }

    @AllArgsConstructor
    @Data
    private final class LongElement {
        private long key;
        private LongElement next;
        private Queue<BlockPosition> values;
    }

}
