package io.gomint.server.addons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
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
     * Miscellaneous
     */
    private final Logger logger = LoggerFactory.getLogger(AddonRegistry.class);

    /*
     * Fields related to Addon installation
     */
    private final File addonFolder; // Replicated here so it can be made configurable at some time
    private Set<AddonPack> installedAddons;
    private boolean hasSearchedInstalledAddons;


    public AddonRegistry() {
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

            AddonPack addon = addonContext.createAddonPackFromManifest();
            if (addon != null) {
                this.logger.info("Detected installed addon '{}' [uuid={}]", addon.name(), addon.uuid());
                this.installedAddons.add(addon);
            } else {
                addonContext.close();
            }
        }

        this.hasSearchedInstalledAddons = true;
    }

}
