/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.player;

import io.gomint.GoMint;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public interface PlayerSkin {

    /**
     * Get the skin from an url
     *
     * @param url which we should fetch
     * @return skin or null on error
     */
    static PlayerSkin fromURL( String url ) {
        try {
            URL urlObj = new URL( url );
            URLConnection connection = urlObj.openConnection();
            try ( InputStream inputStream = connection.getInputStream() ) {
                return GoMint.instance().createPlayerSkin( inputStream );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create a empty skin
     *
     * @return
     */
    static PlayerSkin empty() {
        return GoMint.instance().getEmptyPlayerSkin();
    }

    /**
     * Gets the name of the player's skin.
     *
     * @return The name of the player's skin
     */
    String getName();

    /**
     * Gets the raw data of the player's skin.
     *
     * @return The raw data of the player's skin
     */
    byte[] getRawData();

    /**
     * Get cape data
     *
     * @return cape data or null
     */
    byte[] getCapeData();

    /**
     * Get the name of the geometry used
     *
     * @return geometry name
     */
    String getGeometryName();

    /**
     * Data used for geometry of the skin
     *
     * @return geometry data
     */
    String getGeometryData();

    /**
     * Save the skin to a given file in PNG format
     *
     * @param out stream to which the image should be saved
     * @throws IOException which can be thrown in case of errors while saving
     */
    void saveSkinTo( OutputStream out ) throws IOException;

}
