package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.LevelEvent;
import io.gomint.util.random.FastRandom;
import io.gomint.world.Gamemode;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:dragon_egg" )
public class DragonEgg extends Block implements io.gomint.world.block.BlockDragonEgg {

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
    public boolean punch( EntityPlayer player, BlockPosition position ) {
        if ( player.getGamemode() != Gamemode.CREATIVE ) {
            this.teleport();
            return true;
        }

        return false;
    }

    @Override
    public void teleport() {
        BlockPosition pos = this.getLocation().toBlockPosition();
        FastRandom random = FastRandom.current();

        for ( int i = 0; i < 1000; i++ ) {
            BlockPosition blockPos = pos.add( random.nextInt( 16 ) - random.nextInt( 16 ), random.nextInt( 8 ) - random.nextInt( 8 ), random.nextInt( 16 ) - random.nextInt( 16 ) );

            if ( this.world.getBlockAt( blockPos ).getType() == BlockType.AIR ) {
                this.setType( Air.class );
                this.world.getBlockAt( blockPos ).setType( DragonEgg.class );
                this.world.sendLevelEvent( blockPos.toVector(), LevelEvent.DRAGON_EGG_TELEPORT, 0 );

                return;
            }
        }
    }

}

