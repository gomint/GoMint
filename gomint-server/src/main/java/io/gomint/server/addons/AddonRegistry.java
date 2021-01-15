package io.gomint.server.addons;

import io.gomint.server.blocks.BlockCatalogue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipFile;

/**
 * A central registry for all installed addons.
 * <p>
 * This class acts as a centralized registry for all installed addons and provides further registries for
 * all extensible components of gameplay.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
public class AddonRegistry {

    /**
     * The folder from which addons will be loaded.
     */
    private static final File ADDON_FOLDER = new File("addons");

    /*
     * Catalogues required for addon activation
     */
    private final BlockCatalogue blockCatalogue;

    /*
     * Miscellaneous
     */
    private final Logger logger = LoggerFactory.getLogger(AddonRegistry.class);

    /*
     * Fields related to Addon installation
     */
    private final File addonFolder; // Replicated here so it can be made configurable at some time
    private Set<Addon> installedAddons;
    private boolean hasSearchedInstalledAddons;


    public AddonRegistry(BlockCatalogue blockCatalogue) {
        this.blockCatalogue = blockCatalogue;

        this.addonFolder = ADDON_FOLDER;
        this.installedAddons = new HashSet<>();
        this.hasSearchedInstalledAddons = false;
    }


    // ======================================= Addon Installation

    /**
     * Searches the server's addon directory for any installed addons and compiles
     * them into a list for further reference. Addons may be loaded once detected.
     *
     * @throws IOException Thrown an error occurs during addon detection
     */
    public void searchInstalledAddons() throws IOException {
        this.installedAddons.clear();

        if (!this.addonFolder.exists()) {
            if (!this.addonFolder.mkdirs()) {
                throw new IOException("Failed to create addons directory");
            }
        }

        File[] files = this.addonFolder.listFiles();
        if (!this.addonFolder.isDirectory() || files == null) {
            throw new IOException("Addon folder is not a directory");
        }

        for (File file : files) {
            AddonContext addonContext = null;

            if (file.isFile()) {
                ZipFile zipFile = new ZipFile(file, ZipFile.OPEN_READ);
                addonContext = new AddonZipFileContext(zipFile);
            } else if (file.isDirectory()) {
                addonContext = new AddonFolderContext(file);
            }

            if (addonContext == null) {
                continue;
            }

            Addon addon = addonContext.createAddonFromManifest();
            if (addon != null) {
                this.logger.info("Detected installed addon '{}' [uuid={}]", addon.name(), addon.uuid());
                this.installedAddons.add(addon);
            } else {
                addonContext.close();
            }
        }

        this.hasSearchedInstalledAddons = true;
    }

    /**
     * @return Whether or not the list of installed addons has been searched previously
     */
    public boolean hasSearchedInstalledAddons() {
        return this.hasSearchedInstalledAddons;
    }

    /**
     * Returns an unmodifiable set of installed addons. If no search for installed addons was
     * conducted before invoking this method, one will be performed before returning the result
     * set.
     *
     * @return The set of installed addons
     */
    public Set<Addon> installedAddons() {
        if (!this.hasSearchedInstalledAddons) {
            try {
                this.searchInstalledAddons();
            } catch (IOException e) {
                this.logger.error("Failed to search installed addons");
                return Collections.emptySet();
            }
        }

        return Collections.unmodifiableSet(this.installedAddons);
    }

    /**
     * Attempts to load the given set of addons.
     *
     * @param addons The addons to load
     */
    public void loadAddons(Set<Addon> addons) {
        // TODO: Actually load addons here
    }

}
