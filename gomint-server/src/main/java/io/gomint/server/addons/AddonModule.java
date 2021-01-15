package io.gomint.server.addons;

import io.gomint.addons.AddonModuleType;
import io.gomint.util.SemanticVersion;

import java.util.UUID;

/**
 * Implementation of the AddonModule interface.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
public class AddonModule implements io.gomint.addons.AddonModule {

    /*
     * Metadata fields from Addon Manifest
     */

    private final UUID uuid;
    private final AddonModuleType type;
    private final String description;
    private final SemanticVersion version;




    /**
     * Constructs a new AddonModule given its uuid, type, description and version.
     *
     * @param uuid The module's uuid
     * @param type The module's type
     * @param description The module's description
     * @param version The module's version
     */
    public AddonModule(UUID uuid, AddonModuleType type, String description, SemanticVersion version) {
        this.uuid = uuid;
        this.type = type;
        this.description = description;
        this.version = version;
    }


    // ======================================= Accessors

    @Override
    public UUID uuid() {
        return this.uuid;
    }

    @Override
    public AddonModuleType type() {
        return this.type;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public SemanticVersion version() {
        return this.version;
    }
}
