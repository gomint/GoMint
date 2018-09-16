package io.gomint.server.world.converter;

import io.gomint.server.GoMintServer;
import io.gomint.server.assets.AssetsLibrary;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseChunkConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger( BaseChunkConverter.class );

    private GoMintServer server;

    public BaseChunkConverter( GoMintServer server ){
        this.server = server;
    }

    public abstract BlockIdentifier[] convertChunkSection(int chunkX, int chunkZ, NBTTagCompound section);
    public abstract TileEntity convertTileEntity(NBTTagCompound tileEntity);

    public AssetsLibrary getAssets(){
        return this.server.getAssets();
    }

    public Logger getLogger(){
        return LOGGER;
    }
}
