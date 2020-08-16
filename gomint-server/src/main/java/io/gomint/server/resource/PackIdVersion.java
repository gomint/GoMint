package io.gomint.server.resource;

import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PackIdVersion {

    private UUID id;
    private String version;

    public PackIdVersion(UUID id, String version) {
        this.id = id;
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }
}
