package io.gomint.addons;

import io.gomint.util.SemanticVersion;

import java.util.UUID;

/**
 * Some addons may depend on other addons' presence to function properly.
 * For this reason it is possible to declare an addon's dependencies within
 * its manifest. This class models such a dependency.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
public class AddonDependency {

    private final UUID uuid;
    private final SemanticVersion version;

    /**
     * Constructs a new addon dependency given the UUID of the addon that is being required
     * and the minimum version required.
     *
     * @param uuid The UUID of the addon that is being required
     * @param version The version of the addon that is being required
     * @throws IllegalArgumentException Thrown if either uuid or version are null
     */
    public AddonDependency(UUID uuid, SemanticVersion version) {
        if (uuid == null || version == null) {
            throw new IllegalArgumentException("UUID and version must not be null");
        }

        this.uuid = uuid;
        this.version = version;
    }

    /**
     * @return The UUID of the addon that is being required
     */
    public UUID uuid() {
        return this.uuid;
    }

    /**
     * @return The version of the addon that is being required
     */
    public SemanticVersion version() {
        return this.version;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AddonDependency)) return false;
        AddonDependency o = (AddonDependency) obj;
        return this.uuid.equals(o.uuid) && this.version.equals(o.version);
    }

    @Override
    public int hashCode() {
        int hash = 157;
        hash = 31 * hash + this.uuid.hashCode();
        hash = 31 * hash + this.version.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return String.format("[uuid=%s, version=%s]", this.uuid, this.version);
    }

}
