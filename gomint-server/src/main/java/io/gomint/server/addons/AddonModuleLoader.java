package io.gomint.server.addons;

import java.io.IOException;

/**
 * An interface for loading a module and registering its components.
 * <p>
 * Different types of modules must be loaded in different ways. It is therefore required
 * that a suitable loading mechanism is employed when loading modules.
 *
 * @author Ciel DeVille
 * @version 1.0
 */
public interface AddonModuleLoader {

    /**
     * Attempts to load the addon module into memory.
     *
     * @param module  The addon to be loaded
     * @param context The addon's context
     * @throws IOException Thrown if loading the addon failed
     */
    void load(AddonModule module, AddonContext context) throws IOException;

    /**
     * If the addon is currently loaded into memory, unloads all resources previously loaded.
     *
     * @param module  The addon to be unloaded
     * @param context The addon's context
     * @throws IOException Thrown if unloading the addon failed
     */
    void unload(AddonModule module, AddonContext context) throws IOException;

}
