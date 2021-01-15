package io.gomint.util;

/**
 * Holds a simple semantic version number. Please refer to <a href="https://semver.org/">https://semver.org/</a>
 * for an explanation of the idea behind semantic versioning.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability 1
 */
public class SemanticVersion implements Comparable<SemanticVersion> {

    private final int major;
    private final int minor;
    private final int patch;

    /**
     * Constructs a new semantic version number with the patch version set to 0.
     *
     * @param major The version's major number
     * @param minor The version's minor number
     */
    public SemanticVersion(int major, int minor) {
        this(major, minor, 0);
    }

    /**
     * Constructs a new semantic version number.
     *
     * @param major The version's major number
     * @param minor The version's minor number
     * @param patch The version's patch number
     */
    public SemanticVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /**
     * Checks whether or not this version is strictly newer than the given one.
     *
     * @param o The other version
     * @return Whether or not this version is strictly newer
     */
    public boolean isNewer(SemanticVersion o) {
        return (this.major > o.major || this.minor > o.minor || this.patch > o.patch);
    }

    /**
     * Checks whether or not this version is strictly older than the given one.
     *
     * @param o The other version
     * @return Whether or not this version is strictly older
     */
    public boolean isOlder(SemanticVersion o) {
        return (this.major < o.major || this.minor < o.minor || this.patch < o.patch);
    }

    /**
     * Checks whether or not this version is compatible with the given one (assuming it
     * semantic versioning was followed appropriately). The ordering is important:
     * version 1.5.2 would be compatible with version 1.4.2 but not vice-versa since
     * feature-additions only present in 1.5.2 might be in use.
     *
     * @param o The other version
     * @return Whether or not the versions are compatible
     */
    public boolean isCompatibleWith(SemanticVersion o) {
        if (this.major != o.major || o.minor > this.minor) return false;
        return true;
    }

    @Override
    public int compareTo(SemanticVersion o) {
        int tmp = Integer.compare(this.major, o.major);
        if (tmp != 0) return tmp;

        tmp = Integer.compare(this.minor, o.minor);
        if (tmp != 0) return tmp;

        return Integer.compare(this.patch, o.patch);
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d", this.major, this.minor, this.patch);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SemanticVersion)) return false;
        SemanticVersion o = (SemanticVersion) obj;
        return this.compareTo(o) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 157;
        hash = 31 * hash + this.major;
        hash = 31 * hash + this.minor;
        hash = 31 * hash + this.patch;
        return hash;
    }

}
