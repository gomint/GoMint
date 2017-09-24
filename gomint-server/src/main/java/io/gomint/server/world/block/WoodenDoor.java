package io.gomint.server.world.block;

import io.gomint.math.BlockPosition;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 64 )
public class WoodenDoor extends Door {

    @Override
    public int getBlockId() {
        return 64;
    }

    @Override
    public void afterPlacement() {
        // Set the top part
        Block above = location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.UP ) );
        above.setType( WoodenDoor.class, (byte) 0x08 );
    }

}
