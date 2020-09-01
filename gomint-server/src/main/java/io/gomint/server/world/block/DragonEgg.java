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
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 45.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DRAGON_EGG;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {
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
    public void teleport( BlockPosition blockPosition ) {
        this.setBlockType( Air.class );
        this.world.getBlockAt( blockPosition ).setBlockType( DragonEgg.class );
        this.world.sendLevelEvent( blockPosition.toVector(), LevelEvent.DRAGON_EGG_TELEPORT, 0 );
    }

    @Override
    public void teleport() {
        BlockPosition pos = this.getLocation().toBlockPosition();

        for ( int i = 0; i < 1000; i++ ) {
            BlockPosition blockPos = pos.add( ThreadLocalRandom.current().nextInt( 16 ) - ThreadLocalRandom.current().nextInt( 16 ),
                ThreadLocalRandom.current().nextInt( 8 ) - ThreadLocalRandom.current().nextInt( 8 ),
                ThreadLocalRandom.current().nextInt( 16 ) - ThreadLocalRandom.current().nextInt( 16 ) );

            if ( this.world.getBlockAt( blockPos ).getBlockType() == BlockType.AIR ) {
                this.teleport( blockPos );
                return;
            }
        }
    }

}

