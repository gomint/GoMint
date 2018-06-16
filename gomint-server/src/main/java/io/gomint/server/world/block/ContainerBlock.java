package io.gomint.server.world.block;

import io.gomint.server.entity.tileentity.ContainerTileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ContainerBlock extends Block {

    public void setCustomName( String customName ) {
        ContainerTileEntity containerTileEntity = this.getTileEntity();
        if ( containerTileEntity != null ) {
            containerTileEntity.setCustomName( customName );
            this.updateBlock();
        }
    }

    public String getCustomName() {
        ContainerTileEntity containerTileEntity = this.getTileEntity();
        if ( containerTileEntity != null ) {
            return containerTileEntity.getCustomName();
        }

        return null;
    }

}
