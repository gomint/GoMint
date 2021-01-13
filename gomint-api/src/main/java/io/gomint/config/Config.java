/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config;

import java.io.File;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface Config<T> {

    T save() throws InvalidConfigurationException;

    T save( File file ) throws InvalidConfigurationException;

    T init() throws InvalidConfigurationException;

    T init( File file ) throws InvalidConfigurationException;

    T reload() throws InvalidConfigurationException;

    T load() throws InvalidConfigurationException;

    T load( File file ) throws InvalidConfigurationException;

}
