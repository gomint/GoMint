package io.gomint.server.addons;

import io.gomint.util.SemanticVersion;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * An exception that indicates a version mismatch between two addons.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
public class AddonsIncompatibleException extends Exception {

    public static class AddonIncompatibility {

        private final Addon dependent;
        private final Addon required;
        private final SemanticVersion requiredVersion;

        public AddonIncompatibility(Addon dependent, Addon required, SemanticVersion requiredVersion) {
            this.dependent = dependent;
            this.required = required;
            this.requiredVersion = requiredVersion;
        }

        @Override
        public String toString() {
            return String.format("'%s' -> '%s': version '%s' required, but '%s' installed",
                    this.dependent.name(),
                    this.required.name(),
                    this.requiredVersion,
                    this.required.version());
        }

    }

    /**
     * Constructs a new exception indicating that first depends on second in version requiredVersion.
     *
     * @param incompatibilities A list of incompatibilities
     */
    public AddonsIncompatibleException(Collection<AddonIncompatibility> incompatibilities) {
        super(
                String.format("Addons incompatible: [%s]",
                        incompatibilities
                                .stream()
                                .map(AddonIncompatibility::toString)
                                .collect(Collectors.joining(", "))
                )
        );
    }

}
