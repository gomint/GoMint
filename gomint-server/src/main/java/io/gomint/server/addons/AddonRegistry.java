package io.gomint.server.addons;

import io.gomint.addons.AddonDependency;
import io.gomint.server.blocks.BlockCatalogue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
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
    private boolean hasSearchedInstalledAddons;
    private Map<UUID, Addon> installedAddons;
    private Map<UUID, Addon> loadedAddons;


    public AddonRegistry(BlockCatalogue blockCatalogue) {
        this.blockCatalogue = blockCatalogue;

        this.addonFolder = ADDON_FOLDER;
        this.hasSearchedInstalledAddons = false;
        this.installedAddons = new HashMap<>();
        this.loadedAddons = new HashMap<>();
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
                this.installedAddons.put(addon.uuid(), addon);
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
     * Returns an unmodifiable collection of installed addons. If no search for installed addons was
     * conducted before invoking this method, one will be performed before returning the result
     * set.
     *
     * @return The set of installed addons
     */
    public Collection<Addon> installedAddons() {
        if (!this.hasSearchedInstalledAddons) {
            try {
                this.searchInstalledAddons();
            } catch (IOException e) {
                this.logger.error("Failed to search installed addons");
                return Collections.emptySet();
            }
        }

        return Collections.unmodifiableCollection(this.installedAddons.values());
    }

    /**
     * Attempts to load the given set of addons.
     *
     * @param addons The addons to load
     * @throws AddonsMissingException           Thrown if an addon required by one of the addons to be installed is missing
     * @throws AddonsIncompatibleException      Thrown if an addon's version that is required by another addon is not compatible with the installed version
     * @throws CyclicAddonDependenciesException Thrown if there are cyclic dependencies in the given set of addons
     * @throws IOException Thrown if one or more addons could not be loaded
     */
    public void loadAddons(Collection<Addon> addons) throws AddonsMissingException,
                                                            AddonsIncompatibleException,
                                                            CyclicAddonDependenciesException,
                                                            IOException {
        Set<UUID> requiredAddonUUIDs = this.collectRequiredAddonUUIDs(addons);
        this.ensureAddonsInstalled(requiredAddonUUIDs);
        Set<Addon> requiredAddons = this.ensureAddonCompatibility(requiredAddonUUIDs);
        List<Addon> topologicalAddons = this.sortAddonsTopologically(requiredAddons);

        AddonLoader loader = new AddonLoader(this.blockCatalogue);
        Map<UUID, Addon> loadedAddons = new HashMap<>();
        for (Addon addon : topologicalAddons) {
            try {
                loader.load(addon);
                loadedAddons.put(addon.uuid(), addon);
            } catch (IOException e) {
                IOException wrapped = new IOException(String.format("Failed to load addon '%s'", addon.name()));
                this.logger.error(wrapped.getMessage());
                throw wrapped;
            }
        }

        this.loadedAddons.putAll(loadedAddons);
    }

    /**
     * Compiles all addons and their depencies into a set of UUIDs which may be used to check for missing addons.
     *
     * @param addons The addons whose requirements to collect
     * @return A set of required addons' UUIDs
     */
    private Set<UUID> collectRequiredAddonUUIDs(Collection<Addon> addons) {
        Set<UUID> requiredAddonUUIDs = new HashSet<>();
        for (Addon addon : addons) {
            requiredAddonUUIDs.add(addon.uuid());
            for (AddonDependency dependency : addon.dependencies()) {
                requiredAddonUUIDs.add(dependency.uuid());
            }
        }
        return requiredAddonUUIDs;
    }

    /**
     * Ensures that all addons whose UUID is in the given set of UUIDs are installed.
     *
     * @param addonUUIDs The addon UUIDs to ensure
     * @throws AddonsMissingException Thrown if one or more addons are missing
     */
    private void ensureAddonsInstalled(Collection<UUID> addonUUIDs) throws AddonsMissingException {
        UUID[] missingAddonUUIDs = addonUUIDs.stream()
                .filter(uuid -> !this.installedAddons.containsKey(uuid))
                .toArray(UUID[]::new);

        if (missingAddonUUIDs.length > 0) {
            for (UUID missingAddon : missingAddonUUIDs) {
                this.logger.error("Missing addon: [uuid={}]", missingAddon);
            }
            throw new AddonsMissingException(missingAddonUUIDs);
        }
    }

    /**
     * Ensures that all version requirements of any of the specified addon's dependencies are fulfilled.
     *
     * @param addonUUIDs The UUIDs of the addons whose compatibility to ensure
     * @return A set of installed addons resolved by their UUIDs
     * @throws AddonsIncompatibleException Thrown if one or more of addon incompatibility was detected
     */
    private Set<Addon> ensureAddonCompatibility(Collection<UUID> addonUUIDs) throws AddonsIncompatibleException {
        List<AddonsIncompatibleException.AddonIncompatibility> incompatibilities = new ArrayList<>();

        Set<Addon> requiredAddons = addonUUIDs.stream().map(this.installedAddons::get).collect(Collectors.toSet());
        for (Addon addon : requiredAddons) {
            for (AddonDependency dependency : addon.dependencies()) {
                Addon dep = this.installedAddons.get(dependency.uuid());
                if (dep != null) {
                    // Should always be true since we check for missing addons before
                    if (!dep.version().isCompatibleWith(dependency.version())) {
                        this.logger.error("Incompatible addon versions: '{}' requires '{}' in version '{}' ; installed version is '{}'",
                                addon.name(),
                                dep.name(),
                                dependency.version(),
                                dep.version());

                        incompatibilities.add(new AddonsIncompatibleException.AddonIncompatibility(addon, dep, dependency.version()));
                    }
                }
            }
        }

        if (!incompatibilities.isEmpty()) {
            throw new AddonsIncompatibleException(incompatibilities);
        }

        return requiredAddons;
    }

    /**
     * Sorts the given addons topologically so that the resulting list specifies a valid order in which
     * to load the addons. If the addons contain at least one cyclic dependency, a CyclicAddonDependenciesException
     * will be thrown instead. In this case there is no valid load order and the addons can therefore not be
     * sorted topologically.
     *
     * @param addons The addons to sort topologically
     * @return A topologically sorted list of addons
     * @throws CyclicAddonDependenciesException Thrown if the addons contain at least one cyclic dependency
     */
    private List<Addon> sortAddonsTopologically(Collection<Addon> addons) throws CyclicAddonDependenciesException {
        Map<UUID, AddonNode> nodes = new HashMap<>();
        Stack<AddonNode> nextNodes = new Stack<>();
        List<Addon> result = new ArrayList<>(addons.size());

        for (Addon addon : addons) {
            nodes.put(addon.uuid(), new AddonNode(addon));
        }

        for (Addon addon : addons) {
            AddonNode node = nodes.get(addon.uuid());
            for (AddonDependency dependency : addon.dependencies()) {
                AddonNode depNode = nodes.get(dependency.uuid());
                depNode.outbound.add(node);
                node.inbound.add(depNode);
            }
        }

        for (AddonNode node : nodes.values()) {
            if (node.inbound.isEmpty()) {
                nextNodes.push(node);
            }
        }

        while (!nextNodes.isEmpty()) {
            AddonNode node = nextNodes.pop();
            result.add(node.addon);
            for (AddonNode requiringNode : node.outbound) {
                requiringNode.inbound.remove(node);
                if (requiringNode.inbound.isEmpty()) {
                    nextNodes.push(requiringNode);
                }
            }
            node.outbound.clear();
        }

        if (!nodes.values().stream().allMatch(node -> node.inbound.isEmpty() && node.outbound.isEmpty())) {
            throw new CyclicAddonDependenciesException();
        }

        return result;
    }

    /**
     * A helper class to implement topological sorting for addons.
     */
    private static class AddonNode {

        private final Addon addon;
        private final Set<AddonNode> inbound;
        private final Set<AddonNode> outbound;

        public AddonNode(Addon addon) {
            this.addon = addon;
            this.inbound = new HashSet<>();
            this.outbound = new HashSet<>();
        }

    }

}
