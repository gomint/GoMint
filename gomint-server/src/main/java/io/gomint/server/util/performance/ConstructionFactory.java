/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.performance;

/**
 * @author geNAZt
 * @version 1.0
 * <p>
 * No we don't build houses in here, just objects from classes !
 */
public interface ConstructionFactory {

    /**
     * Construct a new object with no arguments
     *
     * @return fresh new and shiny object
     */
    Object newInstance();

}
