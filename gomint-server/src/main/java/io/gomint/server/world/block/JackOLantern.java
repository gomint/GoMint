package io.gomint.server.world.block;

import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.world.block.BlockJackOLantern;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Direction;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:lit_pumpkin" )
public class JackOLantern extends Block implements BlockJackOLantern {

    private static final DirectionBlockState DIRECTION = new DirectionBlockState(() -> new String[]{"direction"});

    @Override
    public long getBreakTime() {
        return 1500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.JACK_O_LANTERN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public void setDirection(Direction direction) {
        DIRECTION.setState(this, direction);
    }

    @Override
    public Direction getDirection() {
        return DIRECTION.getState(this);
    }

}
