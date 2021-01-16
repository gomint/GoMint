/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.i18n.localization;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 * <p>
 * Any ResourceLoader must implement this Interface.
 */
public interface ResourceLoader<R> {

    /**
     * Get all Keys this Resource has
     *
     * @return A List of Strings (if no keys are there it should return an empty (size 0) list)
     */
    List<String> keys();

    /**
     * Get the translatable message out of the Resource
     *
     * @param key The key which should be looked up in the Resource
     * @return The translatable Message or null when the Message could not be found in the Resource
     */
    String get( String key );

    /**
     * Get the Formats this Resource can load. All file endings need to include the dot. For example this supports yml Files
     * then the return should be List which contains a entry ".yml"
     *
     * @return The list of supported File/Format endings
     */
    List<String> formats();

    /**
     * Reload the Resource. This is issued if the ResourceManager wants to reload all loaded Resources to get a actual Cache
     *
     * @throws ResourceLoadFailedException when the Resource could not be reloaded
     */
    R reload() throws ResourceLoadFailedException;

    /**
     * Remove and cleanup this Resource. This is called when the ResourceManager no longer needs this Resource
     */
    void cleanup();

}
