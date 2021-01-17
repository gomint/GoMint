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
    public long breakTime() {
        return 1500;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.JACK_O_LANTERN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public BlockJackOLantern direction(Direction direction) {
        DIRECTION.state(this, direction);
        return this;
    }

    @Override
    public Direction direction() {
        return DIRECTION.state(this);
    }

}
