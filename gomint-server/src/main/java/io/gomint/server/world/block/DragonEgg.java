package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.LevelEvent;
import io.gomint.world.Gamemode;
import io.gomint.world.block.BlockDragonEgg;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockType;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:dragon_egg" )
public class DragonEgg extends Block implements BlockDragonEgg {

    @Override
    public String getBlockId() {
        return "minecraft:dragon_egg";
    }

    @Override
    public long breakTime() {
        return 4500;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 45.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.DRAGON_EGG;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack<?> item ) {
        this.teleport();
        return true;
    }

    @Override
    public boolean punch( EntityPlayer player ) {
        if ( player.getGamemode() != Gamemode.CREATIVE ) {
            this.teleport();
            return true;
        }

        return false;
    }

    @Override
    public BlockDragonEgg teleport( BlockPosition blockPosition ) {
        this.blockType( Air.class );
        this.world.blockAt( blockPosition ).blockType( DragonEgg.class );
        this.world.sendLevelEvent( blockPosition.toVector(), LevelEvent.DRAGON_EGG_TELEPORT, 0 );
        return this;
    }

    @Override
    public BlockDragonEgg teleport() {
        for ( int i = 0; i < 1000; i++ ) {
            BlockPosition blockPos = this.position.add( ThreadLocalRandom.current().nextInt( 16 ) - ThreadLocalRandom.current().nextInt( 16 ),
                ThreadLocalRandom.current().nextInt( 8 ) - ThreadLocalRandom.current().nextInt( 8 ),
                ThreadLocalRandom.current().nextInt( 16 ) - ThreadLocalRandom.current().nextInt( 16 ) );

            if ( this.world.blockAt( blockPos ).blockType() == BlockType.AIR ) {
                return this.teleport( blockPos );
            }
        }

        return this;
    }

}

