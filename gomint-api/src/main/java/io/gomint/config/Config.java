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
public interface Config {

    void save() throws InvalidConfigurationException;

    void save( File file ) throws InvalidConfigurationException;

    void init() throws InvalidConfigurationException;

    void init( File file ) throws InvalidConfigurationException;

    void reload() throws InvalidConfigurationException;

    void load() throws InvalidConfigurationException;

    void load( File file ) throws InvalidConfigurationException;

}
