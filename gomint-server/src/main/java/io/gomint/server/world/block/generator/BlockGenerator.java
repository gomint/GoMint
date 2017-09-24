package io.gomint.server.world.block.generator;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 */
public interface BlockGenerator {

    <T> T generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location );

    <T> T generate();

}
