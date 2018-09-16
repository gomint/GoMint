/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.converter.anvil.tileentity.v1_8;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.SignTileEntity;
import io.gomint.server.inventory.item.Items;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SignConverter extends BasisConverter<SignTileEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger( SignConverter.class );

    SignConverter( Items items, Object2IntMap<String> itemConverter ) {
        super( items, itemConverter );
    }

    @Override
    public SignTileEntity readFrom( NBTTagCompound compound ) {
        // Read position first
        Location position = this.getPosition( compound );

        // Fast out for nukkit / pmmp converted worlds
        if ( compound.containsKey( "Text" ) ) {
            String[] lines = compound.getString( "Text", "" ).split( "\n" );
            return new SignTileEntity( lines, position );
        }

        // Clean sign text
        String[] lines = new String[4];
        lines[0] = this.cleanText( compound, "Text1" );
        lines[1] = this.cleanText( compound, "Text2" );
        lines[2] = this.cleanText( compound, "Text3" );
        lines[3] = this.cleanText( compound, "Text4" );

        // Construct new sign
        return new SignTileEntity( lines, position );
    }

    private String cleanText( NBTTagCompound compound, String tagName ) {
        String text = compound.getString( tagName, "" );
        if ( text != null && ( text.startsWith( "{\"text" ) || text.startsWith( "{\"extra" ) ) ) {
            try {
                JSONObject jsonObject = (JSONObject) new JSONParser().parse( text );

                StringBuilder output = new StringBuilder();
                Object extraDataObject = jsonObject.get( "extra" );
                if ( extraDataObject != null ) {
                    JSONArray extraData = (JSONArray) extraDataObject;
                    for ( Object extra : extraData ) {
                        parseInner( output, extra );
                    }
                }

                output.append( jsonObject.get( "text" ) );

                String tempOutput = output.toString();
                if ( tempOutput.length() == 2 && tempOutput.equals( "\"\"" ) ) {
                    return "";
                }

                return tempOutput;
            } catch ( Exception e ) {
                LOGGER.warn( "Could not parse sign content for {} -> {}", tagName, text, e );
            }
        }

        return text;
    }

    private void parseInner( StringBuilder output, Object extra ) {
        if ( extra instanceof String ) {
            output.append( (String) extra );
        } else if ( extra instanceof JSONObject ) {
            // This also can be a JsonObject containing text and extra
            JSONObject innerObj = (JSONObject) extra;
            if ( innerObj.containsKey( "text" ) ) {
                output.append( innerObj.get( "text" ) );
            }

            if ( innerObj.containsKey( "extra" ) ) {
                parseInner( output, innerObj.get( "extra" ) );
            }
        }
    }

}
