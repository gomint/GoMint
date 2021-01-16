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
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class YamlResourceLoader extends FileResourceLoader implements ResourceLoader<YamlResourceLoader> {

    private Map<String, Object> lookup;
    private String file;

    /**
     * Empty Constructor template for the
     * {@link ResourceManager#registerLoader(ResourceLoader)}
     */
    public YamlResourceLoader() {

    }

    /**
     * Load a new YamlResource
     *
     * @param module   The module for which this Resource should be loaded
     * @param file          The file to load
     * @throws ResourceLoadFailedException if the stream could not be closed
     */
    public YamlResourceLoader( Module module, String file ) throws ResourceLoadFailedException {
        super( module );

        this.file = file;
        this.load();
    }

    private void load() throws ResourceLoadFailedException {
        InputStreamReader stream = null;
        try {
            // Get the correct InputStreamReader for this file
            stream = fileInputStreamReader( file );

            // Read from the InputStreamReader till he is empty
            BufferedReader br = new BufferedReader( stream );
            String line;
            StringBuilder sb = new StringBuilder();
            while ( ( line = br.readLine() ) != null ) {
                sb.append( line );
                sb.append( "\n" );
            }

            //Try to read the YamlConfiguration
            Yaml yaml = new Yaml();
            lookup = yaml.load( sb.toString() );
        } catch ( IOException e ) {
            lookup = null;
            throw new ResourceLoadFailedException( e );
        } catch ( ResourceLoadFailedException e ) {
            throw e;
        } finally {
            if ( stream != null ) {
                try {
                    stream.close();
                } catch ( IOException e ) {
                    throw new ResourceLoadFailedException( e );
                }
            }
        }
    }

    /**
     * Get all keys which can be handled by this Resource
     *
     * @return List of keys available
     */
    @Override
    public List<String> keys() {
        List<String> keys = new ArrayList<>();
        addKeys( keys, "", this.lookup );
        return keys;
    }

    private void addKeys( List<String> keys, String root, Map<String, Object> lookup ) {
        for ( Map.Entry<String, Object> entry : lookup.entrySet() ) {
            if ( entry.getValue() instanceof Map ) {
                this.addKeys( keys, entry.getKey() + ".", (Map<String, Object>) entry.getValue() );
            } else {
                keys.add( root + entry.getKey() );
            }
        }
    }

    /**
     * Get the key from the YamlConfiguration
     *
     * @param key Key to get
     * @return The object from YAML or null if YAML loading was an error
     */
    @Override
    public String get( String key ) {
        // Fast out when parsing the YML did fail
        if ( lookup == null ) {
            return null;
        }

        Object finalData;

        // Check if we have a dot in the key
        if ( key.contains( "." ) ) {
            String[] keyParts = key.split( "\\." );

            Map<String, Object> current = (Map<String, Object>) lookup.get( keyParts[0] );
            if ( current == null ) {
                return null;
            }

            for ( int i = 1; i < keyParts.length - 1; i++ ) {
                current = (Map<String, Object>) current.get( keyParts[i] );
                if ( current == null ) {
                    return null;
                }
            }

            finalData = current.get( keyParts[keyParts.length - 1] );
        } else {
            finalData = lookup.get( key );
        }

        return finalData instanceof String ? (String) finalData : finalData == null ? null : String.valueOf( finalData );
    }

    /**
     * Get the Formats this Loader can load
     *
     * @return A List of String as formats this Loader supports
     */
    @Override
    public List<String> formats() {
        return Arrays.asList( ".yml" );
    }

    /**
     * Force the reload of this Resource
     *
     * @throws ResourceLoadFailedException
     */
    @Override
    public YamlResourceLoader reload() throws ResourceLoadFailedException {
        this.load();
        return this;
    }

    /**
     * If plugin gets unloaded remove all refs
     */
    @Override
    public void cleanup() {
        lookup = null;
        file = null;

        super.cleanup();
    }

}
