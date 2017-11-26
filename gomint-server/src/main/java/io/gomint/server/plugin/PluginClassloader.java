package io.gomint.server.plugin;

import io.gomint.GoMint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PluginClassloader extends URLClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger( PluginClassloader.class );
    private static final Set<PluginClassloader> ALL_LOADERS = new CopyOnWriteArraySet<>();
    private static ClassLoader applicationClassloader;

    static {
        ClassLoader.registerAsParallelCapable();

        applicationClassloader = GoMint.class.getClassLoader();
    }

    /**
     * Create a new plugin class loader
     *
     * @param pluginFile which holds all classes of a plugin
     * @throws MalformedURLException when the file is incorrectly labeled
     */
    PluginClassloader( File pluginFile ) throws MalformedURLException {
        super( new URL[]{ pluginFile.toURI().toURL() } );
        ALL_LOADERS.add( this );
    }

    @Override
    protected Class<?> loadClass( String name, boolean resolve ) throws ClassNotFoundException {
        return loadClass0( name, resolve, true );
    }

    private Class<?> loadClass0( String name, boolean resolve, boolean checkOther ) throws ClassNotFoundException {
        try {
            return super.loadClass( name, resolve );
        } catch ( ClassNotFoundException ex ) {
            // Ignored
        }

        if ( checkOther ) {
            for ( PluginClassloader loader : ALL_LOADERS ) {
                if ( loader != this ) {
                    try {
                        return loader.loadClass0( name, resolve, false );
                    } catch ( ClassNotFoundException ex ) {
                        // Ignored
                    }
                }
            }

            try {
                return applicationClassloader.loadClass( name );
            } catch ( ClassNotFoundException ex ) {
                // Ignored
            }
        }

        throw new ClassNotFoundException( name );
    }

    /**
     * Remove the loader and free the resources loaded with it
     */
    public void remove() {
        ALL_LOADERS.remove( this );

        try {
            super.close();
        } catch ( IOException e ) {
            LOGGER.warn( "Could not close plugin classloader", e );
        }
    }

}
