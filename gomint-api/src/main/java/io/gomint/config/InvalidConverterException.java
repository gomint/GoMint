/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class InvalidConverterException extends Exception {

    public InvalidConverterException() {}

    public InvalidConverterException(String msg) {
        super(msg);
    }

    public InvalidConverterException(Throwable cause) {
        super(cause);
    }

    public InvalidConverterException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
