/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@Deprecated
public interface SystemCommand {

    /**
     * Execute the command
     *
     * @param args arguments which have been given
     */
    void execute( String[] args );

    /**
     * Get a list of completion suggestions
     *
     * @param args space seperated array of currently given arguments
     * @return list of suggestions or null
     */
    List<String> complete( String[] args );

}
