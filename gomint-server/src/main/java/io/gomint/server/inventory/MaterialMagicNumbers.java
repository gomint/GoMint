package io.gomint.server.inventory;

import io.gomint.server.world.BlockRuntimeIDs;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author geNAZt
 * @version 1.0
 */
public class MaterialMagicNumbers {

    private static final Object2IntMap<String> NEW_ID_MAPPING = new Object2IntOpenHashMap<>();
    private static final Int2ObjectMap<String> OLD_ID_MAPPING = new Int2ObjectOpenHashMap<>();

    static {
        InputStream inputStream = BlockRuntimeIDs.class.getResourceAsStream( "/temp_runtimeids_282.json" );
        if ( inputStream == null ) {
            try {
                inputStream = new FileInputStream( "gomint-server/src/main/resources/temp_runtimeids_282.json" );
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
            for ( Object id : runtimeIDs ) {
                JSONObject idObj = (JSONObject) id;
                int blockId = ( (Long) idObj.get( "id" ) ).intValue();
                String dataId = ( (String) idObj.get( "name" ) );

                register( blockId, dataId );
            }
        }
    }

    public static void register( int id, String newId ) {
        NEW_ID_MAPPING.put( newId, id );
        OLD_ID_MAPPING.put( id, newId );
    }

    public static int valueOfWithId( String newId ) {
        return NEW_ID_MAPPING.getOrDefault( newId, -1 );
    }

    public static String newIdFromValue( int id ) {
        return OLD_ID_MAPPING.getOrDefault( id, "minecraft:air" );
    }

}
