package io.gomint.server.world.block;

import io.gomint.world.block.BlockInvertedDaylightSensor;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:daylight_detector_inverted" )
public class InvertedDaylightSensor extends Block implements BlockInvertedDaylightSensor {

    @Override
    public String blockId() {
        return "minecraft:daylight_detector_inverted";
    }

    @Override
    public long breakTime() {
        return 300;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.INVERTED_DAYLIGHT_SENSOR;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
