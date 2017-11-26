/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * This Bootstrap downloads all Libraries given inside of the "libs.dep" File in the Root
 * of the Application Workdir and then instanciates the Class which is given as Application
 * entry point.
 *
 * @author geNAZt
 * @version 1.0
 */
public class Bootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger( Bootstrap.class );


    /**
     * Main entry point. May be used for custom dependency injection, dynamic
     * library class loaders and other experiments which need to be done before
     * the actual main entry point is executed.
     *
     * @param args The command-line arguments to be passed to the entryClass
     */
    public static void main( String[] args ) {
        // Check if classloader has been changed (it should be a URLClassLoader)
        if ( !( ClassLoader.getSystemClassLoader() instanceof URLClassLoader ) ) {
            System.out.println( "System Classloader is no URLClassloader" );
            System.exit( -1 );
        }

        // Parse options first
        OptionParser parser = new OptionParser();
        parser.accepts( "lp" ).withRequiredArg().ofType( Integer.class );
        parser.accepts( "lh" ).withRequiredArg();
        parser.accepts( "slc" );

        OptionSet options = parser.parse( args );

        // Check if we need to create the libs Folder
        File libsFolder = new File( "libs/" );
        if ( !libsFolder.exists() && !libsFolder.mkdirs() ) {
            System.out.println( "Could not create library Directory" );
            System.exit( -1 );
        }

        // Check the libs (versions and artifacts)
        if ( !options.has( "slc" ) ) // -slc (skip lib checking)
        {
            checkLibs( libsFolder );
        }

        File[] files = libsFolder.listFiles();
        if ( files == null ) {
            System.out.println( "Library Directory is corrupted" );
            System.exit( -1 );
        }

        // Scan the libs/ Directory for .jar Files
        for ( File file : files ) {
            if ( file.getAbsolutePath().endsWith( ".jar" ) ) {
                try {
                    System.out.println( "Loading lib: " + file.getAbsolutePath() );
                    addJARToClasspath( file );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }

        // Load the Class entrypoint
        try {
            Class<?> coreClass = ClassLoader.getSystemClassLoader().loadClass( "io.gomint.server.GoMintServer" );
            Constructor constructor = coreClass.getDeclaredConstructor( OptionSet.class );
            constructor.newInstance( new Object[]{ options } );
        } catch ( Throwable t ) {
            LOGGER.error( "GoMint crashed: ", t );
        }
    }

    /**
     * Download needed Libs from the central Maven repository or any other Repo (can be any url in the libs.dep file)
     *
     * @param libsFolder in which the downloads should be stored
     */
    private static void checkLibs( File libsFolder ) {
        // Check if we are able to skip this
        if ( System.getProperty( "skip.libcheck", "false" ).equals( "true" ) ) {
            return;
        }

        // Load the dependency list
        try ( BufferedReader reader = new BufferedReader( new InputStreamReader( Bootstrap.class.getResourceAsStream( "/libs.dep" ) ) ) ) {
            String libURL;
            while ( ( libURL = reader.readLine() ) != null ) {
                // Check for comment
                if ( libURL.isEmpty() || libURL.equals( System.getProperty( "line.separator" ) ) || libURL.startsWith( "#" ) ) {
                    continue;
                }

                // Head first to get informations about the file
                URL url = new URL( libURL );
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod( "HEAD" );

                // Filter out non java archive content types
                if ( !"application/java-archive".equals( urlConnection.getHeaderField( "Content-Type" ) ) ) {
                    System.out.println( "Skipping the download of " + libURL + " because its not a Java Archive" );
                    continue;
                }

                // We need the contentLength to compare
                int contentLength = Integer.parseInt( urlConnection.getHeaderField( "Content-Length" ) );

                String[] tempSplit = url.getPath().split( "/" );
                String fileName = tempSplit[tempSplit.length - 1];

                // Check if we have a file with the same length
                File libFile = new File( libsFolder, fileName );
                if ( libFile.exists() && libFile.length() == contentLength ) {
                    System.out.println( "Skipping the download of " + libURL + " because there already is a correct sized copy" );
                    continue;
                }

                // Download the file from the Server
                Files.copy( url.openStream(), libFile.toPath(), StandardCopyOption.REPLACE_EXISTING );
                System.out.println( "Downloading library: " + fileName );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Appends a JAR into the System Classloader
     *
     * @param moduleFile which should be added to the classpath
     * @throws IOException
     */
    private static void addJARToClasspath( File moduleFile ) throws IOException {
        URL moduleURL = moduleFile.toURI().toURL();
        Class[] parameters = new Class[]{ URL.class };

        ClassLoader sysloader = ClassLoader.getSystemClassLoader();
        Class sysclass = URLClassLoader.class;

        try {
            Method method = sysclass.getDeclaredMethod( "addURL", parameters );
            method.setAccessible( true );
            method.invoke( sysloader, new Object[]{ moduleURL } );
        } catch ( NoSuchMethodException | InvocationTargetException | IllegalAccessException e ) {
            e.printStackTrace();
            throw new IOException( "Error, could not add URL to system classloader" );
        }
    }
}
