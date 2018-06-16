/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PluginLoadException extends RuntimeException {

    /**
     * This exception should be used when a plugin failed to startup {@link Plugin#onStartup()} or failed an installation
     * routine {@link Plugin#onInstall()}
     *
     * @param message which should be used to display this exception
     * @param ex which was the cause of this exception
     */
    public PluginLoadException( String message, Exception ex ) {
        super( message, ex );
    }

}
