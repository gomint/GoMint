/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.player;

import io.gomint.GoMint;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author BlackyPaw
 * @version 1.0
 * @stability 3
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
        return GoMint.instance().emptyPlayerSkin();
    }

    /**
     * Get the name of the geometry used
     *
     * @return geometry name
     */
    String geometryName();

    /**
     * Data used for geometry of the skin
     *
     * @return geometry data
     */
    String geometryData();

    /**
     * Save the skin to a given file in PNG format
     *
     * @param out stream to which the image should be saved
     * @throws IOException which can be thrown in case of errors while saving
     * @return player skin for chaining
     */
    PlayerSkin saveSkinTo( OutputStream out ) throws IOException;

}
