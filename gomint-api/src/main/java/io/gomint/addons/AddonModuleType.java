package io.gomint.addons;

/**
 * An enumeration of supported types of addon modules.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability 1
 */
public enum AddonModuleType {

    /**
     * A behaviour pack adding content such as blocks and defining their properties.
     */
    BEHAVIOUR_PACK("data"),
    /**
     * A behaviour pack adding additional resources such as textures or sounds.
     */
    RESOURCE_PACK("resources");


    private final String identifier;

    AddonModuleType(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return The identifier of this type of addon module
     */
    public String identifier() {
        return this.identifier;
    }

    /**
     * Retrieves an addon module type given its identifier.
     *
     * @param identifier The identifier of the addon module type
     * @return The addon module type or null if no such addon module type exists
     */
    public static AddonModuleType fromIdentifier(String identifier) {
        for (AddonModuleType type : AddonModuleType.values()) {
            if (type.identifier.equals(identifier)) {
                return type;
            }
        }
        return null;
    }

}
