package io.gomint.addons;

import io.gomint.util.SemanticVersion;

import java.util.UUID;

/**
 * A module within an addon such as a resource pack or a behaviour pack.
 */
public interface AddonModule {

    /**
     * @return The addon module's unique ID
     */
    UUID uuid();

    /**
     * @return The type of this addon module
     */
    AddonModuleType type();

    /**
     * @return The addon module's description
     */
    String description();

    /**
     * @return The addon module's version number
     */
    SemanticVersion version();

}
