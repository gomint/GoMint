/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BooleanBlockState extends BlockState<Boolean> {

    @Override
    public byte toData() {
        return (byte) ( this.getState() ? 1 : 0 );
    }

    @Override
    public void fromData( byte data ) {
        this.setState( data == 1 );
    }

}
