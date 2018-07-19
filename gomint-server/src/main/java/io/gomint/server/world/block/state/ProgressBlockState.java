/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.math.MathUtils;

import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ProgressBlockState extends BlockState<Float> {

    private Consumer<Void> maxedProgressConsumer;
    private int max;
    private float step;

    public ProgressBlockState( int max, Consumer<Void> maxedProgressConsumer ) {
        this.step = 1f / max;
        this.maxedProgressConsumer = maxedProgressConsumer;
        this.max = max;
        this.setState( 0f );
    }

    public boolean progress() {
        this.setState( this.getState() + this.step );
        if ( 1f - this.getState() <= MathUtils.EPSILON ) {
            this.maxedProgressConsumer.accept( null );
            return false;
        }

        return true;
    }

    @Override
    public byte toData() {
        return (byte) Math.round( this.getState() * this.max );
    }

    @Override
    public void fromData( byte data ) {
        this.setState( data * this.step );
    }

}
