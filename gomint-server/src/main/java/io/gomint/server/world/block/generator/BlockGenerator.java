package io.gomint.server.world.block.generator;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.world.block.Block;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BlockGenerator {

    <T extends Block> T generate( int blockId, byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location, int layer );

    <T extends Block> T generate();

}
