/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.jni.exception;

/**
 * @author geNAZt
 * @version 1.0
 */
public class NativeException extends Exception {

    /**
     * This will be called from the C part of this JNI binding only!
     *
     * @param message why this happened
     * @param reason the native error code to lookup what exactly happened
     */
    public NativeException( String message, int reason ) {
        super( message + " : " + reason );
    }

}
