/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin.config;

import io.gomint.config.Comment;
import io.gomint.config.YamlConfig;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class Config extends YamlConfig {

    @Comment( "Enable the testing of the chunk generator?" )
    private boolean enableChunkGeneration = true;

}
