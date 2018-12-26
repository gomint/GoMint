/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.util.BlockIdentifier;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
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

    public static void init( List<BlockIdentifier> blockPalette ) {
        PacketBuffer buffer = new PacketBuffer( 64 );

        for ( BlockIdentifier identifier : blockPalette ) {
            int runtime = RUNTIME_ID.getAndIncrement();

            BLOCK_TO_RUNTIME.put( identifier.longHashCode(), runtime );
            RUNTIME_TO_BLOCK.put( runtime, identifier );
        }

        buffer.writeUnsignedVarInt( blockPalette.size() );
        for ( BlockIdentifier identifier : blockPalette ) {
            buffer.writeString( identifier.getBlockId() );
            buffer.writeLShort( identifier.getData() );
        }

        START_GAME_BUFFER = Arrays.copyOf( buffer.getBuffer(), buffer.getPosition() );
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
    public static int from( String blockId, short dataValue ) {
        // Get lookup array
        Long2IntMap lookup = BLOCK_TO_RUNTIME;

        // We first lookup the wanted values
        int runtimeID = lookup( blockId, dataValue, lookup );
        if ( runtimeID == -1 ) { // Unknown data => return lookup with 0 data value
            runtimeID = lookup( blockId, (short) 0, lookup );
            if ( runtimeID == -1 ) { // Unknown block => return air
                LOGGER.warn( "Unknown blockId and dataValue combination: {}:{}. Be sure your worlds are not corrupted!", blockId, dataValue );
                return lookup( "minecraft:air", (short) 0, lookup );
            }

            LOGGER.warn( "Unknown blockId and dataValue combination: {}:{}. Be sure your worlds are not corrupted!", blockId, dataValue );
            return runtimeID;
        }

        return runtimeID;
    }

    public static BlockIdentifier toBlockIdentifier( int runtimeId ) {
        return RUNTIME_TO_BLOCK.get( runtimeId );
    }

    private static int lookup( String blockId, short dataValue, Long2IntMap lookup ) {
        if ( lookup == null ) {
            System.out.println( "LOOKUP IS NULL" );
        }

        if ( blockId == null ) {
            System.out.println( "BLOCKID IS NULL" );
        }

        return lookup.get( (long) blockId.hashCode() << 32 | dataValue );
    }

}
