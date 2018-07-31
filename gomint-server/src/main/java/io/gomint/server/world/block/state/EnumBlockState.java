/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnumBlockState<E extends Enum<E>> extends BlockState<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger( EnumBlockState.class );

    private E[] enumValues;

    public EnumBlockState( E[] values ) {
        this.enumValues = values;
        this.setState( values[0] );
    }

    @Override
    public byte toData() {
        return (byte) this.getState().ordinal();
    }

    @Override
    public void fromData( byte data ) {
        if ( data >= this.enumValues.length ) {
            this.setState( this.enumValues[0] );
            LOGGER.error( "Incorrect block data value in block", new Exception() );
            return;
        }

        this.setState( this.enumValues[data] );
    }

}
