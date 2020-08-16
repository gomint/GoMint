package io.gomint.server.resource;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ResourcePack {

    private PackIdVersion version;
    private long size;

    public ResourcePack(PackIdVersion version, long size) {
        this.version = version;
        this.size = size;
    }

    public PackIdVersion getVersion() {
        return version;
    }

    public long getSize() {
        return size;
    }
}
