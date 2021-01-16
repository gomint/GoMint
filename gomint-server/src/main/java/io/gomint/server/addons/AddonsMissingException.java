package io.gomint.server.addons;

import java.util.Arrays;
import java.util.UUID;

/**
 * An exception that denotes that a required addon is missing.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
public class AddonsMissingException extends Exception {

    private final UUID[] uuids;

    /**
     * Constructs a new AddonMissingException given the UUID of the missing addon.
     *
     * @param uuids The uuids of the missing addon
     */
    public AddonsMissingException(UUID[] uuids) {
        super(String.format("Missing addons [uuids=%s]", Arrays.toString(uuids)));
        this.uuids = uuids;
    }

    /**
     * @return The UUID of the missing addon
     */
    public UUID[] uuids() {
        return this.uuids;
    }

}
