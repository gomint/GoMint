/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.converter.anvil.tileentity.v1_8;

import io.gomint.server.entity.tileentity.SignTileEntity;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SignConverter extends BasisConverter<SignTileEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger( SignConverter.class );

    SignConverter( ApplicationContext items, Object2IntMap<String> itemConverter ) {
        super( items, itemConverter );
    }

    @Override
    public SignTileEntity readFrom( NBTTagCompound compound ) {
        SignTileEntity tileEntity = new SignTileEntity( getBlock( compound ) );
        this.context.getAutowireCapableBeanFactory().autowireBean( tileEntity );

        // Fast out for nukkit / pmmp converted worlds
        if ( compound.containsKey( "Text" ) ) {
            tileEntity.fromCompound( compound );
            return tileEntity;
        }

        // Clean sign text
        String[] lines = new String[4];
        lines[0] = this.cleanText( compound, "Text1" );
        lines[1] = this.cleanText( compound, "Text2" );
        lines[2] = this.cleanText( compound, "Text3" );
        lines[3] = this.cleanText( compound, "Text4" );

        for ( int i = 0; i < lines.length; i++ ) {
            tileEntity.getLines().add( lines[i] );
        }

        // Construct new sign
        return tileEntity;
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

        if ( text != null ) {
            if ( ( ( text.length() == 2 && text.equals( "\"\"" ) ) || text.equals( "null" ) ) || text.isEmpty() ) {
                return "";
            }
        }

        return text == null ? "" : text.substring( 1, text.length() - 1 );
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
