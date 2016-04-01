/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command;

/**
 * @author Digot
 * @version 1.0
 */
public interface CommandSender {

    String getName();

    //TODO Add something like a message builder instead of sending a single string message
    void sendMessage(String message);

}
