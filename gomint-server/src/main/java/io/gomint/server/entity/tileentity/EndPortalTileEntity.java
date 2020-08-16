package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "EndPortal")
public class EndPortalTileEntity extends ContainerTileEntity implements InventoryHolder {

    /**
     * Construct new tile entity
     *
     * @param block of the tile entity
     */
    public EndPortalTileEntity(Block block, Items items) {
        super( block, items );
    }

    @Override
    public void toCompound( NBTTagCompound compound, SerializationReason reason ) {
        super.toCompound( compound, reason );

        compound.addValue( "id", "EndPortal" );
    }

}
