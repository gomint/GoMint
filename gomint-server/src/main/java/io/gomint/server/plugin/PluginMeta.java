/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.plugin;

import io.gomint.plugin.PluginVersion;
import io.gomint.plugin.StartupPriority;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author BlackyPaw
 * @version 1.0
 */
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
    private boolean hasModuleInfo;
    private Set<String> packages;
    private Set<File> moduleDependencies;

    public PluginMeta(File pluginFile) {
        this.pluginFile = pluginFile;
    }

    public void addPackage(String packageName) {
        if (this.packages == null) {
            this.packages = new HashSet<>();
        }

        this.packages.add(packageName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PluginVersion getVersion() {
        return version;
    }

    public void setVersion(PluginVersion version) {
        this.version = version;
    }

    public StartupPriority getPriority() {
        return priority;
    }

    public void setPriority(StartupPriority priority) {
        this.priority = priority;
    }

    public Set<String> getDepends() {
        return depends;
    }

    public void setDepends(Set<String> depends) {
        this.depends = depends;
    }

    public Set<String> getSoftDepends() {
        return softDepends;
    }

    public void setSoftDepends(Set<String> softDepends) {
        this.softDepends = softDepends;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public Set<String> getInjectionCommands() {
        return injectionCommands;
    }

    public void setInjectionCommands(Set<String> injectionCommands) {
        this.injectionCommands = injectionCommands;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public boolean hasModuleInfo() {
        return hasModuleInfo;
    }

    public void setHasModuleInfo(boolean hasModuleInfo) {
        this.hasModuleInfo = hasModuleInfo;
    }

    public Set<String> getPackages() {
        return packages;
    }

    public void setPackages(Set<String> packages) {
        this.packages = packages;
    }

    public Set<File> getModuleDependencies() {
        return moduleDependencies;
    }

    public void setModuleDependencies(Set<File> moduleDependencies) {
        this.moduleDependencies = moduleDependencies;
    }

    public File getPluginFile() {
        return pluginFile;
    }
}
