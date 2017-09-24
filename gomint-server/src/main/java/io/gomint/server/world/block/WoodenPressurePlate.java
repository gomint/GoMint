package io.gomint.server.world.block;

import io.gomint.math.AxisAlignedBB;
import io.gomint.server.entity.Entity;
import io.gomint.server.registry.RegisterInfo;

import java.util.function.Function;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 72 )
public class WoodenPressurePlate extends Block {

    @Override
    public int getBlockId() {
        return 72;
    }

    @Override
    public long getBreakTime() {
        return 750;
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
    public void stepOn( Entity entity ) {
        // Check for additional temporary data
        Integer amountOfEntitiesOn = this.storeInTemporaryStorage( "amountOfEntitiesOn", new Function<Integer, Integer>() {
            @Override
            public Integer apply( Integer old ) {
                if ( old == null ) return 1;
                return old + 1;
            }
        } );

        if ( amountOfEntitiesOn > 0 && this.getBlockData() != 1 ) {
            this.setBlockData( (byte) 1 );
            this.updateBlock();
        }
    }

    @Override
    public void gotOff( Entity entity ) {
        Integer amountOfEntitiesOn = this.storeInTemporaryStorage( "amountOfEntitiesOn", new Function<Integer, Integer>() {
            @Override
            public Integer apply( Integer old ) {
                if ( old - 1 == 0 ) return null;
                return old - 1;
            }
        } );

        if ( amountOfEntitiesOn == null && this.getBlockData() == 1 ) {
            this.setBlockData( (byte) 0 );
            this.updateBlock();
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return new AxisAlignedBB(
                this.location.getX(),
                this.location.getY(),
                this.location.getZ(),
                this.location.getX() + 1,
                this.location.getY() + 0.1f,
                this.location.getZ() + 1
        );
    }
}
