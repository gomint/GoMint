package io.gomint.server.world.converter.anvil;

import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.util.Pair;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.ints.Int2ByteMap;
import it.unimi.dsi.fastutil.ints.Int2ByteOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockConverter {

    private final Int2ObjectMap<Converter> converters = new Int2ObjectOpenHashMap<>();
    private final Int2ByteMap metaconverts = new Int2ByteOpenHashMap();

    public BlockConverter( List<NBTTagCompound> converterData ) {
        for ( NBTTagCompound compound : converterData ) {
            short oldId = compound.getShort( "oi", (short) 0 );
            byte oldMeta = compound.getByte( "om", (byte) -1 );
            String newId = compound.getString( "ni", null );
            byte newMeta = compound.getByte( "nm", (byte) -1 );

            if ( oldMeta == -1 && newMeta == -1 ) {
                this.converters.put( oldId, ( blockId, metaData ) -> new BlockIdentifier( newId, null, (short) metaData) );
            } else {
                // Check if we already have a converter
                if ( !this.converters.containsKey( oldId ) ) {
                    this.converters.put( oldId, ( blockId, metaData ) -> new BlockIdentifier( newId, null, (short) metaconverts.get( blockId << 16 | ( metaData & 0xFF ) )) );
                }

                this.metaconverts.put( oldId << 16 | ( oldMeta & 0xFF ), newMeta );
            }
        }
    }

    public BlockIdentifier convert( int blockId, byte data ) {
        Converter converter = this.converters.get( blockId );
        if ( converter != null ) {
            return converter.convert( blockId, data );
        }

        return null;
    }

}
