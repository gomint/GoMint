/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.config;

import io.gomint.config.YamlConfig;

/**
 * Configuration for jRaknet. This is used to bind to a specific IP and port.
 *
 * @author geNAZt
 * @version 1.0
 */
public class ListenerConfig extends YamlConfig {

    private String ip = "0.0.0.0";
    private int port = 19132;
    private boolean useUPNP = true;

    public boolean useUPNP() {
        return useUPNP;
    }

    public String ip() {
        return ip;
    }

    public int port() {
        return port;
    }

}
