/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.collection;

/**
 * @author geNAZt
 * @version 1.0
 * @param <E> type of object inside the array
 */
public class FixedIndexResizableArray<E> {

    private E[] objects;

    public FixedIndexResizableArray() {
        this.objects = (E[]) new Object[128];
    }

    public E set( int index, E object ) {
        // Is the index inside the length?
        while ( index >= this.objects.length ) {
            // Simply add 128 elements
            E[] temp = (E[]) new Object[this.objects.length + 128];
            System.arraycopy( this.objects, 0, temp, 0, this.objects.length );
            this.objects = temp;
        }

        E temp = this.objects[index];
        this.objects[index] = object;
        return temp;
    }

    public E get( int index ) {
        if ( index >= this.objects.length ) {
            return null;
        }

        return this.objects[index];
    }

}
