package io.gomint.server.addons;

import io.gomint.addons.AddonDependency;
import io.gomint.util.SemanticVersion;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of the AddonPack interface.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
public class AddonPack implements io.gomint.addons.AddonPack {

    /*
     * Metadata from the addon's manifest
     */
    private final UUID uuid;
    private final String name;
    private final String description;
    private final SemanticVersion version;
    private final SemanticVersion minimumEngineVersion;
    private final Set<AddonModule> modules;
    private final Set<AddonDependency> dependencies;
    private final AddonContext context;

    /**
     * Constructs a new addon pack given all required information from its manifest.
     *
     * @param uuid                 The addon's identifying UUID
     * @param name                 The addon's name
     * @param description          The addon's description
     * @param version              The addon's version
     * @param minimumEngineVersion The minimum version of Minecraft's engine required by the addon
     * @param modules              The addon's modules
     * @param dependencies         A set of dependencies required by the addon
     * @param context              The addon's context
     */
    public AddonPack(UUID uuid,
                     String name,
                     String description,
                     SemanticVersion version,
                     SemanticVersion minimumEngineVersion,
                     Set<AddonModule> modules,
                     Set<AddonDependency> dependencies,
                     AddonContext context) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.version = version;
        this.minimumEngineVersion = minimumEngineVersion;
        this.modules = modules;
        this.dependencies = dependencies;
        this.context = context;
    }


    // ======================================= Accessors

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public UUID uuid() {
        return this.uuid;
    }

    @Override
    public SemanticVersion version() {
        return this.version;
    }

    @Override
    public SemanticVersion minimumEngineVersion() {
        return this.minimumEngineVersion;
    }

    @Override
    public Collection<? extends AddonModule> modules() {
        return this.modules;
    }

    @Override
    public Collection<? extends AddonDependency> dependencies() {
        return this.dependencies;
    }
}
