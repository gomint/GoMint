/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.player;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 2
 */
public interface AnimationFrame {

    /**
     * Save the frame to a given file in PNG format
     *
     * @param out stream to which the image should be saved
     * @throws IOException which can be thrown in case of errors while saving
     */
    void saveTo( OutputStream out ) throws IOException;

}
