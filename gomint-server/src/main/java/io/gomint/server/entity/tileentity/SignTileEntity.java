/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import com.google.common.base.Joiner;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SignTileEntity extends TileEntity {

    private static final JSONParser JSON_PARSER = new JSONParser();
    private List<String> lines = new ArrayList<>( 4 );

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    public SignTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );

        if ( tagCompound.containsKey( "Text" ) ) {
            String text = tagCompound.getString( "Text", "" );
            this.lines.addAll( Arrays.asList( text.split( "\n" ) ) );
        } else {
            // This is the Anvil version
            for ( int i = 0; i < 4; i++ ) {
                String line = tagCompound.getString( "Text" + ( i + 1 ), "" );
                if ( line.length() > 0 && line.charAt( 0 ) == '{' ) {
                    // Parse JSON (yes this is expensive)
                    try {
                        JSONObject jsonObject = (JSONObject) JSON_PARSER.parse( line );
                        this.lines.add( (String) jsonObject.get( "text" ) );
                    } catch ( ParseException e ) {
                        e.printStackTrace();
                    }
                } else {
                    if ( line.length() == 2 && line.equals( "\"\"" ) ) {
                        continue;
                    }

                    this.lines.add( line );
                }
            }
        }
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );

        compound.addValue( "id", "Sign" );
        compound.addValue( "Text", Joiner.on( "\n" ).join( this.lines ) );
    }

    public List<String> getLines() {
        return this.lines;
    }

}
