/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.server.network.Protocol;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockRuntimeIDs {

    private static final Logger LOGGER = LoggerFactory.getLogger( BlockRuntimeIDs.class );

    // Release version tables
    private static int[][] BLOCK_DATA_TO_RUNTIME = new int[0][0];
    // private static final long[] RUNTIME_TO_BLOCK_DATA = new long[0];

    // Beta channel tables
    //private static int[][] BLOCK_DATA_TO_RUNTIME_BETA = new int[0][0];

    private static final Long2IntMap RUNTIME_IDS = new Long2IntOpenHashMap();       // HashMaps are fine for multithreaded reading
    //private static final Long2IntMap RUNTIME_IDS_BETA = new Long2IntOpenHashMap();  // HashMaps are fine for multithreaded reading

    static {
        // Get the correct resource
        loadFile( "/temp_runtimeids.json", false );
        // loadFile( "/temp_runtimeids_271.json", true );
    }

    public static void loadFile( String file, boolean beta ) {
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
            if ( beta ) {
                //BLOCK_DATA_TO_RUNTIME_BETA = new int[highestBlockID + 1][];
            } else {
                BLOCK_DATA_TO_RUNTIME = new int[highestBlockID + 1][];
            }

            for ( Object id : runtimeIDs ) {
                JSONObject idObj = (JSONObject) id;
                int blockId = ( (Long) idObj.get( "id" ) ).intValue();
                int dataValue = ( (Long) idObj.get( "data" ) ).intValue();

                int[] dataValues = null;
                if ( beta ) {
                    //dataValues = BLOCK_DATA_TO_RUNTIME_BETA[blockId];
                } else {
                    dataValues = BLOCK_DATA_TO_RUNTIME[blockId];
                }

                if ( dataValues == null ) {
                    dataValues = new int[highestDataValues.get( blockId ) + 1];
                }

                dataValues[dataValue] = ( (Long) idObj.get( "runtimeID" ) ).intValue();

                if ( beta ) {
                    //BLOCK_DATA_TO_RUNTIME_BETA[blockId] = dataValues;
                } else {
                    BLOCK_DATA_TO_RUNTIME[blockId] = dataValues;
                }
            }
        }
    }

    /**
     * Get the correct runtime id for the client. This may also result in blocks being 0'ed due to invalid blocks.
     *
     * @param blockId    which should be converted
     * @param dataValue  which should be converted
     * @param protocolID of the client
     * @return runtime id or 0
     */
    public static int fromLegacy( int blockId, byte dataValue, int protocolID ) {
        // Get lookup array
        int[][] lookup = BLOCK_DATA_TO_RUNTIME;
        if ( protocolID == Protocol.MINECRAFT_PE_BETA_PROTOCOL_VERSION ) {
            //lookup = BLOCK_DATA_TO_RUNTIME_BETA;
        }

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
