/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.async;

import lombok.Getter;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author geNAZt
 * @version 1.0
 */
public class MultiOutputDelegate<T> implements Delegate<T> {

    @Getter private Queue<Delegate<T>> outputs = new LinkedBlockingQueue<>();

    @Override
    public void invoke( T arg ) {
        while ( !this.outputs.isEmpty() ) {
            this.outputs.poll().invoke( arg );
        }
    }

}
