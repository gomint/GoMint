package io.gomint.server.world.block;

import io.gomint.server.entity.tileentity.ContainerTileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ContainerBlock<B> extends Block {

    public B customName(String customName ) {
        ContainerTileEntity containerTileEntity = this.tileEntity();
        if ( containerTileEntity != null ) {
            containerTileEntity.setCustomName( customName );
            this.updateBlock();
        }

        return (B) this;
    }

    public String customName() {
        ContainerTileEntity containerTileEntity = this.tileEntity();
        if ( containerTileEntity != null ) {
            return containerTileEntity.getCustomName();
        }

        return null;
    }

}
