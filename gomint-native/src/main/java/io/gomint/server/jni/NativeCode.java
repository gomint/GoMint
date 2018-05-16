/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.jni;

import oshi.PlatformEnum;
import oshi.SystemInfo;

import java.io.*;

import static com.google.common.io.ByteStreams.copy;

/**
 * @param <T> type of interface we want to load natives for
 * @author geNAZt
 * @version 1.0
 */
public final class NativeCode<T> {

    private final String name;
    private final Class<? extends T> javaImpl;
    private final Class<? extends T> nativeImpl;

    private boolean loaded;

    /**
     * Create a new native wrapper
     *
     * @param name       of the native lib we want to load
     * @param javaImpl   the fallback to use when the system is not supported
     * @param nativeImpl the native wrapper which is used to access the native methods
     */
    public NativeCode( String name, Class<? extends T> javaImpl, Class<? extends T> nativeImpl ) {
        this.name = name;
        this.javaImpl = javaImpl;
        this.nativeImpl = nativeImpl;
    }

    /**
     * Create a new instance for the given interface
     *
     * @return either a new instance of the java or the native implementation
     */
    public T newInstance() {
        try {
            return ( loaded ) ? nativeImpl.newInstance() : javaImpl.newInstance();
        } catch ( IllegalAccessException | InstantiationException ex ) {
            throw new RuntimeException( "Error getting instance", ex );
        }
    }

    /**
     * Try to load the native implementation
     *
     * @return true when the native implementation loaded, false otherwise
     */
    public boolean load() {
        if ( !loaded && isSupported() ) {
            String fullName = "gomint-" + name;

            try {
                System.loadLibrary( fullName );
                loaded = true;
            } catch ( Throwable t ) {
            }

            if ( !loaded ) {
                String ending = SystemInfo.getCurrentPlatformEnum() == PlatformEnum.WINDOWS ? ".dll" : ".so";
                try ( InputStream soFile = this.getInput( ending ) ) {
                    // Else we will create and copy it to a temp file
                    File temp = File.createTempFile( fullName, ending );

                    // Don't leave cruft on filesystem
                    temp.deleteOnExit();

                    try ( OutputStream outputStream = new FileOutputStream( temp ) ) {
                        copy( soFile, outputStream );
                    }

                    System.load( temp.getPath() );
                    loaded = true;
                } catch ( IOException ex ) {
                    // Can't write to tmp?
                } catch ( UnsatisfiedLinkError ex ) {
                    System.out.println( "Could not load native library: " + ex.getMessage() );
                }
            }
        }

        return loaded;
    }

    private InputStream getInput( String ending ) {
        InputStream in = NativeCode.class.getClassLoader().getResourceAsStream( this.name + ending );
        if ( in == null ) {
            try {
                in = new FileInputStream( "./src/main/resources/" + this.name + ending );
            } catch ( FileNotFoundException e ) {
                // Ignored -.-
            }
        }

        return in;
    }

    /**
     * Check if the current platform is supported by native code or not
     *
     * @return true when supported, false when not
     */
    private static boolean isSupported() {
        // We currently only support windows and linux x64
        return ( SystemInfo.getCurrentPlatformEnum() == PlatformEnum.WINDOWS ||
            SystemInfo.getCurrentPlatformEnum() == PlatformEnum.LINUX ) &&
            "amd64".equals( System.getProperty( "os.arch" ) );
    }

}
