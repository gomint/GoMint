/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.i18n.localization.loader;

import io.gomint.i18n.localization.ResourceLoadFailedException;
import io.gomint.i18n.localization.ResourceLoader;
import io.gomint.i18n.localization.ResourceManager;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public abstract class FileResourceLoader {

    protected Module module;

    /**
     * Empty Constructor template for the
     * {@link ResourceManager#registerLoader(ResourceLoader)}
     */
    public FileResourceLoader() {

    }

    /**
     * Load a new FileResource. Every Loader which want to read from either the JAR or the Filesystem should extend this Class.
     * It first checks if the File can be found on the Disk and then if not it gets read from the Jar
     *
     * @param module The module from and for which this Resource should be loaded
     */
    public FileResourceLoader( Module module ) {
        this.module = module;
    }

    /**
     * Gets the Resource at the given relative path. It first searches the Plugins Datafolder and then it checks the JAR for
     * an entry in it.
     *
     * @param path The path which should be loaded
     * @return An InputStreamReader for the Resource (either from out of the JAR or from disk)
     * @throws ResourceLoadFailedException if the Encoding is wrong or the File was not found
     */
    protected InputStreamReader getFileInputStreamReader( String path ) throws ResourceLoadFailedException {
        try {
            if ( !path.startsWith( "file://" ) ) {
                InputStream resourceAsStream = module.getResourceAsStream( path );

                if ( resourceAsStream != null ) {
                    //If the file is not on the Disk read it from the JAR
                    return new InputStreamReader( resourceAsStream, StandardCharsets.UTF_8 );
                }
            } else {
                File file = new File( path.substring( 7 ) );

                if ( file.isFile() ) {
                    return new InputStreamReader( new FileInputStream( file ), StandardCharsets.UTF_8 );
                }
            }
        } catch ( UnsupportedEncodingException | FileNotFoundException e ) {
            throw new ResourceLoadFailedException( e );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        throw new ResourceLoadFailedException( "Resource not found" );
    }

    /**
     * Remove the Reference on the Plugin
     */
    protected void cleanup() {
        this.module = null;
    }

}
