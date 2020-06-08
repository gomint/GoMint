/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.server.util.BlockIdentifier;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.taglib.NBTWriter;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockRuntimeIDs {

    private static final Logger LOGGER = LoggerFactory.getLogger( BlockRuntimeIDs.class );

    private static final Long2IntMap BLOCK_TO_RUNTIME = new Long2IntOpenHashMap();
    private static Int2ObjectMap<BlockIdentifier> RUNTIME_TO_BLOCK = new Int2ObjectOpenHashMap<>();

    //
    private static AtomicInteger RUNTIME_ID = new AtomicInteger( 0 );

    // Cached packet streams
    private static byte[] START_GAME_BUFFER;

    public static void init( List<BlockIdentifier> blockPalette ) throws IOException {
        List<Object> compounds = new ArrayList<>();

        ByteArrayOutputStream data = new ByteArrayOutputStream();
        NBTWriter writer = new NBTWriter(data, ByteOrder.LITTLE_ENDIAN);
        writer.setUseVarint(true);

        for ( BlockIdentifier identifier : blockPalette ) {
            int runtime = RUNTIME_ID.getAndIncrement();

            BLOCK_TO_RUNTIME.put( identifier.longHashCode(), runtime );
            RUNTIME_TO_BLOCK.put( runtime, identifier );

            NBTTagCompound compound = new NBTTagCompound("");
            NBTTagCompound block = new NBTTagCompound("block");
            NBTTagCompound states = new NBTTagCompound("states");

            for (Map.Entry<String, Object> entry : identifier.getStates(false).entrySet()) {
                Class<?> typ = entry.getValue().getClass();
                if (typ.equals(Byte.class)) {
                    states.addValue(entry.getKey(), (byte) entry.getValue());
                } else if (typ.equals(Integer.class)) {
                    states.addValue(entry.getKey(), (int) entry.getValue());
                } else if (typ.equals(Short.class)) {
                    states.addValue(entry.getKey(), (short) entry.getValue());
                } else if (typ.equals(String.class)) {
                    states.addValue(entry.getKey(), (String) entry.getValue());
                } else {
                    LOGGER.warn("Unknown state type: {}", typ.getName());
                }
            }

            block.addValue("name", identifier.getBlockId());
            block.addValue("states", states);
            compound.addValue("block", block);
            compounds.add(compound);
        }

        writer.write(compounds);

        START_GAME_BUFFER = data.toByteArray();
        BLOCK_TO_RUNTIME.defaultReturnValue( -1 );
    }

    /**
     * Get the cached view for the start game packet
     *
     * @return correct cached view
     */
    public static byte[] getPacketCache() {
        return START_GAME_BUFFER;
    }

    /**
     * Get the correct runtime id for the client. This may also result in blocks being 0'ed due to invalid blocks.
     *
     * @param blockId   which should be converted
     * @param dataValue which should be converted
     * @return runtime id or 0
     */
    public static int from(String blockId, SortedMap<String, Object> states, short dataValue ) {
        // Get lookup array
        Long2IntMap lookup = BLOCK_TO_RUNTIME;

        // We first lookup the wanted values
        int runtimeID = lookup( blockId, states, dataValue, lookup );
        if ( runtimeID == -1 ) { // Unknown data => return lookup with 0 data value
            runtimeID = lookup( blockId, null, (short) 0, lookup );
            if ( runtimeID == -1 ) { // Unknown block => return air
                LOGGER.warn( "Unknown blockId and dataValue combination: {}:{}. Be sure your worlds are not corrupted!", blockId, dataValue );
                return lookup( "minecraft:air", null, (short) 0, lookup );
            }

            LOGGER.warn( "Unknown blockId and dataValue combination: {}:{}. Be sure your worlds are not corrupted!", blockId, dataValue );
            return runtimeID;
        }

        return runtimeID;
    }

    public static BlockIdentifier toBlockIdentifier( int runtimeId ) {
        return RUNTIME_TO_BLOCK.get( runtimeId );
    }

    private static int lookup( String blockId, SortedMap<String, Object> states, short dataValue, Long2IntMap lookup ) {
        long hash = 0;
        if (states != null) {
            hash = (long) blockId.hashCode() << 32 | states.hashCode();
        } else {
            hash = (long) blockId.hashCode() << 32 | dataValue;
        }

        return lookup.get( hash );
    }

}
