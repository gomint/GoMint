/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.generator;

/**
 * @author generated
 * @version 2.0
 */
public class LightWeightedPressurePlateGenerator implements BlockGenerator {

    public io.gomint.server.world.block.Block generate( int blockId, byte blockData, byte skyLightLevel, byte blockLightLevel, io.gomint.server.entity.tileentity.TileEntity tileEntity, io.gomint.math.Location location, int layer ) {
        io.gomint.server.world.block.Block block = new io.gomint.server.world.block.LightWeightedPressurePlate();
        block.setData( blockId, blockData, tileEntity, (io.gomint.server.world.WorldAdapter) location.getWorld(), location, layer, skyLightLevel, blockLightLevel );
        return block;
    }

    public io.gomint.server.world.block.Block generate() {
        return new io.gomint.server.world.block.LightWeightedPressurePlate();
    }

}
