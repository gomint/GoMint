package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.world.LevelEvent;
import io.gomint.world.Gamemode;
import io.gomint.world.Particle;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

import java.util.Random;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 122 )
public class DragonEgg extends Block implements io.gomint.world.block.BlockDragonEgg {

    @Override
    public int getBlockId() {
        return 122;
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
    public BlockType getType() {
        return BlockType.DRAGON_EGG;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean interact( Entity entity, BlockFace face, Vector facePos, ItemStack item ) {
        this.teleport();
        return true;
    }

    @Override
    public boolean punch( Entity entity, BlockPosition position, boolean creative ) {
        if( !creative ) {
            this.teleport();
            return true;
        }
        return false;
     }

    private void teleport() {
        BlockPosition pos = this.getLocation().toBlockPosition();
        Random random = new Random();

        for( int i = 0; i < 1000; i++ ) {
            BlockPosition blockPos = pos.add( random.nextInt( 16 ) - random.nextInt( 16 ), random.nextInt( 8 ) - random.nextInt( 8 ), random.nextInt( 16 ) - random.nextInt( 16 ) );

            if( this.world.getBlockAt( blockPos ).getType() == BlockType.AIR ) {
                this.setType( Air.class );
                this.world.getBlockAt( blockPos ).setType( DragonEgg.class );
                this.world.sendLevelEvent( blockPos.toVector(), LevelEvent.DRAGON_EGG_TELEPORT, 0 );

                return;
            }
        }
    }

}

