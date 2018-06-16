package io.gomint.server.world.anvil.tileentity.v1_8;

import io.gomint.server.entity.tileentity.EndPortalTileEntity;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EndPortalConverter extends BasisConverter<EndPortalTileEntity> {

    /**
     * Construct new converter
     *
     * @param worldAdapter for which we construct
     */
    public EndPortalConverter( WorldAdapter worldAdapter ) {
        super( worldAdapter );
    }

    @Override
    public EndPortalTileEntity readFrom( NBTTagCompound compound ) {
        return new EndPortalTileEntity( getPosition( compound ) );
    }

}
