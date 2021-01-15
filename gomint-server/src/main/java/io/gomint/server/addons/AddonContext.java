package io.gomint.server.addons;

import com.jsoniter.JsonIterator;
import io.gomint.addons.AddonDependency;
import io.gomint.addons.AddonModuleType;
import io.gomint.util.SemanticVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * An addon's context is an abstraction of how an addon is laid out within the
 * server's installation path.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
public abstract class AddonContext implements Closeable {
    /**
     * The name of an addon's manifest file.
     */
    private static final String ADDON_MANIFEST_FILENAME = "manifest.json";

    private final Logger logger = LoggerFactory.getLogger(AddonContext.class);


    /**
     * Attempts to open the entry identified by the given path relative to
     * the addon's root installation path, e.g. 'sounds/sound_definitions.json'.
     *
     * @param path The entry's path
     * @return An input stream to the specified entry
     * @throws IOException Thrown if the entry was not found or could not be opened
     */
    public abstract InputStream openEntry(String path) throws IOException;

    /**
     * Gets a list of all entries within this context.
     *
     * @return A list of all entries within this context
     * @throws IOException Thrown if the list of entries could not be retrieved
     */
    public abstract Stream<? extends String> entries() throws IOException;

    /**
     * Creates an addon by parsing its manifest found in this context.
     *
     * @return The created addon on success or null if the manifest was incomplete
     * @throws IOException Thrown if an I/O error occurs while parsing the addon's manifest
     */
    Addon createAddonFromManifest() throws IOException {
        // Obligatory addon metadata
        String addonDescription = null;
        String addonName = null;
        UUID addonUuid = null;
        SemanticVersion addonVersion = null;
        SemanticVersion addonMinimumEngineVersion = null;
        Set<AddonModule> addonModules = null;
        Set<AddonDependency> addonDependencies = new HashSet<>();

        // Attempt to open manifest
        try (JsonIterator manifest = JsonIterator.parse(this.openEntry(ADDON_MANIFEST_FILENAME), 4096)) {
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

            return new Addon(addonUuid,
                    addonName,
                    addonDescription,
                    addonVersion,
                    addonMinimumEngineVersion,
                    addonModules,
                    addonDependencies,
                    this);
        }
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
