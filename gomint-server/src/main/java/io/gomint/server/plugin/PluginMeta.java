/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.plugin;

import io.gomint.plugin.PluginVersion;
import io.gomint.plugin.StartupPriority;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@RequiredArgsConstructor
@ToString
@Data
public class PluginMeta {

    // Plugin basics
    private final File pluginFile;
    private String name;
    private PluginVersion version;
    private StartupPriority priority;
    private Set<String> depends;
    private Set<String> softDepends;
    private String mainClass;

    // Injection stuff
    private Set<String> injectionCommands;

    // Module stuff
    private String moduleName;
    private Set<String> packages;

    public void addPackage(String packageName) {
        if (this.packages == null) {
            this.packages = new HashSet<>();
        }

        this.packages.add(packageName);
    }
}
