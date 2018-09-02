/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.jraknet.PacketBuffer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockRuntimeIDs {

    private static final Logger LOGGER = LoggerFactory.getLogger( BlockRuntimeIDs.class );

    // Release version tables
    private static int[][] BLOCK_DATA_TO_RUNTIME = new int[0][0];

    //
    private static AtomicInteger RUNTIME_ID = new AtomicInteger( 0 );

    // Cached packet streams
    private static byte[] START_GAME_BUFFER;

    static {
        // Get the correct resource
        loadFile( "/temp_runtimeids.json" );
    }

    public static void loadFile( String file ) {
        InputStream inputStream = BlockRuntimeIDs.class.getResourceAsStream( file );
        if ( inputStream == null ) {
            try {
                inputStream = new FileInputStream( "gomint-server/src/main/resources" + file );
            } catch ( FileNotFoundException e ) {
                e.printStackTrace();
            }
        }

        JSONParser parser = new JSONParser();
        JSONArray runtimeIDs = null;

        try ( InputStreamReader reader = new InputStreamReader( inputStream ) ) {
            runtimeIDs = (JSONArray) parser.parse( reader );
        } catch ( IOException | ParseException e ) {
            e.printStackTrace();
        }

        // Read in json data
        if ( runtimeIDs != null ) {
            PacketBuffer buffer = new PacketBuffer( 64 );

            int highestBlockID = -1;
            Map<Integer, Integer> highestDataValues = new HashMap<>();

            for ( Object id : runtimeIDs ) {
                JSONObject idObj = (JSONObject) id;
                int blockId = ( (Long) idObj.get( "id" ) ).intValue();
                int dataValue = ( (Long) idObj.get( "data" ) ).intValue();

                if ( highestBlockID < blockId ) {
                    highestBlockID = blockId;
                }

                Integer knownData = highestDataValues.get( blockId );
                if ( knownData == null || dataValue > knownData ) {
                    highestDataValues.put( blockId, dataValue );
                }
            }

            // Init array
            BLOCK_DATA_TO_RUNTIME = new int[highestBlockID + 1][];
            buffer.writeUnsignedVarInt( runtimeIDs.size() );

            for ( Object id : runtimeIDs ) {
                JSONObject idObj = (JSONObject) id;
                int blockId = ( (Long) idObj.get( "id" ) ).intValue();
                int dataValue = ( (Long) idObj.get( "data" ) ).intValue();

                int[] dataValues = BLOCK_DATA_TO_RUNTIME[blockId];
                if ( dataValues == null ) {
                    dataValues = new int[highestDataValues.get( blockId ) + 1];
                }

                Long overrideId = (Long) idObj.get( "runtimeID" );


                dataValues[dataValue] = overrideId != null ? overrideId.intValue() : RUNTIME_ID.getAndIncrement();
                BLOCK_DATA_TO_RUNTIME[blockId] = dataValues;
                buffer.writeString( (String) idObj.get( "name" ) );
                buffer.writeLShort( (short) dataValue );
            }

            START_GAME_BUFFER = Arrays.copyOf( buffer.getBuffer(), buffer.getPosition() );
        }
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
    public static int fromLegacy( int blockId, byte dataValue ) {
        // Get lookup array
        int[][] lookup = BLOCK_DATA_TO_RUNTIME;
<<<<<<< HEAD
        if ( protocolID >= Protocol.MINECRAFT_PE_NEXT_STABLE_PROTOCOL_VERSION ) {
            lookup = BLOCK_DATA_TO_RUNTIME_BETA;
        }
=======
>>>>>>> 893927c5c519bac56ae7d9e5f6a224f821340990

        // We first lookup the wanted values
        int runtimeID = lookup( blockId, dataValue, lookup );
        if ( runtimeID == -1 ) { // Unknown block => return air
            LOGGER.warn( "Unknown blockId and dataValue combination: {}:{}. Be sure your worlds are not corrupted!", blockId, dataValue );
            return 0;
        } else if ( runtimeID == -2 ) { // Unknown data => return lookup with 0 data value
            LOGGER.warn( "Unknown blockId and dataValue combination: {}:{}. Be sure your worlds are not corrupted!", blockId, dataValue );
            return lookup( blockId, (byte) 0, lookup );
        }

        return runtimeID;
    }

    private static int lookup( int blockId, byte dataValue, int[][] lookup ) {
        // First we need to check size of the array
        if ( blockId >= lookup.length ) {
            return -1;
        }

        // Get the data values of this block
        int[] dataValues = lookup[blockId];
        if ( dataValue >= dataValues.length ) {
            return -2;
        }

        return dataValues[dataValue];
    }

}
