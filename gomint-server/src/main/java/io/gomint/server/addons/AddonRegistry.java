package io.gomint.server.addons;

import com.jsoniter.JsonIterator;
import io.gomint.addons.AddonDependency;
import io.gomint.addons.AddonModuleType;
import io.gomint.util.SemanticVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
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

    /**
     * The name of an addon's manifest file.
     */
    private static final String ADDON_MANIFEST_FILENAME = "manifest.json";

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
            InputStream manifestStream = null;
            JsonIterator manifestJson = null;

            try {
                if (file.isFile()) {
                    ZipFile zipFile = new ZipFile(file, ZipFile.OPEN_READ);
                    ZipEntry manifestEntry = zipFile.getEntry(ADDON_MANIFEST_FILENAME);
                    if (manifestEntry != null) {
                        manifestStream = zipFile.getInputStream(manifestEntry);
                    }
                } else if (file.isDirectory()) {
                    File manifestFile = new File(file.getCanonicalPath() + File.separator + ADDON_MANIFEST_FILENAME);
                    if (manifestFile.exists() && manifestFile.isFile()) {
                        manifestStream = new FileInputStream(manifestFile);
                    }
                }

                manifestJson = JsonIterator.parse(manifestStream, 2048);
                AddonPack addon = this.createAddonPackFromManifest(manifestJson);
                if (addon != null) {
                    this.logger.info("Detected installed addon '{}' [uuid={}]", addon.name(), addon.uuid());
                    this.installedAddons.add(addon);
                }
            } finally {
                if (manifestJson != null) {
                    manifestJson.close();
                }

                if (manifestStream != null) {
                    manifestStream.close();
                }
            }
        }

        this.hasSearchedInstalledAddons = true;
    }

    private AddonPack createAddonPackFromManifest(JsonIterator manifest) throws IOException {
        // Obligatory addon metadata
        String addonDescription = null;
        String addonName = null;
        UUID addonUuid = null;
        SemanticVersion addonVersion = null;
        SemanticVersion addonMinimumEngineVersion = null;
        Set<AddonModule> addonModules = null;
        Set<AddonDependency> addonDependencies = new HashSet<>();

        for (String attribute = manifest.readObject(); attribute != null; attribute = manifest.readObject()) {
            switch (attribute) {
                case "format_version": {
                    int manifestVersion = manifest.readInt();
                    if (manifestVersion != 2) {
                        // Unsupported manifest version
                        return null;
                    }
                    break;
                }
                case "header": {
                    for (String headerAttribute = manifest.readObject(); headerAttribute != null; headerAttribute = manifest.readObject()) {
                        switch (headerAttribute) {
                            case "description":
                                addonDescription = manifest.readString();
                                break;
                            case "name":
                                addonName = manifest.readString();
                                break;
                            case "uuid":
                                addonUuid = this.readUUIDFromJson(manifest);
                                break;
                            case "version":
                                addonVersion = this.readSemanticVersionFromJson(manifest);
                                break;
                            case "min_engine_version":
                                addonMinimumEngineVersion = this.readSemanticVersionFromJson(manifest);
                                break;
                        }
                    }
                    break;
                }
                case "modules":
                    addonModules = new HashSet<>();

                    while (manifest.readArray()) {
                        UUID moduleUuid = null;
                        AddonModuleType moduleType = null;
                        String moduleDescription = null;
                        SemanticVersion moduleVersion = null;

                        for (String moduleAttribute = manifest.readObject(); moduleAttribute != null; moduleAttribute = manifest.readObject()) {
                            switch (moduleAttribute) {
                                case "uuid":
                                    moduleUuid = this.readUUIDFromJson(manifest);
                                    break;
                                case "type":
                                    String value = manifest.readString();
                                    moduleType = AddonModuleType.fromIdentifier(value);
                                    if (moduleType == null) {
                                        this.logger.warn("Unknown addon module type '{}'", value);
                                    }
                                    break;
                                case "description":
                                    moduleDescription = manifest.readString();
                                    break;
                                case "version":
                                    moduleVersion = this.readSemanticVersionFromJson(manifest);
                                    break;
                            }
                        }

                        if (moduleUuid == null ||
                                moduleType == null ||
                                moduleDescription == null ||
                                moduleVersion == null) {
                            this.logger.error("Invalid addon module specification encountered ; missing one or more obligatory fields in manifest");
                            return null;
                        }

                        addonModules.add(new AddonModule(moduleUuid, moduleType, moduleDescription, moduleVersion));
                    }
                    break;
                case "dependencies":
                    while (manifest.readArray()) {
                        UUID dependencyUuid = null;
                        SemanticVersion dependencyVersion = null;

                        for (String dependencyAttribute = manifest.readObject(); dependencyAttribute != null; dependencyAttribute = manifest.readObject()) {
                            switch (dependencyAttribute) {
                                case "uuid":
                                    dependencyUuid = this.readUUIDFromJson(manifest);
                                    break;
                                case "version":
                                    dependencyVersion = this.readSemanticVersionFromJson(manifest);
                                    break;
                            }
                        }

                        if (dependencyUuid == null || dependencyVersion == null) {
                            this.logger.warn("Invalid addon dependency specification encountered ; ignoring");
                            continue;
                        }

                        addonDependencies.add(new AddonDependency(dependencyUuid, dependencyVersion));
                    }
                    break;
            }
        }

        if (addonDescription == null ||
                addonName == null ||
                addonUuid == null ||
                addonVersion == null ||
                addonMinimumEngineVersion == null ||
                addonModules == null) {
            this.logger.error("Invalid addon pack specification encountered ; missing one or more obligatory fields");
            return null;
        }

        return new AddonPack(addonUuid,
                addonName,
                addonDescription,
                addonVersion,
                addonMinimumEngineVersion,
                addonModules,
                addonDependencies);
    }

    private UUID readUUIDFromJson(JsonIterator json) throws IOException {
        try {
            return UUID.fromString(json.readString());
        } catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    private SemanticVersion readSemanticVersionFromJson(JsonIterator json) throws IOException {
        int[] version = json.read(int[].class);
        if (version.length != 3) {
            throw new IOException("Invalid version specification");
        }
        return new SemanticVersion(version[0], version[1], version[2]);
    }

}
