package io.gomint.server.world.converter.v1_13;

import io.gomint.server.util.BlockIdentifier;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.List;

public class BlockDataConverter {

    private final Object2ObjectMap<String, Converter> converters = new Object2ObjectOpenHashMap<>();

    public BlockDataConverter(List<NBTTagCompound> converterData ) {
        for ( NBTTagCompound compound : converterData ) {
            String blockData = compound.getString("blockdata", null);
            String newId = compound.getString("ni", null);
            Byte newData = compound.getByte("nd", (byte) -1);
            this.converters.put( blockData, data -> new BlockIdentifier( newId, newData ) );
        }
    }

    public BlockIdentifier convert( String blockData ) {
        Converter converter = this.converters.get( blockData );
        if ( converter != null ) {
            return converter.convert( blockData );
        }
        return null;
    }

}
