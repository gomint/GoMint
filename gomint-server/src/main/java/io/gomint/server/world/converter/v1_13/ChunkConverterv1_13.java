package io.gomint.server.world.converter.v1_13;

import io.gomint.server.GoMintServer;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.converter.BaseChunkConverter;
import io.gomint.server.world.converter.anvil.tileentity.v1_8.TileEntities;
import io.gomint.taglib.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class ChunkConverterv1_13 extends BaseChunkConverter {

    private BlockDataConverter blockConverter;
    private TileEntities tileEntityConverter;

    public ChunkConverterv1_13(GoMintServer server) {
        super(server);
        this.blockConverter = new BlockDataConverter( getAssets().getConverterData_v1_13() );
    }

    @Override
    public BlockIdentifier[] convertChunkSection(int chunkX, int chunkZ, NBTTagCompound section) {
        List<NBTTagCompound> tagList = ( (List<NBTTagCompound>) ( (List) section.getList("Palette", false) ) );

        List<BlockPalette> blockPalette = new ArrayList<>();

        for (NBTTagCompound tag : tagList) {
            List<String> propertiesList = new ArrayList<>();
            if (tag.containsKey("Properties")) {
                NBTTagCompound properties = tag.getCompound("Properties", false);
                for (Entry<String, Object> entry : properties.entrySet()) {
                    propertiesList.add(entry.getKey() + "=" + entry.getValue().toString());
                }
            }
            blockPalette.add(new BlockPalette(tag.getString("Name", null), propertiesList));
        }

        return new BlockIdentifier[0];
    }

    @Override
    public TileEntity convertTileEntity(NBTTagCompound tileEntity) {
        return null;
    }

}
