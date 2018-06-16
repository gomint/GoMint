package io.gomint.server.world.block;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.*;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 10 )
public class FlowingLava extends Liquid implements BlockFlowingLava {

    @Override
    public int getBlockId() {
        return 10;
    }

    @Override
    public long getBreakTime() {
        return 150000;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public int getTickDiff() {
        return 1500; // Depends on the world, in nether its 10 ticks / otherwise its 30
    }

    @Override
    public boolean isFlowing() {
        return true;
    }

    @Override
    public void onEntityStanding( EntityLiving entityLiving ) {
        EntityDamageEvent damageEvent = new EntityDamageEvent( entityLiving, EntityDamageEvent.DamageSource.LAVA, 4.0f );
        entityLiving.damage( damageEvent );
        entityLiving.setBurning( 15, TimeUnit.SECONDS );
        entityLiving.multiplyFallDistance( 0.5f );
    }

    @Override
    protected byte getFlowDecayPerBlock() {
        return 2;
    }

    @Override
    public float getBlastResistance() {
        return 500.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.FLOWING_LAVA;
    }

    @Override
    protected void checkForHarden() {
        // Find the block side on which we collided, can be anything except downwards
        Block colliding = null;
        for ( BlockFace blockFace : BlockFace.values() ) {
            if ( blockFace == BlockFace.DOWN ) {
                continue;
            }

            Block otherBlock = this.getSide( blockFace );
            if ( otherBlock.getType() == BlockType.FLOWING_WATER ) {
                colliding = otherBlock;
                break;
            }
        }

        // Did we find a block we can collide with?
        if ( colliding != null ) {
            if ( this.getBlockData() == 0 ) {
                this.liquidCollide( colliding, Obsidian.class );
            } else if ( this.getBlockData() <= 4 ) {
                this.liquidCollide( colliding, Cobblestone.class );
            }
        }
    }

    @Override
    protected void flowIntoBlock( Block block, int newFlowDecay ) {
        if ( block.getType() == BlockType.FLOWING_WATER ) {
            ( (Liquid) block ).liquidCollide( this, Stone.class );
        } else {
            super.flowIntoBlock( block, newFlowDecay );
        }
    }

}
