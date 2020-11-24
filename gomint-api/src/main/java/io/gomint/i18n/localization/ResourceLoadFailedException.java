/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.i18n.localization;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class ResourceLoadFailedException extends Exception {

    /**
     * Constructs a new ResourceLoadFailedException based on the given Exception
     *
     * @param cause Exception that triggered this Exception
     */
    public ResourceLoadFailedException( final Throwable cause ) {
        super( cause );
    }

    /**
     * Constructs a new ResourceLoadFailedException
     */
    public ResourceLoadFailedException() {

    }

    /**
     * Constructs a new ResourceLoadFailedException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ResourceLoadFailedException( final String message, final Throwable cause ) {
        super( message, cause );
    }

    /**
     * Constructs a new ResourceLoadFailedException with the specified detail message
     *
     * @param message TThe detail message is saved for later retrieval by the getMessage() method.
     */
    public ResourceLoadFailedException( final String message ) {
        super( message );
    }

}
