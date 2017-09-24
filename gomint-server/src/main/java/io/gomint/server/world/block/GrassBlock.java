package io.gomint.server.world.block;

import io.gomint.math.BlockPosition;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.UpdateReason;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 2 )
public class GrassBlock extends Block {

    @Override
    public int getBlockId() {
        return 2;
    }

    @Override
    public long update( UpdateReason updateReason, long currentTimeMS, float dT ) {
        Block block = world.getBlockAt( location.toBlockPosition().add( BlockPosition.UP ) );
        byte lightLevel = block.getSkyLightLevel();

        if ( lightLevel >= 9 ) {

        }

        return -1;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

}
