/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.player;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class GeometryCache {

    private static final Logger LOGGER = LoggerFactory.getLogger( GeometryCache.class );
    private Map<String, String> jsonCache = new HashMap<>();

    /**
     * Get the json data for the given geometry
     *
     * @param name of the geometry
     */
    public String get( String name ) {
        return this.jsonCache.computeIfAbsent( name, this::loadGeometry );
    }

    private String loadGeometry( String geometry ) {
        try ( InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream( geometry + ".json" ) ) {
            StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
            IOUtils.copy( inputStream, stringBuilderWriter, StandardCharsets.UTF_8 );
            return stringBuilderWriter.toString();
        } catch ( IOException e ) {
            LOGGER.error( "Could not load geometry {}: ", geometry, e );
        }

        return null;
    }

}
