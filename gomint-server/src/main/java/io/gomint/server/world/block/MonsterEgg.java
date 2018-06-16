package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 97 )
public class MonsterEgg extends Block implements io.gomint.world.block.BlockMonsterEgg {

    @Override
    public int getBlockId() {
        return 97;
    }

    @Override
    public long getBreakTime() {
        return 1125;
    }

    @Override
    public float getBlastResistance() {
        return 3.75f;
    }

    @Override
    public BlockType getType() {
        return BlockType.MONSTER_EGG;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
