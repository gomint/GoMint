/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.config;

import com.blackypaw.simpleconfig.SimpleConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Configuration for jRaknet. This is used to bind to a specific IP and port.
 *
 * @author geNAZt
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ListenerConfig extends SimpleConfig {

    private String ip = "0.0.0.0";
    private int port = 19132;
    private boolean useTCP = false;

}
