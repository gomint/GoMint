package io.gomint.addons;

import io.gomint.util.SemanticVersion;

import java.util.Collection;
import java.util.UUID;

/**
 * A pack of addons in form of modules.
 * <p>
 * An addon pack provides additional in-game content such as new blocks, items
 * or sounds. All this content must be known to both the client and the server
 * in order to be used.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability 1
 */
public interface Addon {

    /**
     * @return The addon pack's name given in its manifest
     */
    String name();

    /**
     * @return The addon pack's description given in its manifest
     */
    String description();

    /**
     * @return The addon pack's unique ID specified in its manifest
     */
    UUID uuid();

    /**
     * @return The addon pack's version number
     */
    SemanticVersion version();

    /**
     * @return The minimum version of Minecraft's engine this addon pack must be run on
     */
    SemanticVersion minimumEngineVersion();

    /**
     * @return A list of modules this addon provides
     */
    Collection<? extends AddonModule> modules();

    /**
     * @return A list of dependencies this addon requires
     */
    Collection<? extends AddonDependency> dependencies();

}
