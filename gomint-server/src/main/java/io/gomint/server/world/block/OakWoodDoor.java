package io.gomint.server.world.block;

import io.gomint.world.block.BlockOakWoodDoor;
import io.gomint.world.block.BlockType;

import io.gomint.math.BlockPosition;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 64 )
public class OakWoodDoor extends Door implements BlockOakWoodDoor {

    @Override
    public int getBlockId() {
        return 64;
    }

    @Override
    public void afterPlacement() {
        // Set the top part
        Block above = location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.UP ) );
        Door door = above.setType( OakWoodDoor.class );
        door.setTopPart();
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.WOODEN_DOOR;
    }

}
